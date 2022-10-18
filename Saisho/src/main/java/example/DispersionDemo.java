package example;

import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.Dispersion;
import com.saishostudios.saisho.core.components.InstancedMeshRenderer;
import com.saishostudios.saisho.core.components.MeshRenderer;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.scratch.GameObject;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class DispersionDemo extends SaishoGame {
    @Override
    public void init() {
        cameraSpeed = 50f;
        RawModel sampleModel = OBJLoader.loadObjModel("main/models/multiple");
        Random rand = new Random();
        for (int i = 0; i < 5000; i++) {
            GameObject go = new GameObject();
            go.transform.position = new Vector3f((float) rand.nextInt(100), 1.0f, -1 * (float) rand.nextInt(100));
            MeshRenderer mesh = go.addComponent(MeshRenderer.class);
            mesh.model = sampleModel;
            go.transform.scale = 0.4f;
            Dispersion desp = go.addComponent(Dispersion.class);
            desp.speed = 50f;
            world.add(go);
        }

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void fixedUpdate(float dt) {

    }

    public static void main(String[] args){
        DispersionDemo game = new DispersionDemo();
        game.start();
    }
}
