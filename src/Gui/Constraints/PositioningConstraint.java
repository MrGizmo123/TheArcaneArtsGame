package Gui.Constraints;

import VecMath.Vector2f;

public interface PositioningConstraint {

    public float getXPos();
    public float getYPos();

    public Vector2f getPos();

    public void moveX(float dx);
    public void moveY(float dy);

}
