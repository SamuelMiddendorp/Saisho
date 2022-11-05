package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.scratch.GameObject;
import org.joml.Quaternionf;
import org.joml.Random;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class RotateSin extends  Component{
    private Vector3f lookAt = new Vector3f(1);
    private int randomOffset;
    @Override
    public void onUpdate(float deltaTime) {
        //gameObject.transform.rotation.add(new Quaternionf(rotation.x ,rotation.y, rotation.z, Math.sin(glfwGetTime() + randomOffset)));
        gameObject.transform.lookAt(SaishoGame.getIntersectingPlane(GameObject.world.camera.getPosition(), SaishoGame.mousePicker.getCurrentRay()));
    }
    @Override
    public void onStart() {
        //Random rand = new Random();
        //randomOffset = rand.nextInt(100);
        //rotation = new Vector3f(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
    }
}
