package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.input.InputManager;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;

import static com.saishostudios.saisho.core.constants.SaishoKeys.KEY_P;
import static com.saishostudios.saisho.core.constants.SaishoKeys.KEY_SPACE;

public class ShootBullet extends Component {
    float timer = 0;
    @Override
    public void onUpdate(float deltaTime) {

        if(InputManager.keys[KEY_P]){
            if(timer > 0.2f){
                fireBullet(new Vector3f(0.0f, 0.0f, -1.0f));
                timer = 0;
            }
        }
        timer += deltaTime;
    }
    private void fireBullet(Vector3f dir){
        var go = new GameObject();
        go.transform.position.x = gameObject.transform.position.x;
        go.transform.position.y = gameObject.transform.position.y;
        go.transform.position.z = gameObject.transform.position.z - 2f;
        go.addComponent(RigidBody.class).acceleration.add(dir.mul(20.0f));
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.RECT, new Vector3f(0.1f,0.1f,0.1f));
        var bc = go.addComponent(BoxCollider.class);
        bc.h = 0.05f;
        bc.l = 0.05f;
        bc.w = 0.05f;
        GameObject.world.addCandidate(go);

    }
}
