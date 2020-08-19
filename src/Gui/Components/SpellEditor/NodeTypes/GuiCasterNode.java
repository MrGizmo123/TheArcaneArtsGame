package Gui.Components.SpellEditor.NodeTypes;

import Game.Spell.SpellNodeType;
import Gui.Gui;
import Gui.GuiLayout;
import Gui.Components.SpellEditor.GuiSpellNode;

public class GuiCasterNode extends GuiSpellNode{

	public GuiCasterNode(int pointer, Gui parent) {
		super(SpellNodeType.CASTER, pointer, parent);
	}

}
