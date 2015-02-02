#version 130

uniform sampler2D data0;
uniform sampler2D data1;
uniform vec2 uResolution;
uniform int uTexsize;

varying float age;

void main()
{	
	highp vec2 uv = vec2(floor(gl_VertexID/float(uTexsize))/float(uTexsize),mod(gl_VertexID, float(uTexsize))/float(uTexsize));
	highp vec2 pos = texture(data0, uv).rg;
	age = texture(data0,uv).b * 100;
	gl_Position = gl_ModelViewProjectionMatrix * vec4(pos*uResolution,0,1);
}