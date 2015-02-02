#version 130

uniform sampler2D data0;
uniform sampler2D data1;
uniform vec2 uResolution;
uniform vec2 uRes;
uniform vec4 uMouse;
uniform float uTime;
uniform float g;

uniform int useNoise;
uniform int useGravity;
uniform int useAge;

float mid = 0.5;
float G = .01;
float D = .995;
float smoothness = .05;
float speed = 0.0004;
float pi = 3.14159265359;
float range = .015;

vec3 mod289(vec3 x){
  return x - floor(x * (1.0 / 289.0)) * 289.0;
}

vec4 mod289(vec4 x){
  return x - floor(x * (1.0 / 289.0)) * 289.0;
}

vec4 permute(vec4 x){
     return mod289(((x*34.0)+1.0)*x);
}

vec4 taylorInvSqrt(vec4 r)
{
  return 1.79284291400159 - 0.85373472095314 * r;
}

float snoise(vec3 v)
{ 
  const vec2  C = vec2(1.0/6.0, 1.0/3.0) ;
  const vec4  D = vec4(0.0, 0.5, 1.0, 2.0);

// First corner
  vec3 i  = floor(v + dot(v, C.yyy) );
  vec3 x0 =   v - i + dot(i, C.xxx) ;

// Other corners
  vec3 g = step(x0.yzx, x0.xyz);
  vec3 l = 1.0 - g;
  vec3 i1 = min( g.xyz, l.zxy );
  vec3 i2 = max( g.xyz, l.zxy );

  //   x0 = x0 - 0.0 + 0.0 * C.xxx;
  //   x1 = x0 - i1  + 1.0 * C.xxx;
  //   x2 = x0 - i2  + 2.0 * C.xxx;
  //   x3 = x0 - 1.0 + 3.0 * C.xxx;
  vec3 x1 = x0 - i1 + C.xxx;
  vec3 x2 = x0 - i2 + C.yyy; // 2.0*C.x = 1/3 = C.y
  vec3 x3 = x0 - D.yyy;      // -1.0+3.0*C.x = -0.5 = -D.y

// Permutations
  i = mod289(i); 
  vec4 p = permute( permute( permute( 
             i.z + vec4(0.0, i1.z, i2.z, 1.0 ))
           + i.y + vec4(0.0, i1.y, i2.y, 1.0 )) 
           + i.x + vec4(0.0, i1.x, i2.x, 1.0 ));

// Gradients: 7x7 points over a square, mapped onto an octahedron.
// The ring size 17*17 = 289 is close to a multiple of 49 (49*6 = 294)
  float n_ = 0.142857142857; // 1.0/7.0
  vec3  ns = n_ * D.wyz - D.xzx;

  vec4 j = p - 49.0 * floor(p * ns.z * ns.z);  //  mod(p,7*7)

  vec4 x_ = floor(j * ns.z);
  vec4 y_ = floor(j - 7.0 * x_ );    // mod(j,N)

  vec4 x = x_ *ns.x + ns.yyyy;
  vec4 y = y_ *ns.x + ns.yyyy;
  vec4 h = 1.0 - abs(x) - abs(y);

  vec4 b0 = vec4( x.xy, y.xy );
  vec4 b1 = vec4( x.zw, y.zw );

  //vec4 s0 = vec4(lessThan(b0,0.0))*2.0 - 1.0;
  //vec4 s1 = vec4(lessThan(b1,0.0))*2.0 - 1.0;
  vec4 s0 = floor(b0)*2.0 + 1.0;
  vec4 s1 = floor(b1)*2.0 + 1.0;
  vec4 sh = -step(h, vec4(0.0));

  vec4 a0 = b0.xzyw + s0.xzyw*sh.xxyy ;
  vec4 a1 = b1.xzyw + s1.xzyw*sh.zzww ;

  vec3 p0 = vec3(a0.xy,h.x);
  vec3 p1 = vec3(a0.zw,h.y);
  vec3 p2 = vec3(a1.xy,h.z);
  vec3 p3 = vec3(a1.zw,h.w);

//Normalise gradients
  vec4 norm = taylorInvSqrt(vec4(dot(p0,p0), dot(p1,p1), dot(p2, p2), dot(p3,p3)));
  p0 *= norm.x;
  p1 *= norm.y;
  p2 *= norm.z;
  p3 *= norm.w;

// Mix final noise value
  vec4 m = max(0.6 - vec4(dot(x0,x0), dot(x1,x1), dot(x2,x2), dot(x3,x3)), 0.0);
  m = m * m;
  return 42.0 * dot( m*m, vec4( dot(p0,x0), dot(p1,x1), 
                                dot(p2,x2), dot(p3,x3) ) );
}

float rand(vec2 co)
{
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main()
{
	vec2 uv = gl_FragCoord.xy/uResolution;
	vec2 pos = texture(data0, uv).rg;
	vec2 vel = texture(data1, uv).rg - mid;
	float age = texture(data0, uv).b;
	float ratio = uRes.x / uRes.y;
	
	if (useAge == 1)
	{
		age += 0.001 * rand(vec2(uv * 1000.));
		if (age >= 1)
		{
			float l = rand(vec2(uv.x*1000.,-uv.y*1000.)) * range;
			float p = rand(vec2(uv.y*1000., uv.x*1000.)) * pi * 2;
			
			pos = vec2(l*sin(p),l*cos(p)*ratio) + uMouse.xy;
			
			vel = vec2(0);
			age = 0;
		}
	}
	
	if (pos.x == 0. && pos.y == 0.)
		age = 1.;
	
	if (uMouse.z == 1 && uMouse.w == 0)
	{
		vec2 delta = uMouse.xy - pos;
		vel.x += G * delta.x * inversesqrt(delta.x*delta.x + delta.y*delta.y);
		vel.y += G * delta.y * inversesqrt(delta.x*delta.x + delta.y*delta.y);
	}
	
	if (uMouse.z == 0 && uMouse.w == 1)
	{
		vec2 delta = uMouse.xy - pos;
		vel.x -= G * delta.x * inversesqrt(delta.x*delta.x + delta.y*delta.y);
		vel.y -= G * delta.y * inversesqrt(delta.x*delta.x + delta.y*delta.y);
	}
	
	if (uMouse.z == 1 && uMouse.w == 1)
		vel = vec2(0);
	
	if (useNoise == 1)
	{
		float noise = snoise(vec3(pos/smoothness, uTime));
		vel.x += speed * cos(noise * pi * 2 - pi/2);
		vel.y += speed * sin(noise * pi * 2 - pi/2);
	}
	
	if (useGravity == 1)
	 	vel.y += g;
	
	vel *= D;
	pos.x += vel.x/16;
	pos.y += vel.y/16 * ratio;
	
	gl_FragData[0] = vec4(pos, age, 1);
	gl_FragData[1] = vec4(vel + mid, 1, 1);
}