package Gui.Components;

import Game.tools.GameResourcesAndSettings;
import Gui.Gui;
import Gui.GuiLayout;
import Gui.TextRendering.Text;
import VecMath.Vector2f;

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
		super.constraintsUpdated();
		Vector2f normalised = super.getCoordinates();
		
		text.changePos(normalised);
	}
	
}
