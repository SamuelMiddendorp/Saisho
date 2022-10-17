package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.input.InputManager;
import com.saishostudios.saisho.core.scratch.GameObject;
import org.joml.Vector3f;

import static com.saishostudios.saisho.core.constants.SaishoKeys.*;

public class WASDController extends Component{
    private Transform transform;
    public float speed = 30f;
    private GameObject go;
    @Override
    public void onUpdate(float deltaTime) {
        if(InputManager.keys[KEY_W]){
            go.transform.position.add(new Vector3f(0.0f, 0.0f, -speed * deltaTime));
        }
        if(InputManager.keys[KEY_S]){
            go.transform.position.add(new Vector3f(0.0f, 0.0f, speed * deltaTime));
        }
        if(InputManager.keys[KEY_A]){
            go.transform.position.add(new Vector3f(-speed * deltaTime, 0.0f, 0.0f));
        }
        if(InputManager.keys[KEY_D]){
            go.transform.position.add(new Vector3f(speed * deltaTime, 0.0f, 0.0f));
        }
    }

    @Override
    public void onStart() {
        go = GameObject.findByTag("player");
        transform = gameObject.getComponent(Transform.class);
    }
}
