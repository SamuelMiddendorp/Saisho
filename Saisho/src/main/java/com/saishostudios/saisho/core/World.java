package com.saishostudios.saisho.core;

import com.saishostudios.saisho.core.scratch.GameObject;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<GameObject> gameObjects = new ArrayList<>();
    public Camera camera;
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
    public void add(GameObject go){
        gameObjects.add(go);
    }
}
