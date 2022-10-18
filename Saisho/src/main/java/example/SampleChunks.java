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

public class SampleChunks extends SaishoGame {
    private int displayCounter = 0;

    @Override
    public void init() {
        cameraSpeed = 50f;
        var offsets = createOffsets(100000, 100);
        RawModel sampleModel = OBJLoader.loadObjModelInstanced("main/models/multiple", offsets);
        GameObject player = new GameObject();
        InstancedMeshRenderer mesh = player.addComponent(InstancedMeshRenderer.class);
        mesh.model = sampleModel;
        mesh.offSets = offsets;
        world.add(player);
        camera.move(new Vector3f(0.0f, 1.0f, 10f));

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
        SampleChunks game = new SampleChunks();
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
}
