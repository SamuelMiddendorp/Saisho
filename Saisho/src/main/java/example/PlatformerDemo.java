package example;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.*;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;

import java.util.Random;

public class PlatformerDemo extends SaishoGame {
    private int bounds = 25;
    @Override
    public void init() {
        //blockCameraMovement = true;
        camera.move(new Vector3f(0.0f, 25f, 10f));
        cameraSpeed = 4f;
        camera.increasePitch(-45f);
        var go = new GameObject();
        go.addComponent(PlayerJumpController.class);
        go.addComponent(RigidBody.class);
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE, null);
        go.addComponent(WASDController.class).speed = 5f;

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
        moveablePlatform.setFlag("touched", false);
        moveablePlatform.withTag("moveable");
        world.add(moveablePlatform);

    }

    @Override
    public void update(float dt) {
        var platform = GameObject.findByTag("moveable");
        var player = GameObject.findByTag("player");
        if(platform.getFlag("touched")){
            platform.transform.position.z -= 6f;
            platform.setFlag("touched", false);
        }
        //platform.setFlag("touched", false);
    }

    @Override
    public void fixedUpdate(float dt) {

    }
    private GameObject createPlatform(int x, int z){
        var go = new GameObject();
        var rb = go.addComponent(RigidBody.class);
        rb.isStatic = true;
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.RECT, new Vector3f(2.0f, 0.5f, 1.0f));
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
