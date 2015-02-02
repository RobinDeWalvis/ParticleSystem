package net.quantuminfinity.particles.shaders;

import net.quantuminfinity.particles.Input;
import net.quantuminfinity.particles.shaders.Shader.PostShader;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Post extends PostShader
{
	public Post(int textureSize)
	{
		super(textureSize, "/shaders/post/post.frag", "/shaders/post/post.vert");
	}

	@Override
	public void init()
	{
		super.init();
	}
	
	@Override
	public void loadSettings()
	{
		
	}
	
	@Override
	public void input(Input input)
	{
		shader.bind();
		if (input.wasResized)
			shader.setUniform("uResolution", Display.getWidth(), Display.getHeight());
		shader.setUniform("uMouse", input.mouse);
		shader.setUniform("uTime", input.getTimeSec());
		shader.release();
	}
	
	@Override
	public void render(int outTex)
	{
		//##SCREEN DRAWING##
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();
		GL11.glColor3f(1, 1, 1);
				
		shader.bind();
		shader.setUniform("data", 0); GL13.glActiveTexture(GL13.GL_TEXTURE0); GL11.glBindTexture(GL11.GL_TEXTURE_2D, outTex);
		//shader.setUniform("field", 2); GL13.glActiveTexture(GL13.GL_TEXTURE2); GL11.glBindTexture(GL11.GL_TEXTURE_2D, ParticleSystem.flowTex);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex2f(Display.getWidth(), 0);
			GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex2f(Display.getWidth(), Display.getHeight());
			GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex2f(0, Display.getHeight());
		}
		GL11.glEnd();
		shader.release();
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
	}
}
