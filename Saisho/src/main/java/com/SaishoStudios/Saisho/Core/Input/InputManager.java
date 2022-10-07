package com.SaishoStudios.Saisho.Core.Input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class InputManager {
    public Vector2f lastMouse = new Vector2f();
    public boolean mouseInWindow = false;
    private boolean firstMouse = true;
    public Vector2f mouseDelta = new Vector2f();
    public boolean[] keys = new boolean[65535];

    public boolean[] mouseButtons = new boolean[3];
    public void resetMouseDelta(){
        mouseDelta.x = 0;
        mouseDelta.y = 0;
    }
    public void init(long windowHandle){
        //register callbacks
        GLFW.glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> {

            if(firstMouse && mouseInWindow) {
                lastMouse.x = (float)xpos;
                lastMouse.y = (float)ypos;
                firstMouse = false;
            }
            mouseDelta.x = lastMouse.x - (float)xpos;
            mouseDelta.y = lastMouse.y - (float)ypos;
            lastMouse.x = (float)xpos;
            lastMouse.y = (float)ypos;


        });
        GLFW.glfwSetCursorEnterCallback(windowHandle, (window, entered) -> {
            mouseInWindow = entered;
            if(!mouseInWindow){
                firstMouse = true;
            }
        });
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            mouseButtons[0] = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            mouseButtons[1] = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        });
        GLFW.glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) ->{
            keys[key] = action != GLFW.GLFW_RELEASE;
        });

    }
}
