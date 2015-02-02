package net.quantuminfinity.particles.shaders;

import net.quantuminfinity.particles.Input;
import net.quantuminfinity.particles.console.Settings;
import net.quantuminfinity.particles.shaders.Shader.ComputeShader;
import net.quantuminfinity.particles.system.PingPongFBO;
import net.quantuminfinity.particles.system.SystemGenerator;
import net.quantuminfinity.utils.Vector2;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Vectorfield extends ComputeShader
{
	public PingPongFBO fbo;
	int flowTex;
	
	public Vectorfield(int textureSize)
	{
		super(textureSize, "/shaders/compute/vectorfield.frag", "/shaders/compute/vectorfield.vert");
		flowTex = SystemGenerator.genFlowTex(textureSize);
	}

	@Override
	public void init()
	{
		super.init();
		
		fbo = new PingPongFBO(2, textureSize);
		fbo.setData(
			SystemGenerator.genPosTex16(textureSize),
			SystemGenerator.genVelTex16(textureSize)
		);
		
		shader.bind();
		shader.setUniform("uResolution", new Vector2(textureSize,textureSize));
		shader.setUniform("uRes", Display.getWidth(), Display.getHeight());
		shader.release();
		
		loadSettings();
	}
	
	@Override
	public void loadSettings()
	{
		shader.bind();
		shader.setUniform("g", Settings.gravity);
		shader.setUniform("useNoise", Settings.useNoise ? 1 : 0);
		shader.setUniform("useGravity", Settings.useGravity ? 1 : 0);
		shader.setUniform("useAge", Settings.useAge ? 1 : 0);
		shader.release();
	}
	
	@Override
	public void input(Input input)
	{
		shader.bind();
		if (input.wasResized)
			shader.setUniform("uRes", Display.getWidth(), Display.getHeight());
		shader.setUniform("uMouse", input.mouse);
		shader.setUniform("uTime", input.getTimeSec());
		shader.release();
	}
	
	@Override
	public void render()
	{
		fbo.bind();
		shader.bind();
		shader.setUniform("data0", 0); GL13.glActiveTexture(GL13.GL_TEXTURE0); GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo.getData(0));
		shader.setUniform("data1", 1); GL13.glActiveTexture(GL13.GL_TEXTURE1); GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo.getData(1));
		shader.setUniform("field", 2); GL13.glActiveTexture(GL13.GL_TEXTURE2); GL11.glBindTexture(GL11.GL_TEXTURE_2D, flowTex);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex2f(Display.getWidth(), 0);
			GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex2f(Display.getWidth(), Display.getHeight());
			GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex2f(0, Display.getHeight());
		}
		GL11.glEnd();
		shader.release();
		fbo.release();
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		fbo.destroy();
	}
	
	@Override
	public int posTex()
	{
		return fbo.getData(0);
	}
	
	@Override
	public int velTex()
	{
		return fbo.getData(0);
	}
	
}
