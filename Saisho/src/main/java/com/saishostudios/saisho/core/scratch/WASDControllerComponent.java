package com.saishostudios.saisho.core.scratch;

import com.saishostudios.saisho.core.input.InputManager;

import static com.saishostudios.saisho.core.constants.SaishoKeys.*;

public class WASDControllerComponent extends Component{
    private Transform transform;
    private GameObject go;
    @Override
    public void onUpdate(float deltaTime) {
        if(InputManager.keys[KEY_W]){
            go.getComponent(Transform.class).randomFloat += deltaTime;
        }
    }

    @Override
    public void onStart() {
        go = GameObject.findByTag("player");
        transform = gameObject.getComponent(Transform.class);
    }
}
