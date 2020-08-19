package Gui.Components.SpellEditor.NodeTypes;

import Game.Spell.SpellNodeType;
import Gui.Gui;
import Gui.GuiLayout;
import Gui.Components.SpellEditor.GuiSpellNode;

public class GuiEntityDirectionNode extends GuiSpellNode{

	public GuiEntityDirectionNode(int pointer, Gui parent) {
		super(SpellNodeType.LOOKING, pointer, parent);
	}

}
