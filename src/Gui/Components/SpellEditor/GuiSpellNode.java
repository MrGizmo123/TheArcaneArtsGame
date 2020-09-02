package Gui.Components.SpellEditor;

import Game.Render.DisplayManager;
import Game.tools.Input;
import Gui.Components.Draggable;
import Gui.Gui;

import Game.Spell.SpellDataType;
import Game.Spell.SpellNodeType;
import Game.tools.GameResourcesAndSettings;
import Gui.GuiLayout;

import Gui.Components.GuiButton;
import Gui.Constraints.AbsolutePositionConstraint;
import Gui.Constraints.AspectConstraint;
import Gui.Constraints.ScaleConstraint;
import Gui.Layouts.SpellProgrammingMenu;
import VecMath.Vector2f;

public class GuiSpellNode extends GuiButton implements Draggable {

	private static final float width = 0.075f;
	
	private SpellNodeType type;
	private int pointer;
	
	// the pointer var will contain the pointer to this node
	public GuiSpellNode(SpellNodeType type, int pointer, Gui parent)
	{
		super(type.name(), 0.3f, GameResourcesAndSettings.GUI_GREY, new Vector2f(0, 50), parent);
		this.type = type;
		this.pointer = pointer;
		
		createGuis();
	}

	@Override
	public void update() {
		super.update();

		if(super.isPressed())
		{
			Vector2f mousePos = Input.getMousePosition();

			//dragged((int)mousePos.x, (int)mousePos.y);
		}
	}

	private void createGuis()
	{
		this.addScaleConstraint(new ScaleConstraint(width, ScaleConstraint.WIDTH));
		this.addAspectConstraint(new AspectConstraint(1));
			
		this.addXPosConstraint(new AbsolutePositionConstraint(Input.getMousePosition().x));
		this.addYPosConstraint(new AbsolutePositionConstraint(Input.getMousePosition().y));
			
		show();
		
		Vector2f nodePos = Input.getMousePosition();
		addInputs(type.getNoOfInputs(), nodePos, width);
		addOutputs(type.getNoOfOutputs(), nodePos, width);
	}
	
	private void addInputs(int noOfInputs, Vector2f nodePos, float nodeWidth)
	{
		float increment = 2f / (float)(noOfInputs + 1);
		
		Vector2f cursor = new Vector2f(nodePos.x - (DisplayManager.WIDTH * nodeWidth / 2f),
									   nodePos.y + (DisplayManager.HEIGHT * nodeWidth * increment * (noOfInputs - 1)) / 2);
		
		for(int i=0;i<noOfInputs;i++)
		{
			addInput(cursor, this.getInputDataType(i), i);
			
			cursor.y-=(increment * nodeWidth * DisplayManager.HEIGHT);
		}
		
	}
	
	private void addOutputs(int noOfOutputs, Vector2f nodePos, float nodeWidth)
	{
		float increment = 2f / (float)(noOfOutputs + 1);
		
		Vector2f cursor = new Vector2f(nodePos.x + (DisplayManager.WIDTH * nodeWidth / 2f),
									   nodePos.y + (DisplayManager.HEIGHT * nodeWidth * increment * (noOfOutputs - 1)) / 2);
		
		for(int i=0;i<noOfOutputs;i++)
		{
			addOutput(cursor, this.getOutputDataType(i));
			
			cursor.y-=(increment * nodeWidth *  DisplayManager.HEIGHT);
		}
		
	}
	
	private void addInput(Vector2f cursor, SpellDataType typeOfPort, int index)
	{
		GuiButton g = new GuiButton(typeOfPort.getTexture(), parent) {
			@Override
			public void clicked() {

				if(parent instanceof SpellProgrammingMenu)
				{
					SpellProgrammingMenu menu = (SpellProgrammingMenu) parent;

					if(menu.isConnectorSelected())
					{
						menu.setSelectedConnectorP2(this.getPositionInViewPort());
						menu.setSelectedConnectorOutput(pointer, index);
						menu.setSelectedConnector(null);
					}
				}

			}
		};


		g.addXPosConstraint(new AbsolutePositionConstraint((int) cursor.x));
		g.addYPosConstraint(new AbsolutePositionConstraint((int) cursor.y));
		g.addScaleConstraint(new ScaleConstraint(0.01f, ScaleConstraint.WIDTH));
		g.addAspectConstraint(new AspectConstraint(1.77f));
		
		g.show();
	}
	
	private void addOutput(Vector2f cursor, SpellDataType typeOfPort)
	{
		GuiButton g = new GuiButton(typeOfPort.getTexture(), parent) {
			@Override
			public void clicked() {

				if(parent instanceof SpellProgrammingMenu)
				{
					SpellProgrammingMenu menu = (SpellProgrammingMenu) parent;

					if(menu.isConnectorSelected())
					{
						menu.removeCurrentConnector();
						menu.setSelectedConnector(null);
						return;
					}

					menu.setSelectedConnector(new GuiSpellConnector(this.getPositionInViewPort(),
							Input.getMousePosition(),
							typeOfPort,
							10,
                            parent,
							pointer));
				}

			}
		};


		g.addXPosConstraint(new AbsolutePositionConstraint((int) cursor.x));
		g.addYPosConstraint(new AbsolutePositionConstraint((int) cursor.y));
		g.addScaleConstraint(new ScaleConstraint(0.01f, ScaleConstraint.WIDTH));
		g.addAspectConstraint(new AspectConstraint(1.77f));

		g.show();
	}
	
	public SpellDataType getInputDataType(int port)
	{
		return this.type.getInputDataType(port);
	}
	
	public SpellDataType getOutputDataType(int port)
	{
		return this.type.getOutputDataType(port);
	}
	
	public SpellNodeType getType()
	{
		return type;
	}

	@Override
	public void dragged(int mouseX, int mouseY) {
		System.out.println("X: " + mouseX + " Y: " + mouseY);
		super.addXPosConstraint(new AbsolutePositionConstraint(mouseX));
		super.addYPosConstraint(new AbsolutePositionConstraint(mouseY));
	}
}
