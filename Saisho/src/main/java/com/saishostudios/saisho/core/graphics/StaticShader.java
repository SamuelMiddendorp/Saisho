package com.saishostudios.saisho.core.graphics;

import com.saishostudios.saisho.core.Camera;
import com.saishostudios.saisho.core.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class StaticShader extends ShaderProgram{
    private static final String VS_PATH = "src/main/shaders/vertexShader.vs";
    private static final String FS_PATH = "src/main/shaders/fragmentShader.fs";
    private int transformationMatrixLocation;

    private int projectionMatrixLocation;

    private int viewMatrixLocation;

    private int lightPosLocation;

    public StaticShader() {
        super(VS_PATH, FS_PATH);
    }

    @Override
    protected void getAllUniformLocations() {
         transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
         projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
         viewMatrixLocation = super.getUniformLocation("viewMatrix");
         lightPosLocation = super.getUniformLocation("lightPos");
    }
    public void loadTransformationMatrix(Matrix4f matrix){
        super.setUniformMatrix(transformationMatrixLocation, matrix);
    }
    public void loadProjectionMatrix(Matrix4f matrix){
        super.setUniformMatrix(projectionMatrixLocation, matrix);
    }
    public void loadLightPos(Vector3f pos){
        super.loadVector(lightPosLocation, pos);
    }
    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera.getPosition(),camera.getM_cameraFront(), camera.getM_cameraUp());
        super.setUniformMatrix(viewMatrixLocation, matrix);
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,  "position");
        super.bindAttribute(1,  "in_color");
        super.bindAttribute(2,  "aNormal");
    }
}
