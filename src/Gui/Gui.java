package Gui;

import Game.Logging.Logger;
import Game.Render.DisplayManager;
import Game.tools.Callback;
import Gui.Constraints.*;
import Gui.Constraints.Properties.FocusMode;
import Gui.Constraints.Properties.Focusable;
import Gui.Constraints.Properties.FocusableDontShowStatus;
import Gui.TextRendering.Text;

import Game.tools.Input;
import Game.tools.Maths;
import Game.tools.utils.AABB;
import Gui.Transitions.Transition;
import VecMath.Matrix4f;
import VecMath.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Gui {

	private PositioningConstraint posConstraint;
	
	private ScalingConstraint scaleConstraint;
	
	protected int texture;
	protected Gui parent;
	protected List<Gui> children;
	
	private AABB boundingBox;

	private List<Text> texts;

	private FocusMode focusMode;

	private Matrix4f transform;

	private Transition showTransition;
	private Transition hideTransition;

	private Transition currentTransition; // the currently running transition

	private boolean isVisible;

	// default settings
	public Gui(int texture, Gui parent)
	{
		this.texture = texture;
		
		this.scaleConstraint = new RelativeScaleConstraint(0.5f,0.5f);
		this.posConstraint = new RelativePosConstraint(0.5f,0.5f);
		
		this.parent = parent;

		this.focusMode = new FocusableDontShowStatus();

		this.texts = new ArrayList<>();
		this.children = new ArrayList<>();

		this.showTransition = new Transition(1);
		this.hideTransition = new Transition(1);

		this.currentTransition = null;
		this.isVisible = true;

		recalculateAABB();
		recalculateTransform();
	}

	public boolean isVisible()
	{
		return isVisible;
	}

	public void setShowTransition(Transition t)
	{
		this.showTransition = t;
		this.showTransition.setStartedCallback(new Callback() {
			@Override
			public void invoke() {
				showImmediate();
			}
		});

		this.showTransition.setFinishedCallback(new Callback() {
			@Override
			public void invoke() {
				currentTransition = null;
			}
		});
	}

	public void setHideTransition(Transition t)
	{
		this.hideTransition = t;
		this.hideTransition.setFinishedCallback(new Callback() {
			@Override
			public void invoke() {
				hideImmediate();
				currentTransition = null;
			}
		});
	}

	public Matrix4f getTransform()
	{
		return transform;
	}

	public void addGui(Gui g)
	{
		children.add(g);
	}

	public void removeGui(Gui g)
	{
		children.remove(g);
	}

	public List<Gui> getChildren()
    {
        return children;
    }

	public void setFocusMode(FocusMode newMode)
    {
        this.focusMode = newMode;
    }

	public boolean isShowFocusStatus()
	{
		return focusMode.showFocusStatus();
	}

	protected void addText(Text t)
	{
		texts.add(t);
	}

	protected void removeText(Text t)
	{
		texts.remove(t);
	}

	public List<Text> getTexts()
	{
		if(isVisible)
			return texts;

		return new ArrayList<>();
	}

	//update function (some subclases might need this)
	public void update()
	{
		for(Gui g : children)
		{
			g.update();
		}

		if(DisplayManager.wasDisplayResized())
		{
			windowResized();
		}

		handleTransitions();
	}

	private void handleTransitions()
	{
		if(currentTransition != null) {
			currentTransition.update();

			posConstraint.moveX(currentTransition.getDX());
			posConstraint.moveY(currentTransition.getDY());

			scaleConstraint.setWidth(scaleConstraint.getWidth() * currentTransition.getScaleX());
			scaleConstraint.setHeight(scaleConstraint.getHeight() * currentTransition.getScaleY());

			recalculateTransform();
			recalculateAABB();
		}
	}
	
	public boolean hasFocus()
	{
		return focusMode.hasFocus();
	}
	
	public void loseFocus()
	{
		if(focusMode instanceof Focusable) {
            focusMode.loseFocus();
			focusLost();
		}
	}
	
	public void setFocus()
	{
		if(focusMode instanceof Focusable) {
			focusMode.setFocus();
			focusGained();
		}
	}
	
	public boolean checkFocus()
	{
		if(focusMode instanceof Focusable) {
			Vector2f mousePos = Input.getMousePosition();
			if (Input.isMouseButtonPressed(0)) {
				if (boundingBox.isIntersecting(mousePos) && !focusMode.hasFocus()) {
					setFocus();
					return true;
				} else {
					loseFocus();
					return false;
				}
			}
		}
		return false;
	}
	
	public void focusGained() 
	{
		
	}
	
	public void focusLost()
	{
		
	}
	
	public boolean isKeyPressed(char key)
	{
		return (Input.isKeyPressed(key) && focusMode.hasFocus());
	}
	
	public boolean isKeyReleased(char key)
	{
		return (Input.isKeyReleased(key) && focusMode.hasFocus());
	}
	
	private void recalculateAABB()
	{
		boundingBox = calculateAABB();
	}
	
	private AABB calculateAABB() 
	{

		Vector2f scale = this.getScale();
		Vector2f pos = this.getPositionInViewPort();
		
		int widthInPixels = (int) (scale.x * DisplayManager.WIDTH);
		int heightInPixels = (int) (scale.y * DisplayManager.HEIGHT);
		
		int x1 = (int) (pos.x - ((float)widthInPixels / (float)2));
		int y1 = (int) (pos.y - ((float)heightInPixels / (float)2));
		
		int x2 = (int) (pos.x + ((float)widthInPixels / (float)2));
		int y2 = (int) (pos.y + ((float)heightInPixels / (float)2));
		
		return new AABB(x1, y1, x2, y2);
		
	}
	
	//callback for when position constraints are updated
	protected void constraintsUpdated()
	{
		recalculateAABB();
		recalculateTransform();
	}

	// callback
	protected void windowResized()
	{

	}
	
	public void show()
	{
		this.currentTransition = showTransition;
		currentTransition.start();
	}

	public void showImmediate()
	{
		isVisible = true;
	}
	
	public void hide()
	{
		this.currentTransition = hideTransition;
		currentTransition.start();
	}

	public void hideImmediate()
	{
		isVisible = false;
	}
	
	// self explanatory
	
	public void addPosConstraint(PositioningConstraint constraint)
	{
		this.posConstraint = constraint;
		constraintsUpdated();
	}
	
	public void addScaleConstraint(ScalingConstraint c)
	{
		this.scaleConstraint = c;
		constraintsUpdated();
	}

	public void recalculateTransform()
	{
		if(parent != null)
		{
			Matrix4f selfTransform = Maths.createTransMatrix(this.getPositionInNDC(), this.getScale());
			transform = Matrix4f.mul(parent.getTransform(), selfTransform, transform);
		}
		else
		{
			transform = new Matrix4f();
			transform.setIdentity();
		}

		for(Gui g : children)
		{
			g.recalculateTransform();
		}
	}
	
	// returns opengl texture id
	public int getTexture()
	{
		return texture;
	}
	
	public float getAspect()
	{
		return scaleConstraint.getWidth() / scaleConstraint.getHeight();
	}
	
	// calculates and returns the position in Normalised Device Coordinates
	public Vector2f getPositionInNDC()
	{
		Vector2f ndc = Maths.normalisedToNDC(getCoordinates());
		return ndc;
	}

	/**
	 * @return returns the position in viewport coords
	 */
	// calculates and returns the position in Viewport coords
	public Vector2f getPositionInViewPort()
	{
		Vector2f result = Maths.normalisedToViewport(getCoordinates());
		return result;
	}

	/**
	 *
	 * @return returns normalised coordinates , topLeft:(0,0), bottomRight:(1,1)
	 */
	public Vector2f getCoordinates()
	{
		return posConstraint.getPos();
	}
	
	// calculates the scale according to axes and aspect ratio and returns it
	public Vector2f getScale()
	{
		return new Vector2f(scaleConstraint.getWidth(), scaleConstraint.getHeight());
	}
	
}
