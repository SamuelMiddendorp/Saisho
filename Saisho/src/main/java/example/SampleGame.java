package example;

import com.SaishoStudios.Saisho.Core.SaishoGame;

import static com.SaishoStudios.Saisho.Core.Constants.SaishoKeys.*;

public class SampleGame extends SaishoGame {

    @Override
    public void init() {
        // Called after engine is done initalizing
        setWindowTitle("My first game");
        logger.log("Everything well");
    }

    @Override
    public void update(float dt) {
        // Called every frame
        if(inputManager.keys[KEY_W]){
            logger.log("Key pressed");
        }
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
