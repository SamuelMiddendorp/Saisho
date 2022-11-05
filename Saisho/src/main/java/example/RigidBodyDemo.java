package example;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.*;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;

public class RigidBodyDemo extends SaishoGame {
    @Override
    public void init() {
        blockCameraMovement = true;
        camera.move(new Vector3f(0.0f, 10f, 50f));
        var go = new GameObject();
        go.addComponent(RigidBody.class);
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE, null);
        go.addComponent(WASDController.class);
        BoxCollider bc = go.addComponent(BoxCollider.class);
        bc.w = 0.5f;
        bc.h = 0.5f;
        bc.l = 0.5f;
        go.transform.position.z = -5f;
        world.add(go);
        var go2 = new GameObject();
        go2.addComponent(RigidBody.class).velocity.z = 0.2f;
        go2.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE, null);
        BoxCollider bc2 = go2.addComponent(BoxCollider.class);
        bc2.w = 0.5f;
        bc2.h = 0.5f;
        bc2.l = 0.5f;
        go2.transform.position.z = -10f;

        world.add(go2);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void fixedUpdate(float dt) {
        setWindowTitle(1 / dt + "fps");
    }

    public static void main(String[] args){
        RigidBodyDemo game = new RigidBodyDemo();
        game.start();
    }
}
