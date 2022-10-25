package com.saishostudios.saisho.core.components;

import org.joml.Quaternionf;
import org.joml.Random;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class RotateSin extends  Component{
    private Vector3f rotation;
    private int randomOffset;
    @Override
    public void onUpdate(float deltaTime) {
        gameObject.transform.rotation.add(new Quaternionf(rotation.x ,rotation.y, rotation.z, Math.sin(glfwGetTime() + randomOffset)));
    }
    @Override
    public void onStart() {
        Random rand = new Random();
        randomOffset = rand.nextInt(100);
        rotation = new Vector3f(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
    }
}
