#version 400 core
in vec3 color;
in vec3 normal;
in vec3 FragPos;
out vec4 out_Color;
uniform vec3 lightPos;
void main(){
    vec3 lightVec = lightPos - FragPos;
    float dist = length(lightVec);
    float a = 0.01;
    float b = 0.01;
    float inten = 1.0 / (a * dist * dist * b * dist + 1.0f);
    vec3 lightColor = vec3(1.0f, 1.0f, 1.0f);

    float ambientStrength = 0.4;
    vec3 ambient = ambientStrength * lightColor;

    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(lightPos - FragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    vec3 result = (ambient + diffuse * inten) * color;
    out_Color = vec4(result, 1.0f);
}