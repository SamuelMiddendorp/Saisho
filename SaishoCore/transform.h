#pragma once
#include <glm/glm.hpp>
#include <glm/gtx/transform.hpp>
class Transform
{
public:
	Transform(const glm::vec3& pos = glm::vec3(), const glm::vec3& rotation = glm::vec3(), const glm::vec3& scale = glm::vec3(1.0f, 1.0f, 1.0f)) {
		this->m_pos = pos;
		this->m_rotation = rotation;
		this->m_scale = scale;
	}

	inline glm::mat4 GetModel() const {

		glm::mat4 posMatrix = glm::translate(m_pos);
		glm::mat4 rotationXMatrix = glm::rotate(m_rotation.x, glm::vec3(1, 0, 0));
		glm::mat4 rotationYMatrix = glm::rotate(m_rotation.y, glm::vec3(0, 1, 0));
		glm::mat4 rotationZMatrix = glm::rotate(m_rotation.z, glm::vec3(0, 0, 1));
		glm::mat4 scaleMatrix = glm::scale(m_scale);

		glm::mat4 rotationMatrix = rotationZMatrix * rotationYMatrix * rotationXMatrix;

		return posMatrix * rotationMatrix * scaleMatrix;
	}

	inline glm::vec3& GetPos() { return m_pos; }
	inline glm::vec3& GetRotation() { return m_rotation; }
	inline glm::vec3& GetScale() { return m_scale; }

	inline void SetPos(glm::vec3& pos) { m_pos = pos; }
	inline void SetRotation(glm::vec3& rotation) { m_rotation = rotation; }
	inline void SetScale(glm::vec3& scale) { m_scale = scale; }
private:
	glm::vec3 m_pos;
	glm::vec3 m_rotation;
	glm::vec3 m_scale;
};

