package Gui.Transitions.Drivers;

public class ConstantDriver implements TransitionDriver{

    private float val;

    public ConstantDriver(float val)
    {
        this.val = val;
    }

    @Override
    public float getVal(float fac) {
        return val;
    }
}
