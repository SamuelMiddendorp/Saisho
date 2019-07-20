#pragma once
#include <glm/glm.hpp>
class Vertex {
public: 
	Vertex(const glm::vec3& pos, const glm::vec2& textCoord) {
		this->pos = pos;
		this->textCoord = textCoord;
	}
	inline glm::vec3* GetPos() {
		return &pos;
	}
	inline glm::vec2* GettexCoord() {
		return &textCoord;
	}
private:
	glm::vec2 textCoord;
	glm::vec3 pos;
};