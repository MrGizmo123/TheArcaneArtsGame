#version 400 core

in vec3 pos;
in vec2 texCoords;

out vec2 passTextureCoords;

uniform mat4 projMatrix;
uniform mat4 transMatrix;
uniform float displayAspectRatio;

void main(void){

	gl_Position =  transMatrix * vec4(pos.x / displayAspectRatio,pos.yz, 1);

	passTextureCoords = texCoords;

}
