package example;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.MeshRenderer;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;

import java.util.Random;

public class SamplePrefab extends SaishoGame{
    @Override
    public void init() {
        cameraSpeed = 20f;
        RawModel model = Prefab.create(PrefabType.CUBE);
        var cube = new GameObject();
        MeshRenderer mesh = cube.addComponent(MeshRenderer.class);
        mesh.model = model;
        world.add(cube);
    }

    @Override
    public void update(float dt) {
        if(inputManager.mouseButtons[0]){
            createCube();
        }
    }

    @Override
    public void fixedUpdate(float dt) {
        setWindowTitle(dt + "");
    }
    private void createCube(){
        Random rand = new Random();
        RawModel model = Prefab.create(PrefabType.CUBE);
        var cube = new GameObject();
        MeshRenderer mesh = cube.addComponent(MeshRenderer.class);
        cube.transform.position = new Vector3f(rand.nextInt(100), 1.0f, rand.nextInt(100));
        mesh.model = model;
        world.add(cube);
    }
    public static void main(String[] args){
        var game = new SamplePrefab();
        game.start();
    }
}
