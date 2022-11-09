package com.saishostudios.saisho.core.scratch;

import com.saishostudios.saisho.core.World;
import com.saishostudios.saisho.core.components.CollisionListener;
import com.saishostudios.saisho.core.components.Component;
import com.saishostudios.saisho.core.components.Transform;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObject {
    public static World world;
    private String tag = "";

    private Map<String, Boolean> flags = new HashMap<>();

    public Transform transform;
    private List<Component> components = new ArrayList<>();
    public GameObject(){
        transform = new Transform();
        this.components.add(transform);
    }
    public <T extends Component> T getComponent(Class<T> component) {
        for (Component comp : components) {
            if (comp.getClass() == component) {
                return (T) comp;
            }
        }
        return null;
    }
    public List<CollisionListener> getCollisionListeners(){
        List<CollisionListener> listeners = new ArrayList<>();
        for(Component comp: components){
            if(comp instanceof CollisionListener){
                listeners.add((CollisionListener)comp);
            }
        }
        return listeners;
    }
    public List<Component> getComponents() {
        return components;
    }

    //    public <T extends Component> void addComponent(T component){
//        components.add(component);
//    }
    public <T extends Component> T addComponent(Class<T> component){

        T comp = null;
        try {
            comp = component.getDeclaredConstructor().newInstance();
            comp.setGameObject(this);
            components.add(comp);
            comp.onStart();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return comp;
    }
    public void setFlag(String flag, boolean value){
        flags.put(flag, value);
    }
    public boolean getFlag(String flag){
        return flags.get(flag);
    }
    public void withTag(String value){
        tag = value;
    }
    public static GameObject findByTag(String tag){
        for(GameObject go : world.getGameObjects()){
            if(go.tag == tag){
                return go;
            }
        }
        return null;
    }
}
