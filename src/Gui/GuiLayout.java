package Gui;

import java.util.*;

public abstract class GuiLayout {

	private List<Gui> guis;
	
	private List<Gui> guisToBeAdded;
	private List<Gui> guisToBeRemoved;
	
	private Gui focusedGui;
	
	public GuiLayout()
	{
		guis = new ArrayList<>();
		
		guisToBeAdded = new ArrayList<>();
		guisToBeRemoved = new ArrayList<>();
		
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
		updateGuis(); // calles update() fuction of all guis
		checkGuiFocus(); // checks which gui has focus and
		updateGuiList();

		updateLayout(); // function used by subclasses for doing their updates
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
				setFocusedGui(g);
				break;
			}
		}

		Collections.reverse(guis);
	}

	private void updateGuiList()
	{
		guis.addAll(guisToBeAdded);
		guis.removeAll(guisToBeRemoved);

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
