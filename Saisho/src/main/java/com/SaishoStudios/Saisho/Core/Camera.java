package com.SaishoStudios.Saisho.Core;

import org.joml.Vector3f;

import static java.lang.Math.*;

public class Camera {
    protected Vector3f m_cameraPosition = new Vector3f(0.0f, 0.0f, 0.0f);
    protected Vector3f m_cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    protected Vector3f m_cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

    protected Vector3f m_cameraRight = new Vector3f(1.0f, 0.0f, 0.0f);

    protected Vector3f m_worldUp = new Vector3f(0.0f, 1.0f, 0.0f);
    protected float pitch, yaw = -90f, roll;

    public Vector3f getPosition() {
        return m_cameraPosition;
    }

    public Vector3f getM_cameraFront() {
        return m_cameraFront;
    }

    public void move(Vector3f velocity){
        m_cameraPosition.add(velocity);
        calculateFront();
    }
    public void increaseYaw(float yawDelta){

        yaw += yawDelta;
        calculateFront();
    }
    public void increasePitch(float pitchDelta){
        pitch += pitchDelta;
        if(pitch > 89){
            pitch = 89;
        }
        if(pitch < -89){
            pitch = -89;
        }
        calculateFront();
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
    private void calculateFront(){
        m_cameraFront.x = (float) (cos(toRadians(yaw)) * cos(toRadians(pitch)));
        m_cameraFront.y = (float) sin(toRadians(pitch));
        m_cameraFront.z = (float) sin(toRadians(yaw)) * (float) cos(toRadians(pitch));
        m_cameraFront.normalize();

        m_cameraRight = m_cameraFront.cross(m_worldUp, new Vector3f()).normalize();
        m_cameraUp = m_cameraRight.cross(m_cameraFront, new Vector3f()).normalize();

    }

    public Vector3f getM_cameraUp() {
        return m_cameraUp;
    }

}
