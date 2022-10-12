package com.saishostudios.saisho.core;

import com.saishostudios.saisho.core.utils.Maths;
import com.saishostudios.saisho.core.constants.Saisho;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MousePicker {
    public Vector3f getCurrentRay() {
        return currentRay;
    }

    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

    private double mouseX;
    private double mouseY;
    public MousePicker(Camera cam, Matrix4f projection){
        camera = cam;
        projectionMatrix = projection;
        viewMatrix = Maths.createViewMatrix(camera.getPosition(),camera.getM_cameraFront(), camera.getM_cameraUp());
    }
    public float[] calculateOrthoRay(){

        Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        float x = (normalizedCoords.x) * (20);
        float y = (normalizedCoords.y) * (20);

// Finally, calculate the ray origin:
        Vector3f rayOrigin = camera.getPosition().add(camera.m_cameraRight.mul(x, new Vector3f()), new Vector3f()).add(camera.m_cameraUp.mul(y, new Vector3f()));
        Vector3f rayDirection = camera.m_cameraFront;
        return new float[]{
                rayOrigin.x, rayOrigin.y, rayOrigin.z,
                rayDirection.x, rayDirection.y, rayDirection.z
        };
    }
    public Vector3f calculateMouseRay() {
        Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        Vector4f boxRay = new Vector4f(normalizedCoords.x, normalizedCoords.y, 1, 0);

        boxRay = new Matrix4f(projectionMatrix).invert().transform(boxRay);
        boxRay = new Vector4f(boxRay.x, boxRay.y, -1, 0).normalize();
        boxRay = new Matrix4f(viewMatrix).invert().transform(boxRay).normalize();
        currentRay = new Vector3f(boxRay.x, boxRay.y, boxRay.z);
        return new Vector3f(boxRay.x, boxRay.y, boxRay.z);
    }
    public Vector2f getNormalizedDeviceCoords(double mouseX, double mouseY){
        float x = (2f*(float)mouseX) / Saisho.WIDTH - 1;
        float y = 1.0f - (2f*(float)mouseY) / Saisho.HEIGHT;
        return new Vector2f(x, y);
    }

    public void setMouseCoords(double x, double y) {
        mouseX = x;
        mouseY = y;
        viewMatrix = Maths.createViewMatrix(camera.getPosition(),camera.getM_cameraFront(), camera.getM_cameraUp());
        currentRay = calculateMouseRay();
    }
}
