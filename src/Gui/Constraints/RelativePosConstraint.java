package Gui.Constraints;

import VecMath.Vector2f;

public class RelativePosConstraint implements PositioningConstraint{

    private float x;
    private float y;

    public RelativePosConstraint(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getXPos() {
        return x;
    }

    @Override
    public float getYPos() {
        return y;
    }

    @Override
    public Vector2f getPos() {
        return new Vector2f(x, y);
    }

    @Override
    public void moveX(float dx) {
        this.x += dx;
    }

    @Override
    public void moveY(float dy) {
        this.y += dy;
    }
}
