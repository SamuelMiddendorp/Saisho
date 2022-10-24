package com.saishostudios.saisho.core;

import com.saishostudios.saisho.core.graphics.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private static List<Integer> vaos = new ArrayList<Integer>();
    private static List<Integer> vbos = new ArrayList<Integer>();

    public static RawModel loadToVao(float[] positions, float[] colors, float[] normals, int[] indices) {
        int vaoID = createVAO();
        //bindIndicesBuffer(indices);
        storeDataInAttributesList(0, positions);
        storeDataInAttributesList(1, colors);
        storeDataInAttributesList(2, normals);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }
    public static RawModel loadToVaoInstanced(float[] positions, float[] colors, float[] normals, int[] indices, float[] offsets) {
        int vaoID = createVAO();
        //bindIndicesBuffer(indices);
        storeDataInAttributesList(0, positions);
        storeDataInAttributesList(1, colors);
        storeDataInAttributesList(2, normals);
        storeDataInAttributesList(3, offsets);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public RawModel loadToVao(float[] positions){
        int vaoID = createVAO();
        storeDataInAttributesList(0, positions);
        return new RawModel(vaoID, positions.length);
    }
    public void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL30.glDeleteBuffers(vbo);
        }
    }

    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;

    }

    public static void storeDataInAttributesList(int attributeNumber, float[] data) {
        int vboID = GL30.glGenBuffers();
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(attributeNumber, 3, GL30.GL_FLOAT, false, 0, 0);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }

    private static void unbindVAO() {
        GL30.glBindVertexArray(0);
    }
    private static void bindIndicesBuffer(int[] indices){
        int vboID = GL30.glGenBuffers();
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
    }
    private static IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    private static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
