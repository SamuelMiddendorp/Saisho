package com.SaishoStudios.Saisho.Core;

import com.SaishoStudios.Saisho.Core.Graphics.RawModel;
import org.joml.Vector3f;

import static java.lang.Math.atan2;

public class Entity {
    private RawModel model;

    public void setPosition(Vector3f position) {
        this.position = position;
    }
    private Vector3f position;

    private float scale;

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
    public void setFront(Vector3f front){
        rotY = (float)Math.toDegrees(atan2(front.z, front.x)) + 180;
        System.out.println(rotY);
    }
    private float rotX, rotY, rotZ;

    public Entity(RawModel model, Vector3f position, float scale, float rotX, float rotY, float rotZ) {
        this.model = model;
        this.position = position;
        this.scale = scale;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }
    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
    public void increaseRotation(float dx,   float dy, float dz){
        this.rotX+= dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public RawModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
