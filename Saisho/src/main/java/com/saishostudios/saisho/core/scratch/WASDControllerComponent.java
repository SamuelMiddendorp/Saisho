package com.saishostudios.saisho.core.scratch;

import com.saishostudios.saisho.core.input.InputManager;

import static com.saishostudios.saisho.core.constants.SaishoKeys.*;

public class WASDControllerComponent extends Component{
    private Transform transform;

    @Override
    public void onUpdate(float deltaTime) {
        if(InputManager.keys[KEY_W]){
            transform.randomFloat += deltaTime;
        }
    }

    @Override
    public void onStart() {
        transform = gameObject.getComponent(Transform.class);
    }
}
