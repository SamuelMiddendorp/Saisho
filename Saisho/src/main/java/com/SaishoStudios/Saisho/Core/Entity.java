package com.SaishoStudios.Saisho.Core;

import com.SaishoStudios.Saisho.Core.Graphics.RawModel;
import com.SaishoStudios.Saisho.Core.Utils.Maths;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static java.lang.Math.atan2;

public class Entity {
    private RawModel model;
    private Vector3f position;
    private float scale;
    private Quaternionf rotation = new Quaternionf();

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public void setRotation(Vector3f plane, float angle){
        rotation.setAngleAxis(angle, plane.x, plane.y, plane.z);
    }

    public Entity(RawModel model, Vector3f position, float scale) {
        this.model = model;
        this.position = position;
        this.scale = scale;
    }
    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
    public void lookAt(Vector3f front){
        Vector3f dir = front.sub(position, new Vector3f()).normalize();
        rotation.rotationTo(new Vector3f(0,0,1), dir);
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

    public void setScale(float scale) {
        this.scale = scale;
    }
}
