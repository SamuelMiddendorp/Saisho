package com.saishostudios.saisho.core.scratch;

public class Component {
    protected void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    protected GameObject gameObject;
    public void onUpdate(float deltaTime){};
    public void onStart(){};
    public void onDestroy(){};
}
