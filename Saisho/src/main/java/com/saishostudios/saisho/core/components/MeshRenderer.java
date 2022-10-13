package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.graphics.Renderer;

public class MeshRenderer extends Component{
    public RawModel model = OBJLoader.loadObjModel("main/models/cube");
    private boolean isInited = false;
    @Override
    public void onUpdate(float deltaTime) {
        if(!isInited){
            Renderer.submit(model, gameObject.transform);
            isInited = true;
        }
    }
    @Override
    public void onStart() {
    }
}
