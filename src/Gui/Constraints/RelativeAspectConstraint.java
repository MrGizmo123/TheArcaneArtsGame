package Gui.Constraints;

public class RelativeAspectConstraint  implements ScalingConstraint{

    private float width;
    private float aspect;

    public RelativeAspectConstraint(float width, float aspect)
    {
        this.width = width;
        this.aspect = aspect;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return width / aspect;
    }

    @Override
    public void setHeight(float h) {
        this.aspect = width / h;
    }

    @Override
    public void setWidth(float w) {
        this.width = w;
    }
}
