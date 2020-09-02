package Gui.Components.SpellEditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import Gui.Gui;

import Game.Spell.SpellNodeType;
import Game.tools.Input;
import Gui.Layouts.SpellProgrammingMenu;

public class GuiSpellGrid {

	private static Map<Integer, GuiSpellNode> nodes;
	int currentPtr;
	
	private Gui parent;
	
	public GuiSpellGrid(Gui l)
	{
		nodes = new HashMap<>();
		currentPtr = 0;
		this.parent = l;
	}
	
	public void update()
	{
		if(parent instanceof SpellProgrammingMenu)
		{
			SpellProgrammingMenu menu = (SpellProgrammingMenu) parent;
			if(Input.isKeyPressed('a') && menu.isBackgroundFocused())
			{
				menu.showSelector(Input.getMousePosition());
			}
		}
	}
	
	public void addNode(SpellNodeType type)
	{
		Class<? extends GuiSpellNode> typeClass = type.getTypeClass();
		Constructor<?> c = typeClass.getConstructors()[0];

		GuiSpellNode node = null;
		try {
			node = (GuiSpellNode) c.newInstance(currentPtr, parent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		this.nodes.put(currentPtr, node);
		currentPtr++;
	}
	
	public GuiSpellNode getNodeFromPtr(int ptr)
	{
		return nodes.get(ptr);
	}
	
}
