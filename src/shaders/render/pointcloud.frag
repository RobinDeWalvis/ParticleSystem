#version 130

uniform sampler2D data0;
uniform sampler2D data1;
uniform sampler2D data2;
uniform vec2 uResolution;
uniform int uTexsize;
uniform float uTime;

uniform vec3 color;

float pi = 3.14159265359;

varying float age;

void main()
{	
	vec2 uv = gl_FragCoord.xy/uResolution;
	gl_FragColor = vec4(color, .05);
}