package Gui.Constraints.Properties;

public class NotFocusable implements FocusMode{

    @Override
    public boolean showFocusStatus() {
        return false;
    }

    @Override
    public boolean hasFocus() {
        return false;
    }

    @Override
    public void setFocus() { }

    @Override
    public void loseFocus() { }
}
