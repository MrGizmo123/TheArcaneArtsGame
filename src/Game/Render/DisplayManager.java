package Game.Render;


import Game.tools.Input;
import com.sun.source.tree.WildcardTree;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class DisplayManager {

	private static long window;

	public static float aspectRatio;

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

	private static boolean wasResized;
	private static boolean isGLContextCreated;
	
	public static void createDisplay(){
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		isGLContextCreated = false;

		wasResized = false;
		aspectRatio = (float)WIDTH / (float)HEIGHT;

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		//glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);

		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, "The Arcane Arts", NULL, NULL);
		if ( window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, Input.getKeyCallback());
		glfwSetMouseButtonCallback(window, Input.getMouseButtonCallback());
		glfwSetCursorPosCallback(window, Input.getCursorPosCallback());
		glfwSetScrollCallback(window, Input.getScrollCallback());
		glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				updateWidthAndHeight();
			}
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

		GL.createCapabilities();
		isGLContextCreated = true;
	}

	private static void updateWidthAndHeight()
	{
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer b = BufferUtils.createIntBuffer(1);

		glfwGetWindowSize(window, w, b);

		if(WIDTH != w.get() || HEIGHT != b.get())
		{
			wasResized = true;
		}
		else {
			wasResized = false;
		}


		WIDTH = w.get(0);
		HEIGHT = b.get(0);

		aspectRatio = (float)WIDTH / (float)HEIGHT;

		if(isGLContextCreated)
			GL11.glViewport(0,0, WIDTH, HEIGHT);
	}
	
	public static void updateDisplay(){
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	public static boolean isCloseRequested()
	{
		return glfwWindowShouldClose(window);
	}

	public static boolean wasDisplayResized()
	{
		return wasResized;
	}

	public static void closeDisplay(){
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
}
