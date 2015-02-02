#version 130

uniform sampler2D data;
uniform sampler2D field;
uniform vec2 uResolution;
uniform int uTexsize;
uniform float uTime;
uniform vec4 uMouse;

float pi = 3.14159265359;

void main()
{
	vec2 uv = gl_FragCoord.xy/uResolution;
	gl_FragColor = texture(data, uv);
}