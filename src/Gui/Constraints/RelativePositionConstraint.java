package Gui.Constraints;

import org.lwjgl.opengl.Display;

public class RelativePositionConstraint extends PositionConstraint{
	
	private float pos;
	
	public RelativePositionConstraint(float pos) {
		this.pos = pos;
	}

	/**
	 *
	 * @return returns the normalised coords
	 */
	@Override
	public float getPos() {
		return pos;
	}

}
