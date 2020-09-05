package Gui.Constraints.Properties;

public class Focusable implements FocusMode{

    private boolean hasFocus;

    @Override
    public boolean showFocusStatus() {
        return true;
    }

    @Override
    public boolean hasFocus() {
        return hasFocus;
    }

    @Override
    public void setFocus() {
        this.hasFocus = true;
    }

    @Override
    public void loseFocus() {
        this.hasFocus = false;
    }
}
