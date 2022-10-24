package com.saishostudios.saisho.core.graphics;

import com.saishostudios.saisho.core.Entity;
import com.saishostudios.saisho.core.Loader;
import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.components.Transform;
import com.saishostudios.saisho.core.utils.Maths;
import com.saishostudios.saisho.core.constants.Saisho;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private static final float FOV = 70;
    private static StaticShader _shader;

    private static List<RenderableObject> renderableObjects = new ArrayList<>();
    private static List<RenderableInstancedObject> renderableInstancedObjects = new ArrayList<>();
    private boolean isPerspectiveMatrix = true;
    private Matrix4f projectionMatrix;
    public Renderer(StaticShader shader){
        //changeToOrtho();
        createProjectionMatrix();
        _shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }


    public void render(Entity entity, StaticShader shader){
        RawModel model = entity.getModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition()
                                                                         , entity.getRotation()
                                                                         , entity.getScale());
        //transformationMatrix = Maths.OffSetMatrix(transformationMatrix, new Vector3f(0.5f, 0.0f, 0.5f));
        shader.loadTransformationMatrix(transformationMatrix);
        GL11.glDrawElements(GL30.GL_TRIANGLES, model.getVertexCount(), GL30.GL_UNSIGNED_INT, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    public static void render(){
        for(RenderableObject renderable : renderableObjects){
            render(renderable.model, renderable.transform);
        }
        for(RenderableInstancedObject instancedRenderable : renderableInstancedObjects){
            renderInstanced(instancedRenderable.model, instancedRenderable.transform, instancedRenderable.offsets);
        }
    }
    public static float[] generateVertices(List<Vector3f> offsets){
        float[] vertices = new float[offsets.size() * 3];
        for(int i = 0; i < offsets.size(); i++){
            vertices[i *3] = offsets.get(i).x;
            vertices[i *3 +1] = offsets.get(i).y;
            vertices[i *3 +2] = offsets.get(i).z;
        }
        return vertices;
    }
    public static void render(RawModel model, Transform transform){
        GL30.glBindVertexArray(model.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(transform.position
                , transform.rotation
                , transform.scale);
        //transformationMatrix = Maths.OffSetMatrix(transformationMatrix, new Vector3f(0.5f, 0.0f, 0.5f));
        _shader.loadTransformationMatrix(transformationMatrix);
        GL11.glDrawArrays(GL30.GL_TRIANGLES, 0, model.getVertexCount());
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    public static void renderInstanced(RawModel model, Transform transform, float[] offsets){
        //Loader.storeDataInAttributesList(3, offsets);
        GL30.glBindVertexArray(model.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL30.glEnableVertexAttribArray(3);
        GL40.glVertexAttribDivisor(3,1);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(transform.position
                , transform.rotation
                , transform.scale);
        //transformationMatrix = Maths.OffSetMatrix(transformationMatrix, new Vector3f(0.5f, 0.0f, 0.5f));
        _shader.loadTransformationMatrix(transformationMatrix);
        //GL40.glDrawArraysInstanced(GL30.GL_TRIANGLES, model.getVertexCount(), GL30.GL_UNSIGNED_INT, offsets.length /3);
        GL40.glDrawArraysInstanced(GL30.GL_TRIANGLES, 0 , model.getVertexCount(), offsets.length /3);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
    };
    public void renderDebugLines(RawModel entity){
        _shader.loadTransformationMatrix(new Matrix4f());
        GL30.glBindVertexArray(entity.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_LINES, 0, entity.getVertexCount());
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
    public static void submit(RawModel model, Transform transform){
        System.out.println("submitted" + model.getVaoID() + " " + transform.position);
        renderableObjects.add(new RenderableObject(model, transform));
    }
    public static void submit(RawModel model, Transform transform, float[] offsets){
        //System.out.println("submitted" + model.getVaoID() + " " + transform.position);
        renderableInstancedObjects.add(new RenderableInstancedObject(model, transform, offsets));
    }
    public void changeToOrtho(){
        projectionMatrix = new Matrix4f();
        projectionMatrix.ortho(-20, 20f, -20f,20f, Saisho.NEAR_PLANE - 20, Saisho.FAR_PLANE);
        isPerspectiveMatrix = false;
        //_shader.loadProjectionMatrix(projectionMatrix);
    }
    private void createProjectionMatrix(){
        float aspectRatio = (float) Saisho.WIDTH / (float) Saisho.HEIGHT;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = Saisho.FAR_PLANE - Saisho.NEAR_PLANE;
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((Saisho.FAR_PLANE + Saisho.NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * Saisho.NEAR_PLANE * Saisho.FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);
        isPerspectiveMatrix = true;
        //changeToOrtho();
    }

    public void changePerspective() {
        if(isPerspectiveMatrix){
            changeToOrtho();
            isPerspectiveMatrix = false;
        }
        else{
            createProjectionMatrix();
            isPerspectiveMatrix = true;
        }
        _shader.start();
        _shader.loadProjectionMatrix(projectionMatrix);
        _shader.stop();
    }
}
