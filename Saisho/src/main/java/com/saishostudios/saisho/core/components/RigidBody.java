package com.saishostudios.saisho.core.components;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Maths;
import org.joml.Vector3f;

public class RigidBody extends Component{
    public float mass = 1;
    public float gravity = 12f;
    private boolean onGround = false;
    public boolean isStatic = false;
    public Vector3f velocity;
    @Override
    public void onUpdate(float deltaTime) {
        if(!isStatic) {

            gameObject.transform.position.add(velocity.mul(deltaTime, new Vector3f()));
            gameObject.transform.position.y -= gravity * deltaTime;
            var boundingBox = gameObject.getComponent(BoxCollider.class);
            if (boundingBox != null) {
                for (GameObject go : GameObject.world.getGameObjects()) {
                    if (go != gameObject) {
                        var otherBoundingBox = go.getComponent(BoxCollider.class);
                        if (otherBoundingBox != null) {
                            if (Maths.aabb(gameObject.transform.position.x - boundingBox.w,
                                    gameObject.transform.position.y - boundingBox.h,
                                    gameObject.transform.position.z - boundingBox.l,
                                    gameObject.transform.position.x + boundingBox.w,
                                    gameObject.transform.position.y + boundingBox.h,
                                    gameObject.transform.position.z + boundingBox.l,
                                    go.transform.position.x - otherBoundingBox.w,
                                    go.transform.position.y - otherBoundingBox.h,
                                    go.transform.position.z - otherBoundingBox.l,
                                    go.transform.position.x + otherBoundingBox.w,
                                    go.transform.position.y + otherBoundingBox.h,
                                    go.transform.position.z + otherBoundingBox.l)) {
//                                if (gameObject.transform.position.x > go.transform.position.x) {
//                                    gameObject.transform.position.x = go.transform.position.x + otherBoundingBox.w * 2;
//                                }
                                if (gameObject.transform.position.y > go.transform.position.y) {
                                    gameObject.transform.position.y = go.transform.position.y + otherBoundingBox.h * 2;
                                    onGround = true;
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    @Override
    public void onStart() {
        velocity = new Vector3f();
    }
}
