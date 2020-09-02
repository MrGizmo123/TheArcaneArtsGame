package Game.Render.SubRenderers;

import java.util.List;

import Game.Logging.Logger;
import VecMath.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;


import Game.Render.DisplayManager;
import Game.Render.Shaders.GuiShader;
import Game.tools.GameResourcesAndSettings;

import Gui.Gui;


public class GuiRenderer {

	private GuiShader shader;

	private FontRenderer fontRenderer;
	
	public GuiRenderer()
	{
		shader = new GuiShader();

		fontRenderer = new FontRenderer();
	}
	
	private void bindGui(Gui g)
	{
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		shader.loadTexture(g.getTexture());

		shader.loadTransMatrix(g.getTransform());
		
		shader.loadAspectRatio(g.getAspect());
		shader.isSelected(g.hasFocus() && g.isShowFocusStatus());
	}
	
	private void prepare()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		shader.start();
		shader.loadDisplayAspectRatio(DisplayManager.aspectRatio);
		shader.stop();
	}

	private void calculateAndLoadProjectionMatrix()
	{
		Matrix4f projMatrix = new Matrix4f();
		//projMatrix.setIdentity();
		//Matrix4f.scale(new Vector3f(DisplayManager.aspectRatio,1,1), projMatrix, projMatrix);

		shader.loadProjMatrix(projMatrix);
	}

	private void renderGui(Gui g)
	{
		shader.start();
		GL30.glBindVertexArray(GameResourcesAndSettings.quadVAO);

		bindGui(g);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);

		fontRenderer.renderTexts(g.getTexts());

	}
	
	public void renderGuis(List<Gui> guis)
	{
		prepare();
		
		for(Gui g : guis)
		{
			renderGui(g);
		}
		
		shader.stop();
	}

	public void renderGuisRecursive(Gui root)
	{
		prepare();

		calculateAndLoadProjectionMatrix();

		renderGui(root);
		for(Gui g : root.getChildren())
		{
			renderGuisRecursive(g);
		}

		shader.stop();
	}
	
}
