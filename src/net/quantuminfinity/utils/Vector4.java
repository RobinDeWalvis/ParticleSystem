package net.quantuminfinity.utils;

public class Vector4
{
	public float x,y,z,w;
	
	public Vector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public float length()
	{
		return (float) Math.sqrt(x*x + y*y + z*z + w*w);
	}
	
	public Vector4 copy()
	{
		return new Vector4(x,y,z,w);
	}
	
	public Vector4 add(Vector4 v)
	{
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		this.w += v.w;
		return this;
	}
	
	public Vector4 sub(Vector4 v)
	{
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
		this.w -= v.w;
		return this;
	}

	public Vector4 normalise()
	{
		float length = length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;
		return this;
	}
	
	public Vector4 mult(float n)
	{
		this.x *= n;
		this.y *= n;
		this.z *= n;
		this.w *= n;
		return this;
	}
	
	public Vector4 addl(float length)
	{
		Vector4 v = this.copy().normalise().mult(length);
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		this.w += v.w;
		return this;
	}

	public boolean is(Vector4 v)
	{
		if (v.x == this.x && v.y == this.y && v.z == this.z && v.w == this.w)
			return true;
		return false;
	}

	public void set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
}
