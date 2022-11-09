package com.saishostudios.saisho.core.components;
import com.saishostudios.saisho.core.SaishoLogger;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Maths;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;

public class RigidBody extends Component{
    public float mass = 1;
    public float gravity = 0.1f;
    public boolean onGround = false;
    public boolean isStatic = false;
    public Vector3f velocity;

    public Vector3f acceleration;
    @Override
    public void onUpdate(float deltaTime) {

        if(!isStatic) {

            //gameObject.transform.position.add(velocity.mul(deltaTime, new Vector3f()));
            acceleration.y -= gravity;

            onGround = false;
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
                                // COllision check hierboven is nog een beetje basic
                                for(CollisionListener cl : go.getCollisionListeners()){
                                    cl.onCollide(gameObject);
                                }
                                if (gameObject.transform.position.y <= go.transform.position.y + otherBoundingBox.h * 2) {
                                    gameObject.transform.position.y = go.transform.position.y + otherBoundingBox.h * 2;
                                    onGround = true;
                                    go.setFlag("touched", true);
                                    velocity.y = 0;

                                }
                            }
                        }
                    }
                }

            }
        }
        velocity.add(acceleration);
        gameObject.transform.position.add(velocity.mul(deltaTime,new Vector3f()));
        acceleration.x = 0;
        acceleration.y = 0;
        acceleration.z = 0;
    }

    @Override
    public void onStart() {
        velocity = new Vector3f();
        acceleration = new Vector3f();
    }
}
