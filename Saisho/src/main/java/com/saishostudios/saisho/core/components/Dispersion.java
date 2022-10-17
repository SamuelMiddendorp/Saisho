package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.graphics.Renderer;
import org.joml.Random;
import org.joml.Vector3f;

public class Dispersion extends Component{
    public Vector3f dir;
    public float speed = 1f;
    @Override
    public void onUpdate(float deltaTime) {
        if(gameObject.transform.position.z > 0){
            dir.mul(-1);
        }
        else if(gameObject.transform.position.z < -500){
            dir.mul(-1);
        }
        else if(gameObject.transform.position.x < 0){
            dir.mul(-1);
        }
        else if(gameObject.transform.position.x > 500){
            dir.mul(-1);
        }
        gameObject.transform.position.add(dir.mul(speed * deltaTime, new Vector3f()));
    }
    @Override
    public void onStart() {
        Random rand = new Random();
        dir = new Vector3f(-1 + rand.nextFloat() * 2, 0.0f, -1 + rand.nextFloat() * 2);
    }
}
