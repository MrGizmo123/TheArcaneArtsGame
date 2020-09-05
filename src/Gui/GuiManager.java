package Gui;

import Game.Game;

import Gui.Layouts.MainMenu;
import Gui.Layouts.PlayingMenu;

public class GuiManager {

	private static Gui activeLayout;
	
	private static MainMenu mainMenu;
	private static PlayingMenu playingMenu;
	
	public static void init()
	{
		mainMenu = new MainMenu();
		playingMenu = new PlayingMenu();
		
		activeLayout = mainMenu;
		Game.freezeGameInput();
	}
	
	public static void update()
	{
		activeLayout.update();
	}
	
	//change functions
	
	public static void loadPlayingLayout()
	{
		activeLayout = playingMenu;
		Game.unFreezeGameInput();
	}
	
	public static void loadMainMenu()
	{
		activeLayout = mainMenu;
		Game.freezeGameInput();
	}

	public static Gui getActiveLayout()
	{
		return activeLayout;
	}
	
}
