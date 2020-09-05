package Gui.Components;

import Game.Logging.Logger;
import Game.Render.DisplayManager;

import Game.tools.GameResourcesAndSettings;
import Game.tools.Input;
import Game.tools.utils.AABB;
import Gui.Gui;

import Gui.TextRendering.Text;
import VecMath.Vector2f;

public class GuiButton extends Gui {

	private AABB boundingBox;
	private Text text;
	
	private int normal_texture;
	private int hover_texture;
	
	private Vector2f textOffset;

	private boolean isPressed = false;
	
	public GuiButton(String t, float scale, Gui parent)
	{
		super(GameResourcesAndSettings.GUI_GREY, parent);
		this.boundingBox = calculateAABB();
		
		normal_texture = GameResourcesAndSettings.GUI_GREY;
		hover_texture = GameResourcesAndSettings.GUI_LIGHT_GREY;
		
		textOffset = new Vector2f(0,0);
		
		this.text = new Text(t, scale,GameResourcesAndSettings.GAME_FONT, this.getCoordinates(), true);
		//positionText();
		
		super.addText(text);
	}
	
	public GuiButton(String t, float scale, int texture, Vector2f textOffset, Gui parent)
	{
		super(GameResourcesAndSettings.GUI_GREY, parent);
		this.boundingBox = calculateAABB();
		this.textOffset = textOffset;
		
		normal_texture = texture;
		hover_texture = GameResourcesAndSettings.GUI_LIGHT_GREY;
		
		this.text = new Text(t, scale,GameResourcesAndSettings.GAME_FONT, new Vector2f(), true);
		positionText();
		
		super.addText(text);
	}
	
	public GuiButton(String t, float scale, int texture, Gui parent)
	{
		super(texture, parent);
		this.boundingBox = calculateAABB();
		
		normal_texture = texture;
		hover_texture = GameResourcesAndSettings.GUI_LIGHT_GREY;
		
		textOffset = new Vector2f(0,0);
		
		this.text = new Text(t, scale,GameResourcesAndSettings.GAME_FONT, new Vector2f(), true);
		positionText();
		
		super.addText(text);
	}
	
	public GuiButton(int texture, Gui parent)
	{
		super(texture, parent);
		this.boundingBox = calculateAABB();
		
		normal_texture = texture;
		hover_texture = GameResourcesAndSettings.GUI_LIGHT_GREY;
		
		textOffset = new Vector2f(0,0);
		
		this.text = new Text("", 1, GameResourcesAndSettings.GAME_FONT, new Vector2f(), false);
	}
	
	public GuiButton(int texture, Gui parent, Vector2f textOffset)
	{
		super(texture, parent);
		this.boundingBox = calculateAABB();
		this.textOffset = textOffset;
		
		normal_texture = texture;
		hover_texture = GameResourcesAndSettings.GUI_LIGHT_GREY;
		
		this.text = new Text("", 1, GameResourcesAndSettings.GAME_FONT, new Vector2f(), false);
	}

	@Override
	protected void windowResized() {
		super.windowResized();

		recalculateAABB();
	}

	@Override
	protected void constraintsUpdated() 
	{
		super.constraintsUpdated();
		positionText();
		recalculateAABB();
	}
	
	@Override
	public void update()
	{
		super.update();
		super.texture = normal_texture;
		
		Vector2f mousePos = Input.getNormalisedMousePosition();
		if(boundingBox.isIntersecting(mousePos))
		{
			super.texture = hover_texture;
			if (Input.isMouseButtonPressed(0)) {
				clicked();
				isPressed = true;
			}
			else{
				isPressed = false;
			}
		}
		else {
			isPressed = false;
		}
	}
	
	private void positionText()
	{
		Vector2f normalised = super.getCoordinates();
		Vector2f translatedPos = new Vector2f(normalised.x, normalised.y + (text.getMesh().getHeight() / 4));
		
		text.changePos(translatedPos);
	}
	
	private void recalculateAABB()
	{
		boundingBox = calculateAABB();
	}
	
	private AABB calculateAABB() 
	{
		Vector2f scale = super.getScale();
		Vector2f pos = super.getCoordinates();
		
		float widthInPixels = (scale.x / DisplayManager.aspectRatio);
		float heightInPixels = (scale.y);
		
		float x1 = (pos.x - (widthInPixels / (float)2));
		float y1 = (pos.y - (heightInPixels / (float)2));
		
		float x2 = (pos.x + (widthInPixels / (float)2));
		float y2 = (pos.y + (heightInPixels / (float)2));
		
		return new AABB(x1, y1, x2, y2);
		
	}

	public boolean isPressed()
	{
		return isPressed;
	}
	
	@Override
	public void show()
	{
		super.show();
		//this.addText(text);
	}
	
	@Override
	public void hide()
	{
		super.hide();
		//this.removeText(text);
	}

	public void clicked() {

	}
}
