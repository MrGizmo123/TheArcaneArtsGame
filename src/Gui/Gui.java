package Gui;

import Gui.TextRendering.Text;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import Game.tools.Input;
import Game.tools.Maths;
import Game.tools.utils.AABB;
import Gui.Constraints.AspectConstraint;
import Gui.Constraints.CenterConstraint;
import Gui.Constraints.PositionConstraint;
import Gui.Constraints.ScaleConstraint;

import java.util.ArrayList;
import java.util.List;

public class Gui {

	private PositionConstraint xConstraint;
	private PositionConstraint yConstraint;
	
	private ScaleConstraint scaleConstraint;
	private AspectConstraint aspectConstraint;
	
	protected int texture;
	protected Gui parent;
	protected List<Gui> children;
	
	private AABB boundingBox;
	private boolean hasFocus;

	private List<Text> texts;

	private boolean isFocusable;
	private boolean showFocusStatus;

	private Matrix4f transform;

	// default settings
	public Gui(int texture, Gui parent)
	{
		this.texture = texture;
		this.xConstraint = new CenterConstraint();
		this.yConstraint = new CenterConstraint();
		
		this.scaleConstraint = new ScaleConstraint(0.5f,ScaleConstraint.WIDTH);
		this.aspectConstraint = new AspectConstraint(1);
		
		this.parent = parent;
		
		recalculateAABB();
		hasFocus = false;

		isFocusable = true;
		showFocusStatus = true;

		texts = new ArrayList<>();
		children = new ArrayList<>();

		recalculateTransform();
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

	public void setShowFocusStatus(boolean show)
	{
		this.showFocusStatus = show;
	}

	public boolean isShowFocusStatus()
	{
		return showFocusStatus;
	}

	public void setFocusable(boolean focusable)
	{
		this.isFocusable = focusable;
	}

	public boolean isFocusable()
	{
		return isFocusable;
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
		return texts;
	}

	//update function (some subclases might need this)
	public void update()
	{
		for(Gui g : children)
		{
			g.update();
		}

		if(Display.wasResized())
		{
			windowResized();
		}
	}
	
	public boolean hasFocus()
	{
		return hasFocus;
	}
	
	public void loseFocus()
	{
		if(isFocusable) {
			hasFocus = false;
			focusLost();
		}
	}
	
	public void setFocus()
	{
		if(isFocusable) {
			hasFocus = true;
			focusGained();
		}
	}
	
	public boolean checkFocus()
	{
		if(isFocusable) {
			Vector2f mousePos = new Vector2f(Mouse.getX(), Mouse.getY());
			if (Input.isMouseButtonPressed(0)) {
				if (boundingBox.isIntersecting(mousePos) && !hasFocus) {
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
		return (Input.isKeyPressed(key) && hasFocus);
	}
	
	public boolean isKeyReleased(char key)
	{
		return (Input.isKeyReleased(key) && hasFocus);
	}
	
	private void recalculateAABB()
	{
		boundingBox = calculateAABB();
	}
	
	private AABB calculateAABB() 
	{
		Vector2f scale = this.getScale();
		Vector2f pos = this.getPositionInViewPort();
		
		int widthInPixels = (int) (scale.x * Display.getWidth());
		int heightInPixels = (int) (scale.y * Display.getHeight());
		
		int x1 = (int) (pos.x - ((float)widthInPixels / (float)2));
		int y1 = (int) (pos.y - ((float)heightInPixels / (float)2));
		
		int x2 = (int) (pos.x + ((float)widthInPixels / (float)2));
		int y2 = (int) (pos.y + ((float)heightInPixels / (float)2));
		
		return new AABB(x1, y1, x2, y2);
		
	}
	
	//callback for when position constraints are updated
	protected void constraintsUpdated()
	{
		
	}

	protected void windowResized()
	{

	}
	
	public void show()
	{
		this.parent.addGui(this);
	}
	
	public void hide()
	{
		this.parent.removeGui(this);
	}
	
	// self explanatory
	
	public void addXPosConstraint(PositionConstraint c)
	{
		this.xConstraint = c;
		constraintsUpdated();
		recalculateAABB();
		recalculateTransform();
	}
	
	public void addYPosConstraint(PositionConstraint c)
	{
		this.yConstraint = c;
		constraintsUpdated();
		recalculateAABB();
		recalculateTransform();

	}
	
	public void addScaleConstraint(ScaleConstraint c)
	{
		this.scaleConstraint = c;
		constraintsUpdated();
		recalculateAABB();
		recalculateTransform();
	}
	
	public void addAspectConstraint(AspectConstraint c)
	{
		this.aspectConstraint = c;
		constraintsUpdated();
		recalculateAABB();
		recalculateTransform();
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
		return aspectConstraint.getAspectRatio();
	}
	
	// calculates and returns the position in Normalised Device Coordinates
	public Vector2f getPositionInNDC()
	{
		float xPos = xConstraint.getPos();
		float yPos = yConstraint.getPos();
		
		Vector2f ndc = Maths.viewportToNDC(Maths.normalisedToViewport(new Vector2f(xPos, yPos)));
		return ndc;
	}

	/**
	 * @return returns the position in viewport coords
	 */
	// calculates and returns the position in Viewport coords
	public Vector2f getPositionInViewPort()
	{
		float xPos = xConstraint.getPos();
		float yPos = yConstraint.getPos();
		
		Vector2f result = Maths.normalisedToViewport(new Vector2f(xPos, yPos));
		return result;
	}

	/**
	 *
	 * @return returns normalised coordinates , topLeft:(0,0), bottomRight:(1,1)
	 */
	public Vector2f getCoordinates()
	{
		return new Vector2f(xConstraint.getPos(), yConstraint.getPos());
	}
	
	// calculates the scale according to axes and aspect ratio and returns it
	public Vector2f getScale()
	{
		float xScale;
		float yScale;
		
		float scale = scaleConstraint.getScale();
		
		if(scaleConstraint.getAxis() == ScaleConstraint.WIDTH)
		{
			xScale = scale;
			yScale = aspectConstraint.getHeightFromWidth(xScale);
		}
		else
		{
			yScale = scale;
			xScale = aspectConstraint.getWidthFromHeight(yScale);
		}
		
		return new Vector2f(xScale, yScale);
		
	}
	
	public int getWidth()
	{
		int width;
		
		float scale = scaleConstraint.getScale();
		
		if(scaleConstraint.getAxis() == ScaleConstraint.WIDTH)
		{
			width = (int) (scale * Display.getWidth());
		}
		else
		{
			width = (int) (aspectConstraint.getWidthFromHeight(scale) * Display.getWidth());
		}
		
		return width;
	}
	
	public int getHeight()
	{
		int height;
		
		float scale = scaleConstraint.getScale();
		
		if(scaleConstraint.getAxis() == ScaleConstraint.WIDTH)
		{
			height = (int) (aspectConstraint.getHeightFromWidth(scale) * Display.getHeight());
		}
		else
		{
			height = (int) (scale * Display.getHeight());
		}
		
		return height;
	}
	
}
