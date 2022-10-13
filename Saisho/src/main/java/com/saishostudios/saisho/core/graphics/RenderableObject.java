package com.saishostudios.saisho.core.graphics;

import com.saishostudios.saisho.core.scratch.Transform;
import org.joml.Vector3f;

public class RenderableObject {
    public RawModel model;
    public Transform transform;

    public RenderableObject(RawModel model, Transform transform) {
        this.model = model;
        this.transform = transform;
    }
}
