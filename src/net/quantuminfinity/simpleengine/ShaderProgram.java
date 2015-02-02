package net.quantuminfinity.simpleengine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import net.quantuminfinity.utils.JarFileReader;
import net.quantuminfinity.utils.Vector2;
import net.quantuminfinity.utils.Vector3;
import net.quantuminfinity.utils.Vector4;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram
{	
	private ArrayList<Integer> shaders;
	private int program,loc;
	private boolean compiled;
	private FloatBuffer fbuffer;
	private IntBuffer ibuffer;
	
	public ShaderProgram()
	{
		shaders = new ArrayList<Integer>();
		compiled = false;
	}
	
	public void setUniform(String uniform, int value)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		GL20.glUniform1i(loc, value);
	}
	
	public void setUniform(String uniform, int[] value)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		ibuffer = BufferUtils.createIntBuffer(value.length);
		ibuffer.put(value);
		ibuffer.rewind();
		GL20.glUniform1(loc, ibuffer);
	}
	
	public void setUniform(String uniform, float value)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		GL20.glUniform1f(loc, value);
	}
	
	public void setUniform(String uniform, float[] value)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		fbuffer = BufferUtils.createFloatBuffer(value.length);
		fbuffer.put(value);
		fbuffer.rewind();
		GL20.glUniform1(loc, fbuffer);
	}
	
	public void setUniform(String uniform, Vector2 value)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		GL20.glUniform2f(loc, value.x, value.y);
	}
	
	public void setUniform(String uniform, float x, float y)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		GL20.glUniform2f(loc, x, y);
	}
	
	public void setUniform(String uniform, Vector3 value)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		GL20.glUniform3f(loc, value.x, value.y, value.z);
	}
	
	public void setUniform(String uniform, Vector4 value)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		GL20.glUniform4f(loc, value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String uniform, float x, float y, float z, float w)
	{
		loc = GL20.glGetUniformLocation(program, uniform);
		GL20.glUniform4f(loc, x, y, z, w);
	}
	
	public void bind()
	{
		if(compiled)
			ARBShaderObjects.glUseProgramObjectARB(program);
	}
	
	public void release()
	{
		if(compiled)
			ARBShaderObjects.glUseProgramObjectARB(0);
	}
	
	public void addShader(String location)
	{
		int shader = 0;
			if (location.endsWith(".vert"))
				shader = createShader(location ,ARBVertexShader.GL_VERTEX_SHADER_ARB);
			else if (location.endsWith(".frag"))
				shader = createShader(location, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
			else
			{
				JOptionPane.showMessageDialog(null, "File has to end ether with '.vert' or '.frag'", "Error", JOptionPane.WARNING_MESSAGE);
				System.exit(1);
			}
		if (shader == 0)
			throw new RuntimeException("Error in shader");
		shaders.add(shader);
	}
	
	public void compile()
	{
		program = ARBShaderObjects.glCreateProgramObjectARB();
		if (program == 0)
		{
			JOptionPane.showMessageDialog(null, "Shader = 0", "Error", JOptionPane.WARNING_MESSAGE);
			throw new RuntimeException("Shader = 0");
		}
		
		for (Integer shader:shaders)
		{
			ARBShaderObjects.glAttachObjectARB(program, shader);
		}
		
		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE)
		{
			JOptionPane.showMessageDialog(null, "Shader error: " + getLogInfo(program), "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE)
		{
			JOptionPane.showMessageDialog(null, "Shader error: " + getLogInfo(program), "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		compiled = true;
	}
	
	public void destroy()
	{
		GL20.glUseProgram(0);
		GL20.glDeleteProgram(program);
	}
	
	private int createShader(String filename, int shaderType)
	{
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
			
			if(shader == 0)
				return 0;
			
			ARBShaderObjects.glShaderSourceARB(shader, JarFileReader.read(filename));
			ARBShaderObjects.glCompileShaderARB(shader);
			if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
			{
				JOptionPane.showMessageDialog(null, "Error creating shader "+filename+": " + getLogInfo(shader), "Error", JOptionPane.WARNING_MESSAGE);
				System.exit(1);
			}
			
		}catch(Exception e)
		{
			ARBShaderObjects.glDeleteObjectARB(shader);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		}
		return shader;
	}
	
	private static String getLogInfo(int obj)
	{
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}
}
