package com.saishostudios.saisho.core.components;

import org.joml.Random;
import org.joml.Vector3f;

public class Dispersion extends Component{
    public Vector3f dir;
    public float speed = 1f;
    @Override
    public void onUpdate(float deltaTime) {
        if(gameObject.transform.position.z > 100){
            dir.z *= -1f;
        }
        else if(gameObject.transform.position.z < -300){
            dir.z *= -1f;
        }
        else if(gameObject.transform.position.x < -100){
            dir.x *= -1f;
        }
        else if(gameObject.transform.position.x > 300){
            dir.x *= -1f;
        }
        gameObject.transform.position.add(dir.mul(speed * deltaTime, new Vector3f()));
    }
    @Override
    public void onStart() {
        Random rand = new Random();
        dir = new Vector3f(-1 + rand.nextFloat() * 2, 0.0f, -1 + rand.nextFloat() * 2).normalize();
    }
}
