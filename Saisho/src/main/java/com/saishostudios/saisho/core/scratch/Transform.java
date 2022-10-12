package com.saishostudios.saisho.core.scratch;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform extends Component{
    public float randomFloat = 0.0f;

    public Vector3f position = new Vector3f();

    public Quaternionf rotation = new Quaternionf();
    @Override
    public void onUpdate(float deltaTime) {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
