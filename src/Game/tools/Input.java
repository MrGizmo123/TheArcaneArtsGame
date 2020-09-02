package Game.tools;

import java.util.ArrayList;
import java.util.List;

import Game.Logging.Logger;
import Game.Render.DisplayManager;
import VecMath.Vector2f;
import org.lwjgl.glfw.*;

public class Input {
	
	private static boolean[] keysPressed;
	private static boolean[] keysReleased;
	
	private static boolean[] mousePressed;
	private static boolean[] mouseReleased;

	private static boolean[] mouseButtonStatus;

	private static int mouseX;
	private static int mouseY;

	private static int mouseDX;
	private static int mouseDY;

	private static float dWheel;

	private static GLFWKeyCallback keyCallback;
	private static GLFWMouseButtonCallback mouseButtonCallback;
	private static GLFWCursorPosCallback cursorPosCallback;
	private static GLFWScrollCallback scrollCallback;
	
	public static void init()
	{
		keysPressed = new boolean[256];
		keysReleased = new boolean[256];
		mousePressed = new boolean[3];
		mouseReleased = new boolean[3];
		mouseButtonStatus = new boolean[3];
		mouseX = 0;
		mouseY = 0;
		dWheel = 0;
		
		clearArrays();

		setupGLFWCallbacks();
	}

	private static void setupGLFWCallbacks()
	{
		keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if(action == GLFW.GLFW_PRESS)
				{
					keysPressed[key] = true;
				}
				if(action == GLFW.GLFW_RELEASE)
				{
					keysReleased[key] = true;
				}
			}
		};

		mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				if(action == GLFW.GLFW_PRESS)
				{
					mousePressed[button] = true;
					mouseButtonStatus[button] = true;
				}
				else if(action == GLFW.GLFW_RELEASE) {
					mouseReleased[button] = true;
					mouseButtonStatus[button] = false;
				}
			}
		};

		cursorPosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				int newMouseX = (int) xpos;
				int newMouseY = (int) (DisplayManager.HEIGHT - ypos);

				mouseDX = newMouseX - mouseX;
				mouseDY = newMouseY - mouseY;

				mouseX = newMouseX;
				mouseY = newMouseY;
			}
		};

		scrollCallback = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				dWheel = (float) yoffset;
			}
		};
	}

	public static void update() 
	{
		clearArrays();

		resetDMouse();
	}

	private static void resetDMouse() {

		mouseDX = 0;
		mouseDY = 0;
		dWheel = 0;

	}

	public static int[] getKeysPressed()
	{
		List<Integer> resultList = new ArrayList<Integer>();
		
		for(int i=0;i<keysPressed.length;i++)
		{
			if(keysPressed[i])
			{
				resultList.add(i);
			}
		}
		
		return toArray(resultList);
	}
	
	public static boolean isMouseButtonPressed(int button)
	{
		return mousePressed[button];
	}
	
	public static boolean isMouseButtonReleased(int button)
	{
		return mouseReleased[button];
	}

	public static boolean isMouseButtonDown(int button) { return mouseButtonStatus[button]; }

	public static boolean mouseMoved()
	{
		return ((mouseDX != 0) || (mouseDY != 0));
	}

	public static Vector2f getMousePosition()
	{
		return new Vector2f(mouseX, mouseY);
	}

	public static Vector2f getNormalisedMousePosition()
	{
		return Maths.viewportToNormalised(new Vector2f(mouseX, mouseY));
	}
	
	public static boolean isKeyPressed(char key)
	{
		return keysPressed[key];
	}
	
	public static boolean isKeyReleased(char key)
	{
		return keysReleased[key];
	}

	public static int getMouseDX()
	{
		return mouseDX;
	}

	public static int getMouseDY()
	{
		return mouseDY;
	}

	public static float getMouseDWheel()
	{
		return dWheel;
	}

	public static GLFWKeyCallback getKeyCallback()
	{
		return keyCallback;
	}

	public static GLFWMouseButtonCallback getMouseButtonCallback()
	{
		return mouseButtonCallback;
	}

	public static GLFWCursorPosCallback getCursorPosCallback()
	{
		return cursorPosCallback;
	}

	public static GLFWScrollCallback getScrollCallback()
	{
		return scrollCallback;
	}

	private static void clearArrays()
	{
		for(int i=0;i<256;i++)
		{
			keysPressed[i] = false;
			keysReleased[i] = false;
		}
		
		for(int i=0;i<3;i++)
		{
			mousePressed[i] = false;
			mouseReleased[i] = false;
		}
		
	}
	
	private static int[] toArray(List<Integer> list)
	{
		int[] result = new int[list.size()];
		
		for(int i=0;i<result.length;i++)
		{
			result[i] = list.get(i);
		}
		
		return result;
	}
	
}
