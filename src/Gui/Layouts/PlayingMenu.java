package Gui.Layouts;

import Game.tools.GameResourcesAndSettings;

import Game.tools.Input;
import Gui.Gui;
import Gui.GuiManager;
import org.lwjgl.glfw.GLFW;

public class PlayingMenu extends Gui{

	public PlayingMenu()
	{
		super(GameResourcesAndSettings.GUI_TRANSPARENT, null);
	}
	
	@Override
	public void update()
	{
		if(Input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE))
		{
			GuiManager.loadMainMenu();
		}
	}
	
}
