package example;

import com.saishostudios.saisho.core.SaishoGame_dep;
import com.saishostudios.saisho.core.scratch.GameObject;

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
