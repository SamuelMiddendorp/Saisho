package com.saishostudios.saisho.core;

import com.saishostudios.saisho.core.scratch.Component;
import com.saishostudios.saisho.core.scratch.GameObject;
import com.saishostudios.saisho.core.utils.Maths;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

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
    protected List<GameObject> world = new ArrayList<GameObject>();
    protected final SaishoLogger logger = new SaishoLogger();

    protected final InputManager inputManager = new InputManager();
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
        logger.log("Initalizing systems");
        initializeGLFW();
        logger.log("Hello LWJGL " + Version.getVersion() + "!");
        inputManager.init(window);
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
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.6f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glLineWidth(8.0f);
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);
        Camera camera = new TopDownCamera();
        camera.move(new Vector3f(0f, 0f, 0));
        Loader loader = new Loader();
        RawModel floor = OBJLoader.loadObjModel("main/models/floor", loader);
        RawModel model = OBJLoader.loadObjModel("main/models/dragon", loader);
        RawModel tile = OBJLoader.loadObjModel("main/models/tile", loader);
        MousePicker mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
        Entity floorEnt = new Entity(floor, new Vector3f(0.0f, 0.0f, 0.0f),1);
        Entity player = new Entity(model
                ,new Vector3f(-0.0f,1.0f,0),0.5f);
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        Vector3f ray = mousePicker.getCurrentRay();
        List<RawModel> linesToDraw = new ArrayList<>();
        List<Entity> entities = new ArrayList<>(createEntityGrid(tile));

        while ( !glfwWindowShouldClose(window) ) {
            inputManager.resetMouseDelta();

            DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, xBuffer, yBuffer);
            double x = xBuffer.get(0);
            double y = yBuffer.get(0);
            mousePicker.setMouseCoords(x,y);
            mousePicker.getCurrentRay();
            shader.start();
            shader.loadLightPos(new Vector3f((float)Math.sin(glfwGetTime()) * 10, 3.0f, -5.0f));
            shader.loadViewMatrix(camera);
            //player.setRotY((float)Math.sin(glfwGetTime()) * 360);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            //player.setPosition(new Vector3f(0.0f, inputManager.mouseDelta.y, 0.0f);
            //player.setPosition(new Vector3f((float)Math.sin(glfwGetTime())*10,  0.0f, -(float)Math.sin(glfwGetTime())*10));
            //renderer.render(floorEnt, shader);
            for (GameObject go : world) {
                for (Component component : go.getComponents()) {
                    component.onUpdate(0.01f);
                }
            }
            update(0.01f);
            //renderer.renderDebugLines(lineLook);
            renderer.render(player, shader);
            shader.stop();
            glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
        shader.cleanUp();
        loader.cleanUp();
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
