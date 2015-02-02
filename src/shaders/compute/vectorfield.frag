#version 130
//#define INTERPOLATE

uniform sampler2D data0;
uniform sampler2D data1;
uniform sampler2D field;

uniform vec2 uResolution;
uniform vec2 uRes;
uniform vec4 uMouse;
uniform float uTime;
uniform float g;

uniform int useNoise;
uniform int useGravity;
uniform int useAge;

float mid = 0.5;

float pi = 3.14159265359;

void main()
{
	vec2 uv = gl_FragCoord.xy/uResolution;
	vec2 pos = texture(data0, uv).rg;
	vec2 vel = texture(data1, uv).rg - mid;
	vec2 flow = texture(field, vec2(pos.x, 1-pos.y)).rg - mid;
	float age = texture(data0, uv).b;
	float ratio = uRes.x / uRes.y;

	pos.x += flow.x/200;
	pos.y += flow.y/200 * ratio;

	gl_FragData[0] = vec4(pos, age, 1);
	gl_FragData[1] = vec4(vel + mid, 1, 1);
}