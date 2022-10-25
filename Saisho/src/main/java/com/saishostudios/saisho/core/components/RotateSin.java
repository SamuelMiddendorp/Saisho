package com.saishostudios.saisho.core.components;

import org.joml.Quaternionf;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class RotateSin extends  Component{
    @Override
    public void onUpdate(float deltaTime) {
        gameObject.transform.rotation.set(new Quaternionf(0.0f, 0.4f, 0.0f, Math.sin(glfwGetTime())));
    }
}
