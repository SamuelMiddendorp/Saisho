package com.saishostudios.saisho.core.components;

import com.saishostudios.saisho.core.OBJLoader;
import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.graphics.Renderer;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class InstancedMeshRenderer extends Component{
    public RawModel model = OBJLoader.loadObjModel("main/models/cube");
    public List<Vector3f> offSets = new ArrayList<>();
    private boolean isInited = false;
    @Override
    public void onUpdate(float deltaTime) {
        if(!isInited){
            //Renderer.submit(model, gameObject.transform);
            Renderer.submit(model, gameObject.transform, Renderer.generateVertices(offSets));
            isInited = true;
        }
    }
    @Override
    public void onStart() {
    }
}
