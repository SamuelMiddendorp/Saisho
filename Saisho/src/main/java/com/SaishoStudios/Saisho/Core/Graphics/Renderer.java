package com.SaishoStudios.Saisho.Core.Graphics;

import com.SaishoStudios.Saisho.Core.Entity;
import com.SaishoStudios.Saisho.Core.Utils.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static com.SaishoStudios.Saisho.Core.Constants.Saisho.*;

public class Renderer {
    private static final float FOV = 70;
    private StaticShader _shader;
    private boolean isPerspectiveMatrix = true;
    private Matrix4f projectionMatrix;
    public Renderer(StaticShader shader){
        changeToOrtho();
        //createProjectionMatrix();
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
    public void renderDebugLines(RawModel entity){
        _shader.loadTransformationMatrix(new Matrix4f());
        GL30.glBindVertexArray(entity.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_LINES, 0, entity.getVertexCount());
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
    public void changeToOrtho(){
        projectionMatrix = new Matrix4f();
        projectionMatrix.ortho(-20, 20f, -20f,20f, NEAR_PLANE - 20, FAR_PLANE);
        //_shader.loadProjectionMatrix(projectionMatrix);
    }
    private void createProjectionMatrix(){
        float aspectRatio = (float) WIDTH / (float) HEIGHT;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);
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
