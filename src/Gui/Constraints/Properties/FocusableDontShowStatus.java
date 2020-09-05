package Gui.Constraints.Properties;

public class FocusableDontShowStatus extends Focusable {

    private boolean hasFocus;

    @Override
    public boolean showFocusStatus() {
        return false;
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
