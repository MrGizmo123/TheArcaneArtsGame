package Gui.Layouts;

import Game.tools.GameResourcesAndSettings;

import Game.tools.Input;
import Gui.Gui;
import Gui.GuiManager;

public class PlayingMenu extends Gui{

	public PlayingMenu()
	{
		super(GameResourcesAndSettings.GUI_TRANSPARENT, null);
	}
	
	@Override
	public void update()
	{
		if(Input.isKeyPressed('e'))
		{
			GuiManager.loadSpellEditMenu();
		}
		
		if(Input.isKeyPressed('q'))
		{
			GuiManager.loadMainMenu();
		}
	}
	
}
