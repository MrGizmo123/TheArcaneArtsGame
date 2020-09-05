package Gui.Transitions.Drivers;

import Game.Logging.Logger;

public class LinearDriver implements TransitionDriver{

    private float start;
    private float end;

    public LinearDriver(float start, float end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    public float getVal(float fac) {
        return ((fac * end) + ((1 - fac) * start));
    }
}
