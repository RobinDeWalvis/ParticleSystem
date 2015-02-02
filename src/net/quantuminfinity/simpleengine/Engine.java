package net.quantuminfinity.simpleengine;

import javax.swing.JOptionPane;

import net.quantuminfinity.utils.FPSMeter;
import net.quantuminfinity.utils.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Engine
{
	Application app;
	Vector2 size;
	FPSMeter meter;
	
	public Engine(Application app)
	{
		this.app = app;
		size = app.getDisplaySize();
		meter = new FPSMeter();
	}

	public void start()
	{
		app.state = EngineState.RUNNING;
		initDisplay();
		initGL(app.getGameType());
		if (!app.preInit())
			return;
		app.onCreate();
		loop();
		stop();
	}
	
	public void stop()
	{
		app.onDestroy();
		Display.destroy();
		if (app.state == EngineState.REQ_RESTART)
			start();
		app.state = EngineState.STOPPED;
		//System.exit(0);
	}
	
	public void loop()
	{
		while(!Display.isCloseRequested() && app.state == EngineState.RUNNING)
	    {
			Display.setTitle("Fps: " + meter.updateFPS());
			app.onDraw();
			Display.sync(60);
	    	Display.update();
	    }
	}

	private void initGL(GameType gt)
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		switch(gt)
		{
			default:
			case RENDER_2D:
			{
				GL11.glOrtho(0, (int) size.x, 0, (int) size.y, -1, 1);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
			}
				break;
			case RENDER_3D:
			{
				GL11.glDepthFunc(GL11.GL_LEQUAL);
		        GLU.gluPerspective(67, size.x / size.y, 0.01f, 100);
		        GL11.glMatrixMode(GL11.GL_MODELVIEW);
		        GL11.glLoadIdentity();
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
				break;
			case RENDER_3D_LIGHTS:
			{
				GL11.glDepthFunc(GL11.GL_LEQUAL);
		        GLU.gluPerspective(67, size.x / size.y, 0.01f, 100);
		        GL11.glMatrixMode(GL11.GL_MODELVIEW);
		        GL11.glLoadIdentity();
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			    GL11.glEnable(GL11.GL_CULL_FACE);
			    GL11.glShadeModel(GL11.GL_SMOOTH);
			    GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_LIGHT0);
				GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
			}
				break;
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

		GL11.glClearColor(0,0,0,1);
	}

	 private void initDisplay()
	 {
		 try{
			 Display.setDisplayMode(new DisplayMode((int) size.x,(int) size.y));
			 Display.create(new PixelFormat(32, 8, 0, 0, 0, 0, 32, 8, false));
		 }catch (LWJGLException e)
		 {
			 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			 System.exit(1);
		 }
	 }

	 public enum GameType
	 {
		 RENDER_2D(),
		 RENDER_3D_LIGHTS(),
		 RENDER_3D();
	 }
	 
	 public enum EngineState
	 {
		 STOPPED(),
		 RUNNING(),
		 REQ_STOP(),
		 REQ_RESTART();
	 }
}
