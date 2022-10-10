package com.SaishoStudios.Saisho.Core;

import org.joml.Vector2f;
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

import com.SaishoStudios.Saisho.Core.Graphics.RawModel;
import com.SaishoStudios.Saisho.Core.Graphics.Renderer;
import com.SaishoStudios.Saisho.Core.Graphics.StaticShader;
import com.SaishoStudios.Saisho.Core.Input.InputManager;
import com.SaishoStudios.Saisho.Core.Utils.Maths;
import static com.SaishoStudios.Saisho.Core.Constants.Saisho.*;


public abstract class SaishoGame {
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
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", glfwGetPrimaryMonitor(), NULL);
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


            if(inputManager.keys[GLFW_KEY_W]){
                //player.increasePosition(0.1f, 0.0f, -0.1f);
                //player.increasePosition(0.1f, 0.0f, -0.1f);
                camera.move(new Vector3f(0.1f, 0.0f, -0.1f));
                //player.setRotY(135f);

            }
            if(inputManager.keys[GLFW_KEY_S]){
                //player.setRotY(-45f);
                //camera.increaseYaw(0.2f);
                //player.increasePosition(-0.1f, 0.0f, 0.1f);
                camera.move(new Vector3f(-0.1f, 0.0f, 0.1f));
                //camera.move(Maths.invertV3(camera.getM_cameraFront()));
            }
            if(inputManager.keys[GLFW_KEY_A]){
                //player.setRotY(-135f);
                camera.move(new Vector3f(-0.05f, 0.0f, -0.05f));
                player.increasePosition(-0.05f, 0.0f, -0.05f);
            }
            if(inputManager.keys[GLFW_KEY_D]){
                //camera.increaseYaw(0.2f);
                //player.setRotY(45f);
                camera.move(new Vector3f(0.05f, 0.0f, 0.05f));
                player.increasePosition(0.05f, 0.0f, 0.05f);
            }
            if(inputManager.keys[GLFW_KEY_E]){
                //camera.increaseYaw(0.2f);
                camera.move(new Vector3f(0.0f, 0.05f, 0.0f));
            }
            if(inputManager.keys[GLFW_KEY_Q]){
                //camera.increaseYaw(0.2f);
                camera.increasePitch(0.005f);
            }
            if(inputManager.keys[GLFW_KEY_L]){
                renderer.changePerspective();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(inputManager.keys[GLFW_KEY_B]){
                float[] positions = mousePicker.calculateOrthoRay();
                String foo = "";
                for(float pos: positions){
                    foo = foo.concat(Math.round(pos) + "_");
                }
                glfwSetWindowTitle(window, foo);
            }
            if(inputManager.keys[GLFW_KEY_M]){
                player.lookAt(new Vector3f(10.0f, 0.0f, -10.0f));
            }
            if(inputManager.keys[2]){
                //camera.move(new Vector3f(0.0f, 0.05f, 0.0f));
                camera.increasePitch(-0.2f);
                //camera.increaseYaw(-0.2f);
            }
            if(inputManager.keys[3]){
                //camera.move(new Vector3f(0.0f, 0.05f, 0.0f));
                camera.move(new Vector3f(0.0f, -0.1f, 0.0f));
                //camera.increaseYaw(-0.2f);
            }
            if(inputManager.keys[GLFW_KEY_P]) {
                Vector3f intersection = getIntersectingPlane(camera.getPosition(), ray);
                System.out.println(intersection.x + " " + intersection.z);
                int posX = (intersection.x > 0) ? (int) intersection.x : (int) intersection.x - 1;
                int posZ = (intersection.z > 0) ? (int) intersection.z : (int) intersection.z - 1;
                Entity entity = new Entity(tile, new Vector3f(posX, 0.0f, posZ), 1f);
                entities.add(entity);
            }
            if(inputManager.mouseButtons[0]){
                System.out.println(camera.getPosition());
                float[] verticescam = mousePicker.calculateOrthoRay();
                float[] verticesOrtho = {
                        verticescam[0],verticescam[1],verticescam[2],
                        verticescam[0] + verticescam[3] * 1000,verticescam[1] + verticescam[4] * 1000,verticescam[2] + verticescam[5] * 1000
                };
//                float[] vertices = {
//
//                        camera.getPosition().x, camera.getPosition().y, camera.getPosition().z,
//                        camera.getPosition().x + ray.x * 1000, camera.getPosition().y + ray.y * 1000, camera.getPosition().z  + ray.z * 1000
//                };
//                float[] toOrigin = {
//                        ray.x * 100, ray.y * -100, ray.z * 100,
//                        0.0f, 0.0f, 0.0f
//                };
                float[] verticesCamera = {
                        camera.getPosition().x, camera.getPosition().y, camera.getPosition().z,
                        0.0f, 0.0f, 0.0f
                };
                float[] verticesSimple = {
                        0.0f, 4.0f, 5.0f,
                        4.0f, 3.0f, 0.0f
                };
                Vector3f intersection = getIntersectingPlane(new Vector3f(verticescam[0],verticescam[1],verticescam[2]), new Vector3f(verticescam[3], verticescam[4], verticescam[5]));
                RawModel line = loader.loadToVao(verticesOrtho);
                int posX = (intersection.x > 0) ? (int) intersection.x + 1 : (int) intersection.x - 1;
                int posZ = (intersection.z > 0) ? (int) intersection.z + 1 : (int) intersection.z - 1;
                if(posX <= 30 && posZ < 30){
                    // entities.get(30 * posX + posZ).setScale(0.0000001f);
                }

                linesToDraw.add(line);

            }
            if(inputManager.mouseButtons[1]){
                camera.increaseYaw(inputManager.mouseDelta.x / 4);
                camera.increasePitch(inputManager.mouseDelta.y / 4);
            }



            inputManager.resetMouseDelta();

            DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, xBuffer, yBuffer);
            double x = xBuffer.get(0);
            double y = yBuffer.get(0);
            mousePicker.setMouseCoords(x,y);
            float[] verticescam = mousePicker.calculateOrthoRay();
            Vector3f intersection = getIntersectingPlane(new Vector3f(verticescam[0],verticescam[1],verticescam[2]), new Vector3f(verticescam[3], verticescam[4], verticescam[5]));
            //intersection = new Vector3f(15, 0, -15);
            player.lookAt(intersection);
            //player.setRotation(new Vector3f(0, 1, 0), (float)Math.toRadians(Math.max(Math.sin(glfwGetTime()), 0) * 360));
            //setPlayerRotation(player, intersection);
            //glfwSetWindowTitle(window, ray.x + " " + ray.y + " " + ray.z);
            //player.setPosition(new Vector3f((int)intersection.x, 0.0f, (int)intersection.z));
            shader.start();
            shader.loadLightPos(new Vector3f((float)Math.sin(glfwGetTime()) * 10, 3.0f, -5.0f));
            shader.loadViewMatrix(camera);
            //player.setRotY((float)Math.sin(glfwGetTime()) * 360);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            //player.setPosition(new Vector3f(0.0f, inputManager.mouseDelta.y, 0.0f);
            //player.setPosition(new Vector3f((float)Math.sin(glfwGetTime())*10,  0.0f, -(float)Math.sin(glfwGetTime())*10));
            //renderer.render(floorEnt, shader);
            for (Entity entity: entities) {
                renderer.render(entity, shader);
                //entity.increaseRotation(0.2f, 0.2f, 0.2f);
            }
            for (RawModel line: linesToDraw){
                renderer.renderDebugLines(line);
            }
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
