package com.saishostudios.saisho.core.utils;

import com.saishostudios.saisho.core.Loader;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.graphics.RawModel;
import org.joml.Vector3f;

import static com.saishostudios.saisho.core.constants.Saisho.COLOR_36;

public class Prefab {
    // Front Left Back Right Top Bottom
    private static final int[] aabbIndices = {
            0,1,2,
            0,2,3,

            0,4,7,
            0,3,7,

            4,5,6,
            4,6,7,

            1,5,6,
            1,2,6,

            0,1,4,
            0,1,5,

            0,6,7,
            0,2,6
    };
    private static final float[] aabbNormals = {

            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,

            0.0f,0.0f,-1.0f,
            0.0f,0.0f,-1.0f,
            0.0f,0.0f,-1.0f,
            0.0f,0.0f,-1.0f,
            0.0f,0.0f,-1.0f,
            0.0f,0.0f,-1.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
    };
    public static RawModel create(PrefabType prefabType){
        switch (prefabType){
            case CUBE -> {
                return createUnitCube();
            }
            default -> {
                return createUnitCube();
            }
        }
    }
    public static RawModel create(PrefabType type, Vector3f dims){
        return null;
    }
    private static RawModel createUnitCube(){
        var vertexPositions = new float[aabbIndices.length * 3];
        var vx = new float[]{
                -0.5f,  0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                -0.5f,  0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f
        };
        for(var x = 0; x < aabbIndices.length; x++){
            vertexPositions[x * 3] = vx[aabbIndices[x]*3];
            vertexPositions[x * 3 + 1] = vx[aabbIndices[x]*3 + 1];
            vertexPositions[x * 3 + 2] = vx[aabbIndices[x]*3 + 2];
        }
        return Loader.loadToVao(vertexPositions, COLOR_36, aabbNormals, aabbIndices);
    }
    private static RawModel createCube(float scale){
        return null;
    }
}
