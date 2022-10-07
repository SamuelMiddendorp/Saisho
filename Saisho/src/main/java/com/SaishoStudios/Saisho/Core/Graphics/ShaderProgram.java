package com.SaishoStudios.Saisho.Core.Graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public ShaderProgram(String vertexFile, String fragmentFile){
        vertexShaderID = loadShader(vertexFile, GL30.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL30.GL_FRAGMENT_SHADER);
        programID = GL30.glCreateProgram();
        GL30.glAttachShader(programID, vertexShaderID);
        GL30.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL30.glLinkProgram(programID);
        GL30.glValidateProgram(programID);
        getAllUniformLocations();
    }
    public void setUniformMatrix(int location, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            GL30.glUniformMatrix4fv(location, false, fb);
        }
    }
    protected abstract void getAllUniformLocations();
    protected int getUniformLocation(String uniform){
        return GL30.glGetUniformLocation(programID, uniform);
    }
    protected void loadFloat(int location, float value){
        GL30.glUniform1f(location, value);
    }
    protected void loadVector(int location, Vector3f value){
        GL30.glUniform3f(location, value.x, value.y, value.z);
    }
    public void  start(){
        GL30.glUseProgram(programID);
    }

    public void stop(){
        GL30.glUseProgram(0);
    }

    public void cleanUp(){
        stop();
        GL30.glDetachShader(programID, vertexShaderID);
        GL30.glDetachShader(programID, fragmentShaderID);
        GL30.glDeleteShader(vertexShaderID);
        GL30.glDeleteShader(fragmentShaderID);
        GL30.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        GL30.glBindAttribLocation(programID, attribute, variableName);
    }

    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL30.glCreateShader(type);
        GL30.glShaderSource(shaderID, shaderSource);
        GL30.glCompileShader(shaderID);
        if(GL30.glGetShaderi(shaderID, GL30.GL_COMPILE_STATUS )== GL30.GL_FALSE){
            System.out.println(GL30.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

}
