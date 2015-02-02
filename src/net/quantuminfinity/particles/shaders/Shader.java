package net.quantuminfinity.particles.shaders;

import net.quantuminfinity.particles.Input;
import net.quantuminfinity.simpleengine.ShaderProgram;

public class Shader
{
	protected final String fragLoc, vertLoc;
	protected final int textureSize;
	protected ShaderProgram shader;
	
	public Shader(int textureSize, String fragLoc, String vertLoc)
	{
		this.fragLoc = fragLoc;
		this.vertLoc = vertLoc;
		this.textureSize = textureSize;
	}
	
	public void init()
	{
		shader = new ShaderProgram()
		{{
			addShader(fragLoc);
			addShader(vertLoc);
			compile();
		}};
	}
	
	public void loadSettings(){}
	public void input(Input input){}
	
	public void destroy()
	{
		shader.destroy();
	}
	
	public static abstract class ComputeShader extends Shader
	{
		public ComputeShader(int textureSize, String fragLoc, String vertLoc)
		{
			super(textureSize, fragLoc, vertLoc);
		}
		
		public abstract void render();
		public abstract int posTex();
		public abstract int velTex();
	}
	
	public static abstract class RenderShader extends Shader
	{
		public RenderShader(int textureSize, String fragLoc, String vertLoc)
		{
			super(textureSize, fragLoc, vertLoc);
		}
		
		public abstract void render(int posTex, int velTex);
		public abstract int outTex();
	}
	
	public static abstract class PostShader extends Shader
	{
		public PostShader(int textureSize, String fragLoc, String vertLoc)
		{
			super(textureSize, fragLoc, vertLoc);
		}
		
		public abstract void render(int outTex);
	}
}
