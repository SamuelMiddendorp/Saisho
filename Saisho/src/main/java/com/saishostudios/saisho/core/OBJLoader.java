package com.saishostudios.saisho.core;

import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.graphics.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {
    private static Loader _loader;

    public static void setLoader(Loader loader) {
        _loader = loader;
    }
    public static RawModel loadObjModel(String fileName, Loader loader) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("src/" + fileName + ".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file!");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> rawvertices = new ArrayList<Vector3f>();
        List<Vector3f> rawcolors = new ArrayList<Vector3f>();
        List<Vector2f> rawtextures = new ArrayList<Vector2f>();
        List<Vector3f> rawnormals = new ArrayList<Vector3f>();
        List<Integer> rawindices = new ArrayList<Integer>();

        float[] verticesArray = null;
        float[] colorsArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;

        try {

            while (true) {
                line = reader.readLine();
                if(line == null)
                    break;
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    rawvertices.add(vertex);
                    if(currentLine.length > 4) {
                        Vector3f color = new Vector3f(Float.parseFloat(currentLine[4]),
                                Float.parseFloat(currentLine[5]), Float.parseFloat(currentLine[6]));
                        rawcolors.add(color);
                    }
                    else{
                        rawcolors.add(new Vector3f(1.0f, 0,0));
                    }
                } else if (line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));
                    rawtextures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    rawnormals.add(normal);
                } else if (line.startsWith("f ")) {
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    rawindices.add(Integer.parseInt(vertex1[0]));
                    rawindices.add(Integer.parseInt(vertex1[1]));
                    rawindices.add(Integer.parseInt(vertex1[2]));

                    rawindices.add(Integer.parseInt(vertex2[0]));
                    rawindices.add(Integer.parseInt(vertex2[1]));
                    rawindices.add(Integer.parseInt(vertex2[2]));

                    rawindices.add(Integer.parseInt(vertex3[0]));
                    rawindices.add(Integer.parseInt(vertex3[1]));
                    rawindices.add(Integer.parseInt(vertex3[2]));

                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[rawindices.size()];
        int vertI = 0;
        colorsArray = new float[rawindices.size()];
        int colI = 0;
        indicesArray = new int[rawindices.size()/3];
        int indiceI = 0;
        textureArray = new float[(rawindices.size()/3) * 2];
        int texI = 0;
        normalsArray = new float[rawindices.size()]; // * 3 / 3
        int normI = 0;

        for(int i = 0; i < rawindices.size();i+=0) {
            int ind = rawindices.get(i++)-1;
            Vector3f coord = rawvertices.get(ind);
            Vector3f color = rawcolors.get(ind);
            Vector2f tex = rawtextures.get(rawindices.get(i++)-1);
            Vector3f norm = rawnormals.get(rawindices.get(i++)-1);

            indicesArray[indiceI] = indiceI;
            indiceI++;

            verticesArray[vertI++] = coord.x;
            verticesArray[vertI++] = coord.y;
            verticesArray[vertI++] = coord.z;

            colorsArray[colI++] = color.x;
            colorsArray[colI++] = color.y;
            colorsArray[colI++] = color.z;


            textureArray[texI++] = tex.x;
            textureArray[texI++] = tex.y;

            normalsArray[normI++] = norm.x;
            normalsArray[normI++] = norm.y;
            normalsArray[normI++] = norm.z;

        }


        return loader.loadToVao(verticesArray, colorsArray, normalsArray, indicesArray);
    }
    public static RawModel loadObjModelInstanced(String fileName, List<Vector3f> offsets) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("src/" + fileName + ".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file!");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> rawvertices = new ArrayList<Vector3f>();
        List<Vector3f> rawcolors = new ArrayList<Vector3f>();
        List<Vector2f> rawtextures = new ArrayList<Vector2f>();
        List<Vector3f> rawnormals = new ArrayList<Vector3f>();
        List<Integer> rawindices = new ArrayList<Integer>();

        float[] verticesArray = null;
        float[] colorsArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;

        try {

            while (true) {
                line = reader.readLine();
                if(line == null)
                    break;
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    rawvertices.add(vertex);
                    if(currentLine.length > 4) {
                        Vector3f color = new Vector3f(Float.parseFloat(currentLine[4]),
                                Float.parseFloat(currentLine[5]), Float.parseFloat(currentLine[6]));
                        rawcolors.add(color);
                    }
                    else{
                        rawcolors.add(new Vector3f(1.0f, 0,0));
                    }
                } else if (line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));
                    rawtextures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    rawnormals.add(normal);
                } else if (line.startsWith("f ")) {
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    rawindices.add(Integer.parseInt(vertex1[0]));
                    rawindices.add(Integer.parseInt(vertex1[1]));
                    rawindices.add(Integer.parseInt(vertex1[2]));

                    rawindices.add(Integer.parseInt(vertex2[0]));
                    rawindices.add(Integer.parseInt(vertex2[1]));
                    rawindices.add(Integer.parseInt(vertex2[2]));

                    rawindices.add(Integer.parseInt(vertex3[0]));
                    rawindices.add(Integer.parseInt(vertex3[1]));
                    rawindices.add(Integer.parseInt(vertex3[2]));

                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[rawindices.size()];
        int vertI = 0;
        colorsArray = new float[rawindices.size()];
        int colI = 0;
        indicesArray = new int[rawindices.size()/3];
        int indiceI = 0;
        textureArray = new float[(rawindices.size()/3) * 2];
        int texI = 0;
        normalsArray = new float[rawindices.size()]; // * 3 / 3
        int normI = 0;

        for(int i = 0; i < rawindices.size();i+=0) {
            int ind = rawindices.get(i++)-1;
            Vector3f coord = rawvertices.get(ind);
            Vector3f color = rawcolors.get(ind);
            Vector2f tex = rawtextures.get(rawindices.get(i++)-1);
            Vector3f norm = rawnormals.get(rawindices.get(i++)-1);

            indicesArray[indiceI] = indiceI;
            indiceI++;

            verticesArray[vertI++] = coord.x;
            verticesArray[vertI++] = coord.y;
            verticesArray[vertI++] = coord.z;

            colorsArray[colI++] = color.x;
            colorsArray[colI++] = color.y;
            colorsArray[colI++] = color.z;


            textureArray[texI++] = tex.x;
            textureArray[texI++] = tex.y;

            normalsArray[normI++] = norm.x;
            normalsArray[normI++] = norm.y;
            normalsArray[normI++] = norm.z;

        }


        return _loader.loadToVaoInstanced(verticesArray, colorsArray, normalsArray, indicesArray, Renderer.generateVertices(offsets));
    }
    public static RawModel loadObjModel(String fileName) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("src/" + fileName + ".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file!");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> rawvertices = new ArrayList<Vector3f>();
        List<Vector3f> rawcolors = new ArrayList<Vector3f>();
        List<Vector2f> rawtextures = new ArrayList<Vector2f>();
        List<Vector3f> rawnormals = new ArrayList<Vector3f>();
        List<Integer> rawindices = new ArrayList<Integer>();

        float[] verticesArray = null;
        float[] colorsArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;

        try {

            while (true) {
                line = reader.readLine();
                if(line == null)
                    break;
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    rawvertices.add(vertex);
                    if(currentLine.length > 4) {
                        Vector3f color = new Vector3f(Float.parseFloat(currentLine[4]),
                                Float.parseFloat(currentLine[5]), Float.parseFloat(currentLine[6]));
                        rawcolors.add(color);
                    }
                    else{
                        rawcolors.add(new Vector3f(1.0f, 0,0));
                    }
                } else if (line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));
                    rawtextures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    rawnormals.add(normal);
                } else if (line.startsWith("f ")) {
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    rawindices.add(Integer.parseInt(vertex1[0]));
                    rawindices.add(Integer.parseInt(vertex1[1]));
                    rawindices.add(Integer.parseInt(vertex1[2]));

                    rawindices.add(Integer.parseInt(vertex2[0]));
                    rawindices.add(Integer.parseInt(vertex2[1]));
                    rawindices.add(Integer.parseInt(vertex2[2]));

                    rawindices.add(Integer.parseInt(vertex3[0]));
                    rawindices.add(Integer.parseInt(vertex3[1]));
                    rawindices.add(Integer.parseInt(vertex3[2]));

                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[rawindices.size()];
        int vertI = 0;
        colorsArray = new float[rawindices.size()];
        int colI = 0;
        indicesArray = new int[rawindices.size()/3];
        int indiceI = 0;
        textureArray = new float[(rawindices.size()/3) * 2];
        int texI = 0;
        normalsArray = new float[rawindices.size()]; // * 3 / 3
        int normI = 0;

        for(int i = 0; i < rawindices.size();i+=0) {
            int ind = rawindices.get(i++)-1;
            Vector3f coord = rawvertices.get(ind);
            Vector3f color = rawcolors.get(ind);
            Vector2f tex = rawtextures.get(rawindices.get(i++)-1);
            Vector3f norm = rawnormals.get(rawindices.get(i++)-1);

            indicesArray[indiceI] = indiceI;
            indiceI++;

            verticesArray[vertI++] = coord.x;
            verticesArray[vertI++] = coord.y;
            verticesArray[vertI++] = coord.z;

            colorsArray[colI++] = color.x;
            colorsArray[colI++] = color.y;
            colorsArray[colI++] = color.z;


            textureArray[texI++] = tex.x;
            textureArray[texI++] = tex.y;

            normalsArray[normI++] = norm.x;
            normalsArray[normI++] = norm.y;
            normalsArray[normI++] = norm.z;

        }


        return _loader.loadToVao(verticesArray, colorsArray, normalsArray, indicesArray);
    }



}
