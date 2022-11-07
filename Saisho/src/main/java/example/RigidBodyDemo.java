package example;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.*;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;

import java.util.Random;

public class RigidBodyDemo extends SaishoGame {
    private int bounds = 25;
    @Override
    public void init() {
        blockCameraMovement = true;
        camera.move(new Vector3f(0.0f, 30f, 50f));
        camera.increasePitch(-45f);
        var go = new GameObject();
        go.addComponent(RigidBody.class);
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE, null);
        go.addComponent(WASDController.class).speed = 5f;
        BoxCollider bc = go.addComponent(BoxCollider.class);
        bc.w = 0.5f;
        bc.h = 0.5f;
        bc.l = 0.5f;
        go.transform.position.z = -5f;
        world.add(go);
        var go2 = new GameObject();
        go2.addComponent(RigidBody.class);
        go2.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE, null);
        BoxCollider bc2 = go2.addComponent(BoxCollider.class);
        bc2.w = 0.5f;
        bc2.h = 0.5f;
        bc2.l = 0.5f;
        go2.transform.position.z = -10f;
        world.add(go2);
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
    }

    @Override
    public void fixedUpdate(float dt) {
        if(inputManager.mouseButtons[0]){
            createCube();
        }
        setWindowTitle(1 / dt + "fps");
    }
    private void createCube(){
        Random rand = new Random();
        var go = new GameObject();
        go.addComponent(RigidBody.class);
        go.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE, null);
        BoxCollider bc = go.addComponent(BoxCollider.class);
        bc.w = 0.5f;
        bc.h = 0.5f;
        bc.l = 0.5f;
        //cube.addComponent(RotateSin.class);
        go.transform.position = new Vector3f(-bounds + rand.nextInt(bounds * 2), 4.0f, -bounds + rand.nextInt(bounds * 2));
        world.add(go);
    }
    public static void main(String[] args){
        RigidBodyDemo game = new RigidBodyDemo();
        game.start();
    }
}
