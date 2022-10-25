package com.saishostudios.saisho.core.utils;

import com.saishostudios.saisho.core.Loader;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.graphics.RawModel;
import org.joml.Vector3f;

import static com.saishostudios.saisho.core.constants.Saisho.COLOR_36;

public class Prefab {
    // Front Left Back Right Top Bottom
//    private static final int[] aabbIndices = {
//            0,1,2,
//            0,2,3,
//
//            0,4,7,
//            0,3,7,
//
//            4,5,6,
//            4,6,7,
//
//            1,5,6,
//            1,2,6,
//
//            0,1,4,
//            0,1,5,
//
//            0,6,7,
//            0,2,6
//    };
    private static final float[] aabbNormals = {

            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,

            0.0f,0.0f, 1.0f,
            0.0f,0.0f, 1.0f,
            0.0f,0.0f, 1.0f,
            0.0f,0.0f, 1.0f,
            0.0f,0.0f, 1.0f,
            0.0f,0.0f, 1.0f,

            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,


            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,

            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
    };
    private static final float[] unitCube = {
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                -0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,

                -0.5f, -0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,

                -0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,

                0.5f,  0.5f,  0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,

                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f, -0.5f,

                -0.5f,  0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                0.5f,  0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f, -0.5f,
    };
    public static RawModel create(PrefabType prefabType, Vector3f dims){
        switch (prefabType){
            case RECT -> {
                return createRect(dims.x, dims.y, dims.z);
            }
            default -> {
                return createUnitCube();
            }
        }
    }
    public static RawModel createRect(float width, float height, float length){
        var x = width / 2;
        var y = height /2;
        var z = length /2;
        float[] model = {
                -x, -y, -z,
                x, -y, -z,
                x,  y, -z,
                x,  y, -z,
                -x,  y, -z,
                -x, -y, -z,

                -x, -y, z,
                x, -y,  z,
                x,  y,  z,
                x,  y,  z,
                -x,  y,  z,
                -x, -y,  z,

                -x,  y,  z,
                -x,  y, -z,
                -x, -y, -z,
                -x, -y, -z,
                -x, -y,  z,
                -x,  y,  z,

                x,  y,  z,
                x,  y, -z,
                x, -y, -z,
                x, -y, -z,
                x, -y,  z,
                x,  y,  z,

                -x, -y, -z,
                x, -y, -z,
                x, -y,  z,
                x, -y,  z,
                -x, -y,  z,
                -x, -y, -z,

                -x,  y, -z,
                x,  y, -z,
                x,  y,  z,
                x,  y,  z,
                -x,  y,  z,
                -x,  y, -z,
        };
        return Loader.loadToVao(model, COLOR_36, aabbNormals);
    }
    private static RawModel createUnitCube(){
        return Loader.loadToVao(unitCube, COLOR_36, aabbNormals);
    }
    private static RawModel createCube(float scale){
        return null;
    }
}
