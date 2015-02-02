package net.quantuminfinity.particles.console;

import net.quantuminfinity.particles.Particles;
import net.quantuminfinity.utils.Vector3;

public class Settings
{
	public static int textureSize;
	public static boolean useNoise, useGravity, useAge;
	public static float gravity;
	public static Vector3 color;
	
	public static void resetValues()
	{
		textureSize = Particles.inIDE ? 1024 : 512;
		useNoise = true;
		useGravity = true;
		useAge = true;
		gravity = 0.00075f;
		color = new Vector3(0.5f, 1f, 0.1f);
	}
}
