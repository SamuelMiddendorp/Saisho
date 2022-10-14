package example;

import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.SaishoGame;
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
    private RawModel sampleModel;
    @Override
    public void init() {
        List<Vector3f> offsets = createOffsets(100000);
        sampleModel = OBJLoader.loadObjModelInstanced("main/models/tile", offsets);
        //RawModel playerModel = OBJLoader.loadObjModel("main/models/david_boom";
        GameObject player = new GameObject();
        player.withTag("player");
        player.transform.position.y = 1.0f;
        InstancedMeshRenderer mesh = player.addComponent(InstancedMeshRenderer.class);
        mesh.model = sampleModel;
        mesh.offSets = offsets;
        world.add(player);
        //player.transform.position = new Vector3f(1.0f, 1.0f, -2.0f);
        player.transform.scale = 1.0f;
//        MeshRenderer mesh = player.addComponent(MeshRenderer.class);
//        mesh.model = playerModel;
        //player.addComponent(WASDController.class);


        //createSomeGameObjetcs(10000, 20f);
        // Called after engine is done initalizing

        logger.log("Everything well");
    }

    @Override
    public void update(float dt) {
        setWindowTitle(1 / dt + "");
        // Called every frame
    }

    @Override
    public void fixedUpdate(float dt) {
        // Called at fixed interval
    }

    public static void main(String[] args){
        SampleGame game = new SampleGame();
        game.start();
    }
    private List<Vector3f> createOffsets(int count){
        Random rand = new Random();
        List<Vector3f> offsets = new ArrayList<>();
        for(int i = 0; i < count; i++){
            offsets.add(new Vector3f((float)rand.nextInt((int)(count / 20)), 1.0f, -1 * (float)rand.nextInt((int)(count / 20))));
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
            go.transform.scale = 0.2f;
            world.add(go);
        }
    }
}
