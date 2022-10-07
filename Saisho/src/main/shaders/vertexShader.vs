#version 400 core
layout (location=0) in vec3 position;
layout (location=1) in vec3 in_color;
layout (location=2) in vec3 aNormal;

uniform mat4 viewMatrix;
uniform mat4 transformationMatrix = mat4(1.0);
uniform mat4 projectionMatrix;

out vec3 FragPos;
out vec3 normal;
out vec3 color;

void main(){
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    FragPos = vec3(transformationMatrix * vec4(position, 1.0));
    normal = mat3(transpose(inverse(transformationMatrix))) * aNormal;
    color = in_color;

}