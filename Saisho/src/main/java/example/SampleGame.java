package example;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.SaishoGame_dep;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.scratch.Transform;
import com.saishostudios.saisho.core.scratch.WASDControllerComponent;

import static com.saishostudios.saisho.core.constants.SaishoKeys.*;

public class SampleGame extends SaishoGame {
    private GameObject player = new GameObject();
    @Override
    public void init() {
        player.withTag("player");
        world.add(player);
        Transform transform = player.addComponent(Transform.class);
        transform.randomFloat = 5.0f;
        player.addComponent(WASDControllerComponent.class);

        // Called after engine is done initalizing

        logger.log("Everything well");
    }

    @Override
    public void update(float dt) {
        // Called every frame
        if(inputManager.keys[KEY_W]){
            //logger.log("Key pressed");
        }
        setWindowTitle(player.getComponent(Transform.class).randomFloat + "");
    }

    @Override
    public void fixedUpdate(float dt) {
        // Called at fixed interval
    }

    public static void main(String[] args){
        SampleGame game = new SampleGame();
        game.start();
    }
}
