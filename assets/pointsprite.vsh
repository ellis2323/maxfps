uniform mat4 uMvp;

attribute vec3 aPosition;
attribute vec2 aTexCoord; 
attribute vec4 aColor;

varying vec4 vColor;

void main() {
    vec4 position = uMvp * vec4(aPosition.xyz, 1.);
    vColor = aColor;
    
    gl_PointSize = 4.;
	gl_Position =  position;
}
