package Gui.Layouts;

import Game.tools.GameResourcesAndSettings;
import Gui.Constraints.*;
import org.lwjgl.input.Keyboard;

import Game.Game;

import Gui.Gui;
import Gui.GuiManager;
import Gui.Components.GuiButton;
import Gui.Components.GuiLabel;

import javax.management.monitor.GaugeMonitor;

public class MainMenu extends Gui{

	public MainMenu()
	{
		super(GameResourcesAndSettings.GUI_TRANSPARENT, null);
		
		/*GuiLabel title = new GuiLabel("The Arcane Arts!", 1f, this);
		title.addYPosConstraint(new RelativePositionConstraint(0.1f));

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
		quit.addYPosConstraint(new RelativePositionConstraint(0.277f));
		
		super.addGui(play);
		super.addGui(quit);
		super.addGui(title);
		
		Game.freezeGameInput();*/

		GuiButton g = new GuiButton("public static", 1f, this);
		g.addXPosConstraint(new AbsolutePositionConstraint(0.5f));
		g.addYPosConstraint(new AbsolutePositionConstraint(0.5f));
		//g.addAspectConstraint(new AspectConstraint(1));

		this.addGui(g);
		
	}
	
	@Override
	public void update()
	{
		super.update();
		while(Keyboard.next());
	}
	
}
