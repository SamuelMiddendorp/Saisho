package com.saishostudios.saisho.core.components;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Maths;
import org.joml.Vector3f;

public class RigidBody extends Component{
    public float mass = 1;
    public Vector3f velocity;
    @Override
    public void onUpdate(float deltaTime) {

            gameObject.transform.position.add(velocity.mul(deltaTime, new Vector3f()));
            var boundingBox = gameObject.getComponent(BoxCollider.class);
            if(boundingBox != null){
                for(GameObject go: GameObject.world.getGameObjects()){
                    if(go != gameObject){
                        var otherBoundingBox = go.getComponent(BoxCollider.class);
                        if(otherBoundingBox != null){
                            if(Maths.aabb(gameObject.transform.position.x - boundingBox.w,
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
                                    go.transform.position.z + otherBoundingBox.l)){
                                go.transform.position.x = 10f;
                            }
                        }
                    }
                }
            }


    }

    private void collideMotherFuckers() {
    }

    @Override
    public void onStart() {
        velocity = new Vector3f();
    }
}
