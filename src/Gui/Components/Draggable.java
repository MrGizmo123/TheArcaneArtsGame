package Gui.Components;

public interface Draggable {

    public void dragStarted(int mouseX, int mouseY);
    public void dragContinued(int mouseX, int mouseY);
    public void dragEnded(int mouseX, int mouseY);

}
