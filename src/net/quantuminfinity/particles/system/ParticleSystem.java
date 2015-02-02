package net.quantuminfinity.particles.system;

import net.quantuminfinity.particles.Input;
import net.quantuminfinity.particles.shaders.Shader.ComputeShader;
import net.quantuminfinity.particles.shaders.Shader.PostShader;
import net.quantuminfinity.particles.shaders.Shader.RenderShader;
import net.quantuminfinity.particles.shaders.*;
public class ParticleSystem
{
	public final int texsize;
	public ComputeShader computeShader;
	public RenderShader renderShader;
	public PostShader postShader;
	
	public ParticleSystem(int texsize)
	{
		this.texsize = texsize;
		computeShader = new MouseAttractor(texsize);
		renderShader = new PointCloudRender(texsize);
		postShader = new Post(texsize);
	}
	
	public void init()
	{
		computeShader.init();
		renderShader.init();
		postShader.init();
	}

	public void render(Input input)
	{
		computeShader.input(input);
		computeShader.render();
		
		renderShader.input(input);
		renderShader.render(computeShader.posTex(), computeShader.velTex());
		
		postShader.input(input);
		postShader.render(renderShader.outTex());
	}
	
	public void destroy()
	{
		computeShader.destroy();
		renderShader.destroy();
	}
}
