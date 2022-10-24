package com.saishostudios.saisho.core;

import com.saishostudios.saisho.core.components.Component;
import com.saishostudios.saisho.core.components.MeshRenderer;
import com.saishostudios.saisho.core.constants.PrefabType;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Maths;
import com.saishostudios.saisho.core.utils.Prefab;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.lang.reflect.Field;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.saishostudios.saisho.core.graphics.RawModel;
import com.saishostudios.saisho.core.graphics.Renderer;
import com.saishostudios.saisho.core.graphics.StaticShader;
import com.saishostudios.saisho.core.input.InputManager;

import static com.saishostudios.saisho.core.constants.Saisho.*;


public abstract class SaishoGame{
    protected World world = new World();
    protected final SaishoLogger logger = new SaishoLogger();

    protected final InputManager inputManager = new InputManager();

    protected float cameraSpeed = 1;

    protected float fixedUpdateStep = 0.05f;
    protected Camera camera;
    private long window;
    protected void setWindowTitle(String value){
        glfwSetWindowTitle(window, value);
    }
    public SaishoGame(){

    }
    public void start(){
        initializeCoreSystems();
    }
    public void initializeCoreSystems(){
        // Set world of GameObject, might be slow because its using reflection but its only done once.
        try {
            Field f = GameObject.class.getDeclaredField("world");
            f.setAccessible(true);
            try {
                f.set(null, world);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        OBJLoader.setLoader(new Loader());
        logger.log("Initalizing systems");
        initializeGLFW();
        logger.log("Hello LWJGL " + Version.getVersion() + "!");
        inputManager.init(window);
        camera = new Camera();
        init();
        loop();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    private void initializeGLFW() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);
        GL.createCapabilities();
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        // Set the clear color
        //glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.1f, 0.0f);
        glEnable(GL_DEPTH_TEST);

        glLineWidth(8.0f);
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);
        Loader loader = new Loader();
        MousePicker mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        Vector3f ray = mousePicker.getCurrentRay();
        List<RawModel> linesToDraw = new ArrayList<>();
        float fixedUpdateTimer = 0f;
        float dt = 0.1f;
        double lastTime = glfwGetTime();
        GameObject light = new GameObject();
        light.addComponent(MeshRenderer.class).model = Prefab.create(PrefabType.CUBE);
        world.add(light);
        while ( !glfwWindowShouldClose(window) ) {

            dt = (float)(glfwGetTime() - lastTime);
            fixedUpdateTimer += dt;
            lastTime = glfwGetTime();
            inputManager.resetMouseDelta();
            if(inputManager.keys[GLFW_KEY_W]){
                camera.move(new Vector3f(0.0f, 0.0f, -cameraSpeed * dt));

            }
            if(inputManager.keys[GLFW_KEY_S]){
                camera.move(new Vector3f(0.0f, 0.0f, cameraSpeed * dt));
            }
            if(inputManager.keys[GLFW_KEY_A]){
                camera.move(new Vector3f(-cameraSpeed * dt, 0.0f, 0.0f));
            }
            if(inputManager.keys[GLFW_KEY_D]){
                camera.move(new Vector3f(cameraSpeed * dt, 0.0f, 0.0f));
            }
            if(inputManager.keys[GLFW_KEY_E]){
                camera.move(new Vector3f(0.0f, cameraSpeed * dt, 0.0f));
            }
            if(inputManager.keys[GLFW_KEY_Q]){
                camera.move(new Vector3f(0.0f, -cameraSpeed * dt, 0.0f));
            }
            DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, xBuffer, yBuffer);
            double x = xBuffer.get(0);
            double y = yBuffer.get(0);
            mousePicker.setMouseCoords(x,y);
            mousePicker.getCurrentRay();
            //camera.getPosition().x +=1;
            shader.start();
            light.transform.position = new Vector3f((float)Math.sin(glfwGetTime()) * 10, 3.0f, 0.0f);
            shader.loadLightPos(light.transform.position);
            shader.loadViewMatrix(camera);
            //camera.move(new Vector3f(50f * dt, 0,-50f * dt));
            //player.setRotY((float)Math.sin(glfwGetTime()) * 360);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            //player.setPosition(new Vector3f(0.0f, inputManager.mouseDelta.y, 0.0f);
            //player.setPosition(new Vector3f((float)Math.sin(glfwGetTime())*10,  0.0f, -(float)Math.sin(glfwGetTime())*10));
            //renderer.render(floorEnt, shader);
            if(fixedUpdateTimer > fixedUpdateStep){
                fixedUpdate(dt);
                fixedUpdateTimer = 0f;
            }
            for (GameObject go : world.getGameObjects()) {
                for (Component component : go.getComponents()) {
                    component.onUpdate(dt);
                }
            }
            update(dt);

            render();
            //renderer.renderDebugLines(lineLook);
            //renderer.render(player, shader);
            shader.stop();
            double f = glfwGetTime();
            glfwSwapBuffers(window); // swap the color buffers
            //System.out.println(("rendering time" + (glfwGetTime() - f) *1000));
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            //glFinish();
        }
        shader.cleanUp();
        loader.cleanUp();
    }
    private void render(){
        Renderer.render();
    }
    private float map(float a, float b, float c, float d, float value){
        return (value - a) * ((d - c) / (b - a)) + c;

    }

    private Vector3f getIntersectingPlane(Vector3f cameraPos, Vector3f ray){
        Vector3f planeNormal = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f pointOnPlane = new Vector3f(2.0f, 0.0f, 4.0f);
        Vector3f v = new Vector3f(ray);
        Vector3f w = new Vector3f(Maths.subtractV3(pointOnPlane, cameraPos));
        float k = w.dot(planeNormal)/v.dot(planeNormal);
        Vector3f intersect =  Maths.addV3(cameraPos,v.mul(k));
        //glfwSetWindowTitle(window, intersect.x + " " + intersect.y + " " + intersect.z);
        return intersect;
    }
    private List<Entity> createEntityGrid(RawModel model){
        List<Entity> entities = new ArrayList<>();
        for(int x = 0; x < 30; x++){
            for(int z = -30; z < 0; z++) {
                entities.add(new Entity(model, new Vector3f(x, 0.0f, z), 1));
            }
        }
        return entities;
    }

    public abstract void init();
    public abstract void update(float dt);
    public abstract void fixedUpdate(float dt);
}
