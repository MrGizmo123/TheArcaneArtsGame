package Gui.Components.SpellEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Game.Render.DisplayManager;
import Game.Spell.SpellNodeType;
import Game.tools.GameResourcesAndSettings;
import Gui.Gui;
import Gui.GuiLayout;

import Gui.Components.GuiButton;
import Gui.Constraints.AbsolutePositionConstraint;
import Gui.Constraints.AspectConstraint;
import Gui.Constraints.ScaleConstraint;
import VecMath.Vector2f;

public class GuiSpellNodeSelector extends Gui {

	private static final float TEXT_SIZE = 0.3f;
	private static final int padding = 10;
	private static final float buttonSpacing = 1.1f;
	
	private List<GuiButton> nodeTypes;
	private GuiSpellGrid grid;
	
	public GuiSpellNodeSelector(Gui parent, GuiSpellGrid g)
	{
		super(GameResourcesAndSettings.GUI_GREY, parent);
		nodeTypes = new ArrayList<>();
		grid = g;
		
		createGuis();
	}
	
	@Override
	public void focusLost()
	{
		hide();
	}

	@Override
	public void update() {
		super.update();

		//System.out.println(this.parentLayout.getGuis().contains(this));
	}

	public void show(Vector2f pos)
	{
		super.show();
		
		positionGuis(pos);
	}
	
	@Override
	public void hide()
	{
		this.parent.removeGui(this);

		//System.out.println(this.parentLayout.getGuis().contains(this));
		
		for(GuiButton g : nodeTypes)
		{
			g.hide();
		}
	}
	
	private void positionGuis(Vector2f pos)
	{
		float maxHeight = GameResourcesAndSettings.GAME_FONT.getMaxHeight();
		
		float topY = pos.y;
		float bottomY = pos.y - (TEXT_SIZE * (nodeTypes.size()-1) * maxHeight * buttonSpacing);
		
		float center = (topY + bottomY) / 2;
		
		this.addXPosConstraint(new AbsolutePositionConstraint((int) pos.x));
		this.addYPosConstraint(new AbsolutePositionConstraint((int) center));
		
		int i = 0;
		for(GuiButton g : nodeTypes)
		{
			g.addXPosConstraint(new AbsolutePositionConstraint((int) pos.x));
			g.addYPosConstraint(new AbsolutePositionConstraint((int) (pos.y - (TEXT_SIZE * i * maxHeight * buttonSpacing))));
			
			g.show();
			i++;
		}
	}
	
	private void createGuis()
	{
		List<SpellNodeType> types = new ArrayList<>(Arrays.asList(SpellNodeType.values()));
		
		// constants required to get scale of guis
		int maxStringLength = getMaxNodeNameLength(types);
		
		float maxHeight = GameResourcesAndSettings.GAME_FONT.getMaxHeight();
		float maxWidth = GameResourcesAndSettings.GAME_FONT.getMaxWidth();
		
		float buttonWidth = TEXT_SIZE * maxStringLength * maxWidth / (float)DisplayManager.WIDTH;
		float buttonAspect = maxStringLength * maxWidth / (maxHeight);
		
		float bgHeight = (TEXT_SIZE * maxHeight * (float)types.size() * buttonSpacing + (float)padding) / (float)DisplayManager.HEIGHT;
		float bgWidth = (TEXT_SIZE * maxStringLength * maxWidth + (float)padding) / (float)DisplayManager.WIDTH;
		
		// scale constraints for selector bg gui
		this.addScaleConstraint(new ScaleConstraint(bgWidth, ScaleConstraint.WIDTH));
		this.addAspectConstraint(new AspectConstraint(bgWidth/bgHeight * DisplayManager.aspectRatio));

		this.setShowFocusStatus(false);

		for(SpellNodeType type : types) 
		{
			GuiButton b = new GuiButton(type.name(), TEXT_SIZE, GameResourcesAndSettings.GUI_DARK_GREY, super.parent) {
				@Override
				public void clicked() {
					grid.addNode(type);
					hide();
				}
			};
			b.addScaleConstraint(new ScaleConstraint(buttonWidth, ScaleConstraint.WIDTH));
			b.addAspectConstraint(new AspectConstraint(buttonAspect));
			
			b.hide();

			b.setFocusable(false);
			
			nodeTypes.add(b);
		}
	}
	
	private int getMaxNodeNameLength(List<SpellNodeType> types)
	{
		int maxStringLength = 0;
		for(SpellNodeType type : types) 
		{
			if(type.name().length() > maxStringLength)
			{
				maxStringLength = type.name().length();
			}
		}
		return maxStringLength;
	}

}
