package Gui.Constraints;

import org.lwjgl.opengl.Display;

public class CenterConstraint extends PositionConstraint{
	
	public CenterConstraint()
	{

	}
	
	@Override
	public float getPos()
	{
		return 0.5f;
	}
	
}
