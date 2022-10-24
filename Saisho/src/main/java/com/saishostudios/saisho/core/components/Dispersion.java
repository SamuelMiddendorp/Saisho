package com.saishostudios.saisho.core.components;

import org.joml.Quaternionf;
import org.joml.Random;
import org.joml.Vector3f;

public class Dispersion extends Component{
    public Vector3f dir;
    public float speed = 1f;
    @Override
    public void onUpdate(float deltaTime) {
        gameObject.transform.position.add(dir.mul(speed * deltaTime, new Vector3f()));
        //gameObject.transform.rotation.rotateAxis(0.1f * deltaTime, 0.2f, 0.3f, 0.4f );
    }
    @Override
    public void onStart() {
        Random rand = new Random();
        dir = new Vector3f(-1 + rand.nextFloat() * 2, 0.0f, -1 + rand.nextFloat() * 2).normalize();
    }
}
//        if(gameObject.transform.position.z > 100){
//            dir.z *= -1f;
//        }
//        else if(gameObject.transform.position.z < -300){
//            dir.z *= -1f;
//        }
//        else if(gameObject.transform.position.x < -100){
//            dir.x *= -1f;
//        }
//        else if(gameObject.transform.position.x > 300){
//            dir.x *= -1f;
//        }