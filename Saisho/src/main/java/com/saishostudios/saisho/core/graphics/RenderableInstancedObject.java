package com.saishostudios.saisho.core.graphics;

import com.saishostudios.saisho.core.components.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class RenderableInstancedObject {
    public RawModel model;
    public Transform transform;
    public float[] offsets;

    public RenderableInstancedObject(RawModel model, Transform transform, float[] offsets) {
        this.model = model;
        this.transform = transform;
        this.offsets = offsets;
    }
}
