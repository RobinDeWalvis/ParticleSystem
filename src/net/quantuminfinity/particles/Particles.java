package net.quantuminfinity.particles;

import net.quantuminfinity.particles.console.Settings;
import net.quantuminfinity.simpleengine.Engine;

public class Particles
{		
	static Renderer renderer;
	static Engine engine;
	public static boolean inIDE = false;
	
	public Particles()
	{
		Settings.resetValues();
		renderer = new Renderer();
		engine = new Engine(renderer);
		engine.start();
		System.exit(0);
	}
	
	public static void main(String[] args)
	{
		if (args.length > 0 && args[0].equalsIgnoreCase("-dev"))
			inIDE = true;
		new Particles();
	}
}
