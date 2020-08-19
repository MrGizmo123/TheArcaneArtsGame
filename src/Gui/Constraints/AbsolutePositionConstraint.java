package Gui.Constraints;

public class AbsolutePositionConstraint extends PositionConstraint{
	
	private float val;
	
	//takes in the absolute value of offset
	public AbsolutePositionConstraint(float value)
	{
		this.val = value;
	}

	//gives the offset in pixels
	@Override
	public float getPos() {
		return val;
	}

}
