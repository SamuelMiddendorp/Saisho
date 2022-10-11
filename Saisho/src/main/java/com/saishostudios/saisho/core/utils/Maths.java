package com.saishostudios.saisho.core.utils;

import com.saishostudios.saisho.core.Camera;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static com.saishostudios.saisho.core.constants.Saisho.*;

public class Maths {
    public static Matrix4f createTransformationMatrix(Vector3f translation, Quaternionf rotation, float scale){
        Matrix4f matrix = new Matrix4f();

        matrix.translate(translation);
        matrix.scale(new Vector3f(scale, scale, scale));
        matrix.rotate(rotation.normalize());

        return matrix;
    }
    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }
    public static Matrix4f createViewMatrix(Vector3f pos, Vector3f  front, Vector3f up) {
        return new Matrix4f().lookAt(pos, pos.add(front, new Vector3f()), up);
    }
    public static Quaternionf mul(Quaternionf m, Vector3f r)
    {
        float w = -m.x * r.x - m.y * r.y - m.z * r.z;
        float x =  m.w * r.x + m.y * r.z - m.z * r.y;
        float y =  m.w * r.y + m.z * r.x- m.x * r.z;
        float z =  m.w * r.z + m.x * r.y - m.y * r.x;

        return new Quaternionf(x, y, z, w);
    }
    public static Vector3f rotate(Quaternionf quat, Vector3f rotation)
    {
        Quaternionf conjugate = quat.conjugate(new Quaternionf());

        Quaternionf w = mul(quat, rotation).mul(conjugate, new Quaternionf());

        return new Vector3f(w.x, w.y, w.z);
    }
    public static Matrix4f createOrthographicProjection(){
        return new Matrix4f().ortho(-20, 20f, -20f,20f, NEAR_PLANE , FAR_PLANE);
    }
    public static Vector3f invertV3(Vector3f vec) {
        return new Vector3f(vec).mul(-1f);
    }
    public static Vector3f subtractV3(Vector3f left, Vector3f right){
        return new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z);
    }
    public static Vector3f crossV3(Vector3f left, Vector3f right){
        return new Vector3f(left).cross(right);
    }
    public static Vector3f mulScalarV3(Vector3f left, float scalar){
        return new Vector3f(left.x * scalar, left.y * scalar, left.z * scalar);
    }
    public static Vector3f addV3(Vector3f left, Vector3f right) {
        return new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z);
    }

    public static Matrix4f OffSetMatrix(Matrix4f matrix, Vector3f offset) {
        return matrix.translate(offset);

    }
}
