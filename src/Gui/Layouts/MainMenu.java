package Gui.Layouts;

import org.lwjgl.input.Keyboard;

import Game.Game;

import Gui.GuiLayout;
import Gui.GuiManager;
import Gui.Components.Clickable;
import Gui.Components.GuiButton;
import Gui.Components.GuiLabel;
import Gui.Constraints.AspectConstraint;
import Gui.Constraints.RelativePositionConstraint;
import Gui.Constraints.ScaleConstraint;

public class MainMenu extends GuiLayout{

	public MainMenu()
	{
		super();
		
		GuiLabel title = new GuiLabel("The Arcane Arts!", 1f, this);
		title.addYPosConstraint(new RelativePositionConstraint(0.833f, RelativePositionConstraint.HEIGHT));

		title.setFocusable(false);

		GuiButton play = new GuiButton("Play", 0.75f, this) {
			@Override
			public void clicked() {
				GuiManager.loadPlayingLayout();
			}
		};
		
		play.addScaleConstraint(new ScaleConstraint(0.2f, ScaleConstraint.WIDTH));
		play.addAspectConstraint(new AspectConstraint(1.77f));
		
		GuiButton quit = new GuiButton("Quit", 0.75f, this) {
			@Override
			public void clicked() {
				GuiManager.loadPlayingLayout();
			}
		};
		
		quit.addScaleConstraint(new ScaleConstraint(0.2f, ScaleConstraint.WIDTH));
		quit.addAspectConstraint(new AspectConstraint(1.77f));
		quit.addYPosConstraint(new RelativePositionConstraint(0.277f, RelativePositionConstraint.HEIGHT));
		
		super.addGui(play);
		super.addGui(quit);
		super.addGui(title);
		
		Game.freezeGameInput();
		
	}
	
	@Override
	protected void updateLayout()
	{
		while(Keyboard.next());
	}
	
}
