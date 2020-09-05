package Gui.Transitions;

import Game.tools.Callback;
import Game.tools.GameTickUpdater;
import Gui.Transitions.Drivers.ConstantDriver;
import Gui.Transitions.Drivers.TransitionDriver;

public class Transition {

    private float fac;

    private float duration; // in seconds
    private float elapsedTime; // in seconds

    private boolean isRunning;

    private TransitionDriver dxDriver;
    private TransitionDriver dyDriver;

    private TransitionDriver scaleXDriver;
    private TransitionDriver scaleYDriver;

    private TransitionDriver opacityDriver;

    private Callback transitionFinishedCallback;
    private Callback transitionStartedCallback;

    public Transition(float duration)
    {
        this.duration = duration;
        this.elapsedTime = 0;
        this.isRunning = false;

        this.dxDriver = new ConstantDriver(0);
        this.dyDriver = new ConstantDriver(0);

        this.opacityDriver = new ConstantDriver(1);

        this.scaleXDriver = new ConstantDriver(1);
        scaleYDriver = new ConstantDriver(1);

        this.transitionStartedCallback = new Callback() {
            @Override
            public void invoke() {
                // do nothin
            }
        };

        this.transitionFinishedCallback = new Callback() {
            @Override
            public void invoke() {
                // do nothin
            }
        };
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void setFinishedCallback(Callback c)
    {
        this.transitionFinishedCallback = c;
    }

    public void setStartedCallback(Callback c)
    {
        this.transitionStartedCallback = c;
    }

    public void start()
    {
        this.isRunning = true;
        transitionStartedCallback.invoke();
    }

    public void stop()
    {
        this.isRunning = false;
    }

    public void reset()
    {
        elapsedTime = 0;
    }

    public void update()
    {
        if(isRunning) {
            if (elapsedTime >= duration) {
                isRunning = false;
                transitionFinishedCallback.invoke();
                reset();
                return;
            }

            elapsedTime += GameTickUpdater.getFrameTime();
            fac = elapsedTime / duration;
        }
    }

    public Transition setXDriver(TransitionDriver newDriver)
    {
        this.dxDriver = newDriver;
        return this;
    }

    public Transition setYDriver(TransitionDriver newDriver)
    {
        this.dyDriver = newDriver;
        return this;
    }

    public Transition setOpacityDriver(TransitionDriver newDriver)
    {
        this.opacityDriver = newDriver;
        return this;
    }

    public Transition setScaleXDriver(TransitionDriver newDriver)
    {
        this.scaleXDriver = newDriver;
        return this;
    }

    public Transition setScaleYDriver(TransitionDriver newDriver)
    {
        this.scaleYDriver = newDriver;
        return this;
    }

    public float getDX()
    {
        return dxDriver.getVal(fac);
    }

    public float getDY()
    {
        return dyDriver.getVal(fac);
    }

    public float getOpacity()
    {
        return opacityDriver.getVal(fac);
    }

    public float getScaleX()
    {
        return scaleXDriver.getVal(fac);
    }

    public float getScaleY()
    {
        return scaleYDriver.getVal(fac);
    }

}
