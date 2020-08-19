package Gui.Components;

import org.lwjgl.util.vector.Vector2f;

import Game.tools.GameResourcesAndSettings;
import Gui.Gui;
import Gui.GuiLayout;
import Gui.TextRendering.Text;

public class GuiLabel extends Gui{

	private Text text;
	
	public GuiLabel(String text, float size, Gui parent)
	{
		super(GameResourcesAndSettings.GUI_TRANSPARENT, parent);
		
		Vector2f viewport = super.getPositionInViewPort();
		
		this.text = new Text(text, size, GameResourcesAndSettings.GAME_FONT, super.getPositionInNDC(), true);
	
		super.addText(this.text);
	}
	
	@Override
	protected void constraintsUpdated()
	{
		Vector2f normalised = super.getCoordinates();
		
		text.changePos(normalised);
	}
	
}
