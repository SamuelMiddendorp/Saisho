package example;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.*;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.input.InputManager;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;

import java.util.Random;

import static com.saishostudios.saisho.core.constants.SaishoKeys.KEY_X;

public class PlatformerDemo extends SaishoGame {
    private int bounds = 25;
    @Override
    public void init() {
        //blockCameraMovement = true;
        camera.move(new Vector3f(0.0f, 25f, 10f));
        //camera.increaseYaw(30f);
        cameraSpeed = 4f;
        camera.increasePitch(-45f);
        var go = new GameObject();
        go.addComponent(PlayerJumpController.class);
        go.addComponent(RigidBody.class);
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE, null);
        go.addComponent(WASDController.class).speed = 5f;
        go.addComponent(ShootBullet.class);

        BoxCollider bc = go.addComponent(BoxCollider.class);
        bc.w = 0.5f;
        bc.h = 0.5f;
        bc.l = 0.5f;
        go.transform.position.y = 5f;
        go.transform.position.z = -2f;
        go.withTag("player");
        world.add(go);

        world.add(createPlatform(0,-2));
        world.add(createPlatform(0,-6));
        world.add(createPlatform(0,-12));
        var moveablePlatform = createPlatform(0, -16);
        moveablePlatform.addComponent(ColliderResolver.class);
        moveablePlatform.setFlag("touched", false);
        moveablePlatform.withTag("moveable");
        world.add(moveablePlatform);
        var go3 = new GameObject();
        go3.transform.position.y = -10f;
        go3.addComponent(RigidBody.class).isStatic = true;
        go3.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.RECT, new Vector3f(30,1.0f,30));
        BoxCollider bc3 = go3.addComponent(BoxCollider.class);
        bc3.w = 15;
        bc3.h = 0.5f;
        bc3.l = 15;
        world.add(go3);
    }

    @Override
    public void update(float dt) {
        var platform = GameObject.findByTag("moveable");
        var player = GameObject.findByTag("player");
        if(platform.getFlag("touched") && !player.getComponent(RigidBody.class).onGround){
            platform.transform.position.z -= 6f;
            platform.setFlag("touched", false);
        }
//        if(player.transform.position.y < -4f){
//            player.transform.position = new Vector3f(0.0f, 5f, -2f);
//            camera.setM_cameraPosition(new Vector3f(0.0f, 25f, 10f));
//            platform.transform.position.z = -16f;
//        }
        //platform.setFlag("touched", false);
    }

    @Override
    public void fixedUpdate(float dt) {
        if(InputManager.keys[KEY_X]){
            camera.increaseYaw(0.1f);
        }
    }
    private GameObject createPlatform(int x, int z){
        var go = new GameObject();
        var rb = go.addComponent(RigidBody.class);
        rb.isStatic = true;
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.RECT, new Vector3f(2.0f, 1.0f, 1.0f));
        BoxCollider bc = go.addComponent(BoxCollider.class);
        bc.w = 1f;
        bc.h = 0.5f;
        bc.l = 0.5f;
        //cube.addComponent(RotateSin.class);
        go.transform.position = new Vector3f(x, 0.0f, z);
        return go;
    }
    public static void main(String[] args){
        PlatformerDemo game = new PlatformerDemo();
        game.start();
    }
}
