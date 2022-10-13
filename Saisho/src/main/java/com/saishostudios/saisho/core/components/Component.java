package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.scratch.GameObject;

public class Component {
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    protected GameObject gameObject;
    public void onUpdate(float deltaTime){};
    public void onStart(){};
    public void onDestroy(){};
}
