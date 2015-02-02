package net.quantuminfinity.particles.shaders;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.quantuminfinity.particles.Input;
import net.quantuminfinity.particles.console.Settings;
import net.quantuminfinity.particles.shaders.Shader.RenderShader;
import net.quantuminfinity.particles.system.FBO;
import net.quantuminfinity.particles.system.VBOPointCloud;
import net.quantuminfinity.utils.Vector2;

public class PointCloudRender extends RenderShader
{
	FBO fbo;
	VBOPointCloud points;
	
	public PointCloudRender(int textureSize)
	{
		super(textureSize, "/shaders/render/pointcloud.frag", "/shaders/render/pointcloud.vert");
	}

	@Override
	public void init()
	{
		super.init();
		fbo = new FBO(1, new Vector2(Display.getWidth(), Display.getHeight()));
		points = new VBOPointCloud(textureSize*textureSize);
		
		shader.bind();
		shader.setUniform("uResolution", Display.getWidth(), Display.getHeight());
		shader.setUniform("uTexsize", textureSize);
		shader.release();
		
		loadSettings();
	}
	
	@Override
	public void loadSettings()
	{
		shader.bind();
		shader.setUniform("color", Settings.color);
		shader.release();
	}
	
	@Override
	public void input(Input input)
	{
		shader.bind();
		if (input.wasResized)
		{
			shader.setUniform("uResolution", Display.getWidth(), Display.getHeight());
			fbo.destroy();
			fbo = new FBO(1, new Vector2(Display.getWidth(), Display.getHeight()));
		}
		shader.setUniform("uTime", input.getTimeSec());
		shader.release();
	}
	
	public void render(int posTex, int velTex)
	{
		//##FBO DRAWING##
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		fbo.bind();
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();
		GL11.glColor3f(1, 1, 1);
		
		shader.bind();
		shader.setUniform("data0", 0); GL13.glActiveTexture(GL13.GL_TEXTURE0); GL11.glBindTexture(GL11.GL_TEXTURE_2D, posTex);
		shader.setUniform("data1", 1); GL13.glActiveTexture(GL13.GL_TEXTURE1); GL11.glBindTexture(GL11.GL_TEXTURE_2D, velTex);
		points.render();
		shader.release();
		fbo.release();
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		points.destroy();
		fbo.destroy();
	}

	@Override
	public int outTex()
	{
		return fbo.getData(0);
	}
}
