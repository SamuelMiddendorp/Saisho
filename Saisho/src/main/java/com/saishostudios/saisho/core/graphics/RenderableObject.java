package com.saishostudios.saisho.core.graphics;

import com.saishostudios.saisho.core.components.Transform;

public class RenderableObject {
    public RawModel model;
    public Transform transform;

    public RenderableObject(RawModel model, Transform transform) {
        this.model = model;
        this.transform = transform;
    }
}
