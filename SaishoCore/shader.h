#pragma once
#include <string>
#include <GL/glew.h>
#include "transform.h"
#include "Camera.h"

class Shader
{
public:
	Shader(const std::string& filename);

	void BindShader();
	void Update(const Transform& transform, const Camera& camera);
	virtual ~Shader();
private:
	
	static const unsigned int NUM_SHADERS = 2;

	enum {
		TRANSFORM_U,

		NUM_UNIFORMS
	};
	GLuint m_program;
	GLuint m_shaders[NUM_SHADERS];
	GLuint m_uniforms[NUM_UNIFORMS];
};

