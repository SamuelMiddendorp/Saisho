package example;

import com.saishostudios.saisho.core.SaishoGame;
import com.saishostudios.saisho.core.SaishoGame_dep;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.scratch.Transform;
import com.saishostudios.saisho.core.scratch.WASDControllerComponent;

import static com.saishostudios.saisho.core.constants.SaishoKeys.KEY_W;

public class SampleGameDep extends SaishoGame_dep {
    private GameObject player = new GameObject();
    @Override
    public void init() {
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
        SampleGameDep game = new SampleGameDep();
        game.start();
    }
}
