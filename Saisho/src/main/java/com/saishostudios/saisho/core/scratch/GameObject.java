package com.saishostudios.saisho.core.scratch;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private List<Component> components = new ArrayList<>();

    public <T extends Component> T getComponent(Class<T> component) {
        for (Component comp : components) {
            if (comp.getClass() == component) {
                return (T) comp;
            }
        }
        return null;
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
}
