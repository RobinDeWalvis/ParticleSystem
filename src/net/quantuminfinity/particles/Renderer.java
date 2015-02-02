package net.quantuminfinity.particles;

import javax.swing.JOptionPane;

import net.quantuminfinity.particles.console.ParticleConsole;
import net.quantuminfinity.particles.console.Settings;
import net.quantuminfinity.particles.system.ParticleSystem;
import net.quantuminfinity.simpleengine.Engine.GameType;
import net.quantuminfinity.simpleengine.Application;
import net.quantuminfinity.simpleengine.ShaderProgram;
import net.quantuminfinity.utils.Vector2;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Renderer extends Application
{	
	ShaderProgram postshader;
	boolean reloadShaderSettings = false, reloadSystem = false;
	ParticleConsole console;
	
	ParticleSystem system;
	Input input;
	
	@Override
	public boolean preInit()
	{		
		ContextCapabilities caps = GLContext.getCapabilities();
		if (!caps.GL_EXT_framebuffer_object)
			return error("Frame Buffer Objects not supported.");
		if (!caps.OpenGL30)
			return error("OpenGL version not supported (Current version: " + GL11.glGetString(GL11.GL_VERSION) + ").");
		if (!caps.GL_ARB_fragment_shader)
			return error("Fragment shader not supported.");
		if (!caps.GL_ARB_shader_objects)
			return error("Shader Objects not supported.");
		if (!caps.GL_ARB_vertex_shader)
			return error("Vertex shader not supported.");
		return true;
	}
	
	@Override
	public void onCreate()
	{
		Display.setTitle("Initializing");
		console = new ParticleConsole(this);
		
		Display.setResizable(true);
		GL11.glClearColor(0, 0, 0.1f, 1);
		GL11.glClearAccum(0,0,0,1);
		GL11.glLineWidth(1);
		GL11.glPointSize(1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		input = new Input();
		reloadSystem();
	}
	
	@Override
	public void onDraw()
	{
		input.update();
		Display.setTitle(Display.getTitle() + " Particles: " + (system.texsize*system.texsize) + " (tex size: " + system.texsize + ")");
		
		if (Keyboard.isKeyDown(Keyboard.KEY_F1) && !console.isVisible())
			console.setVisible(true);
		
		if (reloadShaderSettings)
		{
			loadShaderSettings();
			reloadShaderSettings = false;
		}
		
		if (reloadSystem)
		{
			reloadSystem();
			reloadSystem = false;
			input.wasResized = true;
		}
		
		if (input.wasResized)
		{
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
		
		system.render(input);
	}
	
	public void reloadSystem()
	{
		system = new ParticleSystem(Settings.textureSize);
		system.init();
		
		loadShaderSettings();
	}
	
	public void loadShaderSettings()
	{
		system.computeShader.loadSettings();
		system.renderShader.loadSettings();
	}
	
	public void reqReloadShaderSettings()
	{
		reloadShaderSettings = true;
	}
	
	public void scheduleReloadSystem()
	{
		reloadSystem = true;
	}
	
	@Override
	public void onDestroy()
	{
		system.destroy();
		console.dispose();
	}
	
	@Override
	public Vector2 getDisplaySize()
	{
		return new Vector2(1300,800);
	}

	@Override
	public GameType getGameType()
	{
		return GameType.RENDER_2D;
	}
	
	public static boolean error(String msg)
	{
		if (JOptionPane.showOptionDialog(null, msg + "\nTry updating your graphics driver.\n\nThese checks aren't always correct. Continue anyway?", "Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes") == 0)
			return true;
		return false;
	}
}
