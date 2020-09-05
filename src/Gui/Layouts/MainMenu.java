package Gui.Layouts;

import Game.tools.GameResourcesAndSettings;
import Game.tools.Input;
import Gui.Constraints.*;

import Game.Game;

import Gui.Constraints.Properties.NotFocusable;
import Gui.Gui;
import Gui.GuiManager;
import Gui.Components.GuiButton;
import Gui.Components.GuiLabel;
import Gui.Transitions.Drivers.LinearDriver;
import Gui.Transitions.Transition;
import org.lwjgl.glfw.GLFW;

public class MainMenu extends Gui{

	private GuiButton play;

	public MainMenu()
	{
		super(GameResourcesAndSettings.GUI_TRANSPARENT, null);
		
		GuiLabel title = new GuiLabel("The Arcane Arts!", 3f, this);
		title.addPosConstraint(new RelativePosConstraint(0.5f,0.8f));

		title.setFocusMode(new NotFocusable());

		play = new GuiButton("Play", 2f, this) {
			@Override
			public void clicked() {
				GuiManager.loadPlayingLayout();
			}
		};
		
		play.addScaleConstraint(new RelativeAspectConstraint(0.3f, 1.77f));
		play.addPosConstraint(new RelativePosConstraint(0.5f,0.5f));



		play.setHideTransition(new Transition(0.5f).setXDriver(new LinearDriver(0f, 0.1f)));
		play.setShowTransition(new Transition(0.5f).setXDriver(new LinearDriver(0.1f, 0f)));


		GuiButton quit = new GuiButton("Quit", 2f, this) {
			@Override
			public void clicked() {
				Game.closeGame();
			}
		};
		
		quit.addScaleConstraint(new RelativeAspectConstraint(0.3f, 1.77f));
		quit.addPosConstraint(new RelativePosConstraint(0.5f,0.277f));
		
		super.addGui(play);
		super.addGui(quit);
		super.addGui(title);
		
		Game.freezeGameInput();

		/*GuiButton g = new GuiButton("public static", 1f, this);
		g.addXPosConstraint(new AbsolutePositionConstraint(0.5f));
		g.addYPosConstraint(new AbsolutePositionConstraint(0.5f));
		//g.addAspectConstraint(new AspectConstraint(1));*/

		//this.addGui(g);
		
	}
	
	@Override
	public void update()
	{
		super.update();

		if(Input.isKeyPressed(GLFW.GLFW_KEY_H))
		{
			play.hide();
		}
		else if(Input.isKeyPressed(GLFW.GLFW_KEY_S))
		{
			play.show();
		}

	}
	
}
