package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.input.InputManager;

import static com.saishostudios.saisho.core.constants.SaishoKeys.KEY_SPACE;

public class PlayerJumpController extends Component {
    @Override
    public void onUpdate(float deltaTime) {
        if(InputManager.keys[KEY_SPACE]){

            var rb = gameObject.getComponent(RigidBody.class);
            if(rb.onGround)
            {
                rb.acceleration.y += 15.0f;
            }
        }
    }
}
