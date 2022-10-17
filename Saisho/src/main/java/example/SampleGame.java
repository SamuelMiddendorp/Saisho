package example;

import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.components.Dispersion;
import com.saishostudios.saisho.core.components.InstancedMeshRenderer;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.components.MeshRenderer;
import com.saishostudios.saisho.core.components.WASDController;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SampleGame extends SaishoGame {
    private int displayCounter = 0;
    private RawModel sampleModel;
    @Override
    public void init() {

            sampleModel = OBJLoader.loadObjModel("main/models/tile");
//        List<Vector3f> offsets = createOffsets(5000,8);
        //sampleModel = OBJLoader.loadObjModelInstanced("main/models/david_boom", offsets);
          createSomeGameObjetcs(12000,8);
//            GameObject player = new GameObject();
//
//            InstancedMeshRenderer mesh = player.addComponent(InstancedMeshRenderer.class);
//            mesh.model = sampleModel;
//            mesh.offSets = offsets;
//            player.transform.scale = 0.2f;
//            world.add(player);
            cameraSpeed = 30f;
            camera.increasePitch(-75f);
            camera.move(new Vector3f(50f, 450f, 0f));
//        GameObject player = new GameObject();
//        player.withTag("player");
//        MeshRenderer mesh = player.addComponent(MeshRenderer.class);
//        camera.move(new Vector3f(0f,100f,100));
//        camera.increasePitch(-45f);
//        mesh.model = sampleModel;
//        world.add(player);
//        player.addComponent(WASDController.class);
//
//        logger.log("Everything well");
    }

    @Override
    public void update(float dt) {
        if(displayCounter > 50){
            setWindowTitle(1 / dt + "");
            displayCounter = 0;
        }

        // Called every frame
        displayCounter ++;
    }

    @Override
    public void fixedUpdate(float dt) {
        // Called at fixed interval
    }

    public static void main(String[] args){
        SampleGame game = new SampleGame();
        game.start();
    }
    private List<Vector3f> createOffsets(int count, int density){
        Random rand = new Random();
        List<Vector3f> offsets = new ArrayList<>();
        for(int i = 0; i < count; i++){
            offsets.add(new Vector3f((float)rand.nextInt((int)(count / density / 2)), 1.0f, -1 * (float)rand.nextInt((int)(count / density / 2))));
        }
        return offsets;
    }
    private void createSomeGameObjetcs(int count, float density){
        Random rand = new Random();
        for(int i = 0; i < count; i++){
            GameObject go = new GameObject();
            go.transform.position = new Vector3f((float)rand.nextInt((int)(count / density / 2)), 1.0f, -1 * (float)rand.nextInt((int)(count / density / 2)));
            MeshRenderer mesh = go.addComponent(MeshRenderer.class);
            mesh.model = sampleModel;
            go.transform.scale = 0.4f;
            Dispersion desp = go.addComponent(Dispersion.class);
            desp.speed = 100f;
            world.add(go);
        }
    }
}
