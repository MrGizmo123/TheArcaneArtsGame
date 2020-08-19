package Gui.Components.SpellEditor.NodeTypes;

import Game.Spell.SpellNodeType;
import Gui.Gui;
import Gui.GuiLayout;
import Gui.Components.SpellEditor.GuiSpellNode;

public class GuiPushNode extends GuiSpellNode{

	public GuiPushNode(int pointer, Gui parent) {
		super(SpellNodeType.PUSH, pointer, parent);
	}

}
