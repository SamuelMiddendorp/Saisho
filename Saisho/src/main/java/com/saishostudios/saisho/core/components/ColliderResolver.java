package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.scratch.GameObject;

public class ColliderResolver extends Component implements CollisionListener{

    @Override
    public void onCollide(GameObject go) {
        System.out.println("collided!");
        go.transform.position.y = 20f;
    }
}
