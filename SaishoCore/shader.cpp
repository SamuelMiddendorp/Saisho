#include "shader.h"
#include <fstream>
#include <iostream>


static GLuint CreateShader(const std::string& text, GLenum shadertype);
static std::string LoadShader(const std::string& fileName);
static void CheckShaderError(GLuint shader, GLuint flag, bool isProgram, const std::string& errorMessage);

Shader::Shader(const std::string& fileName)
{

	m_program = glCreateProgram();
	m_shaders[0] = CreateShader(LoadShader(fileName + ".vs"), GL_VERTEX_SHADER);
	m_shaders[1] = CreateShader(LoadShader(fileName + ".fs"), GL_FRAGMENT_SHADER);

	for (unsigned int i = 0; i < NUM_SHADERS; i++) {
		glAttachShader(m_program, m_shaders[i]);

	}
	glBindAttribLocation(m_program, 0, "position");
	glBindAttribLocation(m_program, 1, "texCoord");

	glLinkProgram(m_program);
	CheckShaderError(m_program, GL_LINK_STATUS, true, "Shader error");

	glValidateProgram(m_program);
	CheckShaderError(m_program, GL_VALIDATE_STATUS, true, "Shader error");

	m_uniforms[TRANSFORM_U] = glGetUniformLocation(m_program, "transform");

}




static std::string LoadShader(const std::string& fileName)
{
	std::ifstream file;
	file.open((fileName).c_str());

	std::string output;
	std::string line;

	if (file.is_open())
	{
		while (file.good())
		{
			getline(file, line);
			output.append(line + "\n");
		}
	}
	else
	{
		std::cout << "Unable to load shader: " << fileName << std::endl;
	}

	return output;
}

static void CheckShaderError(GLuint shader, GLuint flag, bool isProgram, const std::string& errorMessage)
{
	GLint success = 0;
	GLchar error[1024] = { 0 };

	if (isProgram)
		glGetProgramiv(shader, flag, &success);
	else
		glGetShaderiv(shader, flag, &success);

	if (success == GL_FALSE)
	{
		if (isProgram)
			glGetProgramInfoLog(shader, sizeof(error), NULL, error);
		else
			glGetShaderInfoLog(shader, sizeof(error), NULL, error);

		std::cerr << errorMessage << ": '" << error << "'" << std::endl;
	}
}
void Shader::BindShader() {

	glUseProgram(m_program);

}
void Shader::Update(const Transform& transform, const Camera& camera) {

	glm::mat4 model = camera.GetViewProjection() * transform.GetModel();
	glUniformMatrix4fv(m_uniforms[TRANSFORM_U], 1, GL_FALSE, &model[0][0]);
}
static GLuint CreateShader(const std::string& text, GLenum shaderType) {

	GLuint shader = glCreateShader(shaderType);

	if (shader == 0) {

		std::cerr << "Error: Shader creation failed !" << std::endl;

	}

	const GLchar* shaderSource[1];
	GLint shaderSourceLengths[1];

	shaderSource[0] = text.c_str();
	shaderSourceLengths[0] = text.length();

	glShaderSource(shader, 1, shaderSource, shaderSourceLengths);
	glCompileShader(shader);

	CheckShaderError(shader, GL_COMPILE_STATUS, false, "Shader error");

	return shader;

}

Shader::~Shader()
{
	for (unsigned i = 0; i < NUM_SHADERS; i++) {

		glDetachShader(m_program, m_shaders[i]);
		glDeleteShader(m_shaders[i]);

	}
	glDeleteProgram(m_program);

}
