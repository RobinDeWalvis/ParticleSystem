package net.quantuminfinity.particles;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.quantuminfinity.utils.Vector4;

public class Input
{
	public Vector4 mouse;
	public boolean wasResized;
	public long startTime;
	
	public Input()
	{
		mouse = new Vector4((float) Mouse.getX() / (float) Display.getWidth(),
				(float) Mouse.getY() / (float) Display.getHeight(),
				Mouse.isButtonDown(0) ? 1 : 0, Mouse.isButtonDown(1) ? 1 : 0);
		wasResized = false;
		startTime = System.nanoTime();
	}
	
	public void update()
	{
		mouse.set((float) Mouse.getX() / Display.getWidth(),
				  (float) Mouse.getY() / Display.getHeight(),
				  Mouse.isButtonDown(0) ? 1 : 0, Mouse.isButtonDown(1) ? 1 : 0);
		wasResized = Display.wasResized();
	}
	
	public float getTimeSec()
	{
		return (float)(System.nanoTime() - startTime)/1000000000.0f;
	}
}
