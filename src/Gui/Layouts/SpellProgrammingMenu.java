package Gui.Layouts;

import Game.tools.GameResourcesAndSettings;
import Game.tools.Input;
import Gui.Gui;

import Gui.GuiManager;
import Gui.Components.SpellEditor.GuiSpellConnector;
import Gui.Components.SpellEditor.GuiSpellGrid;
import Gui.Components.SpellEditor.GuiSpellNode;
import Gui.Components.SpellEditor.GuiSpellNodeSelector;
import Gui.Constraints.AspectConstraint;
import Gui.Constraints.ScaleConstraint;
import VecMath.Vector2f;

public class SpellProgrammingMenu extends Gui{

	private GuiSpellGrid grid;
	private GuiSpellConnector selectedConnector;
	private GuiSpellNodeSelector selector;
	
	private Gui bg;
	
	public SpellProgrammingMenu()
	{
		super(GameResourcesAndSettings.GUI_DARK_GREY, null);
		bg = new Gui(GameResourcesAndSettings.GUI_DARK_GREY, this);
		bg.addScaleConstraint(new ScaleConstraint(1f, ScaleConstraint.WIDTH));
		bg.addAspectConstraint(new AspectConstraint(1));
		bg.setShowFocusStatus(false);

		bg.show();
		
		grid = new GuiSpellGrid(this);
		selectedConnector = null;
		
		selector = new GuiSpellNodeSelector(this, grid);
	}
	
	@Override
	public void update()
	{
		if(Input.isKeyPressed('e') && bg.hasFocus())
		{
			GuiManager.loadPlayingLayout();
		}
		
		grid.update();
		
		manageSelectedConnector();
	}
	
	private void manageSelectedConnector()
	{
		if(isConnectorSelected())
		{
			Vector2f mousePos = Input.getMousePosition();
			this.selectedConnector.setP2(mousePos);
			
			if(Input.isKeyPressed('t'))
			{
				GuiSpellConnector c = this.selectedConnector;
				this.selectedConnector = new GuiSpellConnector(mousePos, mousePos, c.getType(), c.getWidth(), this, c.getInputNodePointer());
			}

			if(Input.isMouseButtonPressed(1))
			{
				removeCurrentConnector();
			}
		}
	}
	
	public void showSelector(Vector2f pos)
	{
		selector.show(pos);
	}
	
	public void setSelectedConnector(GuiSpellConnector c)
	{
		this.selectedConnector = c;
	}
	
	public void removeCurrentConnector()
	{
		if(isConnectorSelected())
		{
			this.selectedConnector.hide();
			this.selectedConnector = null;
		}
	}
	
	public boolean isConnectorSelected()
	{
		return (this.selectedConnector != null);
	}
	
	public void setSelectedConnectorOutput(int outputNode, int portIndex)
	{
		if(isConnectorSelected())
		{
			this.selectedConnector.setOutputNode(outputNode, portIndex);
		}
	}
	
	public void setSelectedConnectorP2(Vector2f p2)
	{
		this.selectedConnector.setP2(p2);
	}
	
	public GuiSpellNode getNodeFromPtr(int ptr)
	{
		return grid.getNodeFromPtr(ptr);
	}

	public boolean isBackgroundFocused() { return bg.hasFocus(); }
	
}
