package Gui;

import java.util.*;

import Gui.TextRendering.Text;

public abstract class GuiLayout {

	private List<Gui> guis;
	
	private List<Gui> guisToBeAdded;
	private List<Gui> guisToBeRemoved;
	
	private Gui focusedGui;
	
	public GuiLayout()
	{
		guis = new ArrayList<Gui>();
		
		guisToBeAdded = new ArrayList<Gui>();
		guisToBeRemoved = new ArrayList<Gui>();
		
		focusedGui = null;
	}
	
	public void addGui(Gui g)
	{
		guisToBeAdded.add(g);
		focusedGui = g;
	}
	
	public void removeGui(Gui g)
	{
		guisToBeRemoved.add(g);
	}
	
	public List<Gui> getGuis()
	{
		return guis;
	}
	
	public void update()
	{
		updateLayout(); // function used by subclasses for doing their updates

		updateGuis(); // calles update() fuction of all guis

		checkGuiFocus(); // checks which gui has focus and 
		
		updateGuiList();
	}

	private void updateGuis()
	{
		for(Gui g : guis)
		{
			g.update();
		}
	}

	private void checkGuiFocus()
	{
		Collections.reverse(guis);

		for(Gui g : guis)
		{
			if(g.checkFocus())
			{
				focusedGui.loseFocus();
				focusedGui = g;
				break;
			}
		}

		Collections.reverse(guis);
	}

	private void updateGuiList()
	{
		for(Gui g : guisToBeAdded)
		{
			guis.add(g);
		}

		for(Gui g : guisToBeRemoved)
		{
			guis.remove(g);
		}

		guisToBeAdded.clear();
		guisToBeRemoved.clear();
	}

	protected void updateLayout()
	{
		
	}
	
	public void setFocusedGui(Gui g)
	{
		for(Gui gui : guis)
		{
			if(gui.hasFocus())
				gui.loseFocus();
		}
		
		focusedGui = g;
		focusedGui.setFocus();
	}
	
}
