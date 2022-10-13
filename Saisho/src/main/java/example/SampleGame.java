package example;

import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.scratch.MeshRenderer;
import com.saishostudios.saisho.core.scratch.Transform;
import com.saishostudios.saisho.core.scratch.WASDController;
import org.joml.Random;
import org.joml.Vector3f;

import static com.saishostudios.saisho.core.constants.SaishoKeys.*;

public class SampleGame extends SaishoGame {
    private RawModel sampleModel;
    @Override
    public void init() {
        sampleModel = OBJLoader.loadObjModel("main/models/tile");
        RawModel playerModel = OBJLoader.loadObjModel("main/models/dab_on_em");
        GameObject player = new GameObject();
        player.withTag("player");
        world.add(player);
        player.transform.position = new Vector3f(1.0f, 1.0f, -2.0f);
        MeshRenderer mesh = player.addComponent(MeshRenderer.class);
        mesh.model = playerModel;
        player.addComponent(WASDController.class);


        createSomeGameObjetcs(20);
        // Called after engine is done initalizing

        logger.log("Everything well");
    }

    @Override
    public void update(float dt) {
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
    private void createSomeGameObjetcs(int count){
        Random rand = new Random();
        for(int i = 0; i < count; i++){
            GameObject go = new GameObject();
            go.transform.position = new Vector3f((float)rand.nextInt(20), 1.0f, -1 * (float)rand.nextInt(20));
            MeshRenderer mesh = go.addComponent(MeshRenderer.class);
            mesh.model = sampleModel;
            world.add(go);
        }
    }
}
