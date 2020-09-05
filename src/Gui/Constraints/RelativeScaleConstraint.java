package Gui.Constraints;

public class RelativeScaleConstraint implements ScalingConstraint{

    private float width;
    private float height;

    public RelativeScaleConstraint(float w, float h)
    {
        this.width = w;
        this.height = h;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float h) {
        this.height = h;
    }

    @Override
    public void setWidth(float w) {
        this.width = w;
    }
}
