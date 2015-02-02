package net.quantuminfinity.utils;

public class Vector3 {
	
	public float x;
	public float y;
	public float z;
	
	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float length()
	{
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3 set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3 copy()
	{
		return new Vector3(x,y,z);
	}
	
	public Vector3 add(Vector3 v)
	{
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return this;
	}
	
	public Vector3 sub(Vector3 v)
	{
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
		return this;
	}

	public Vector3 normalise()
	{
		float length = length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}
	
	public Vector3 mult(float n)
	{
		this.x *= n;
		this.y *= n;
		this.z *= n;
		return this;
	}
	
	public Vector3 addl(float length)
	{
		Vector3 v = this.copy().normalise().mult(length);
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return this;
	}

	public boolean is(Vector3 v)
	{
		if (v.x == this.x && v.y == this.y && v.z == this.z)
			return true;
		return false;
	}
	
	public float dot(Vector3 b)
	{
		return this.x * b.x + this.y * b.y + this.z * b.z;
	}
}
