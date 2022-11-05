package example;

import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.Dispersion;
import com.saishostudios.saisho.core.components.MeshRenderer;
import com.saishostudios.saisho.core.components.RotateSin;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.input.InputManager;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

public class SamplePrefab extends SaishoGame{
    private final int bounds = 100;
    private Vector3f lookAt = new Vector3f();
    private GameObject cube;

    private boolean hyperspeed = false;
    @Override
    public void init() {
        cameraSpeed = 50f;
        camera.move(new Vector3f(0,40f, 80));
        camera.increasePitch(-30f);
        lookAt = new Vector3f(1);
        RawModel model = Prefab.create(PrefabType.CUBE, null);
        //model = OBJLoader.loadObjModel("main/models/cube");
        cube = new GameObject();
        RotateSin sin = cube.addComponent(RotateSin.class);
        cube.getComponent(RotateSin.class);
        MeshRenderer mesh = cube.addComponent(MeshRenderer.class);

        mesh.model = model;
        world.add(cube);
        for(int i = 0; i < 1000; i ++){
            createCube();
        }
    }

    @Override
    public void update(float dt) {
        lookAt = getIntersectingPlane(camera.getPosition(), mousePicker.getCurrentRay());
        hyperspeed = false;
        if(InputManager.keys[GLFW.GLFW_KEY_LEFT_SHIFT]){
            hyperspeed = true;
        }
        if(hyperspeed){
            cameraSpeed = 1000f;
        }
        else {
            cameraSpeed = 50f;
        }
        if(inputManager.mouseButtons[0]){
            for(int i = 0; i < 50; i ++){
                createCube();
            }
        }
        for(GameObject go : world.getGameObjects()){
            for(GameObject goOther : world.getGameObjects()){
                if(go != goOther) {
                    if (aabb(go.transform.position.x, go.transform.position.z, goOther.transform.position.x, goOther.transform.position.z)) {
                        Dispersion disp = go.getComponent(Dispersion.class);
                        Dispersion other = goOther.getComponent(Dispersion.class);
                        if (disp != null && other != null) {
                            disp.dir.mul(-1f);
                            other.dir.mul(-1f);
                            light.transform.position = go.transform.position;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void fixedUpdate(float dt) {
        setWindowTitle(1 / dt + "fps");


    }
    private void createCube(){
        Random rand = new Random();
        RawModel model = Prefab.create(PrefabType.RECT, new Vector3f(1.0f, 10, 1.0f));
        var cube = new GameObject();
        MeshRenderer mesh = cube.addComponent(MeshRenderer.class);
        //cube.addComponent(RotateSin.class);
        cube.transform.position = new Vector3f(-bounds + rand.nextInt(bounds * 2), 1.0f, -bounds + rand.nextInt(bounds * 2));
        Dispersion disp = cube.addComponent(Dispersion.class);
        disp.speed = 20f;

        mesh.model = model;
        world.add(cube);
    }
    public static void main(String[] args){
        var game = new SamplePrefab();
        game.start();
    }
    private static boolean aabb(float x, float z, float otherX, float otherZ){
        return x < otherX + 0.5 &&
                x + 0.5 > otherX &&
                z < otherZ + 0.5 &&
                z + 0.5 > otherZ;
    }
}
