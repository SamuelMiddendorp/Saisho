#include "display.h"
#include <iostream>
#include <GL/glew.h>
#include "mesh.h"
#include "shader.h"
#include "texture.h"
#include "transform.h"
#include "Camera.h"

int main(int argc, char **argv){

	Display window(1920, 1080, "SaishoCore");

	Shader shader("./res/shaders/basicShader");
	Texture texture("./res/textures/kirsten.png");

	Vertex vertices[] = { Vertex(glm::vec3(-0.5,-0.5,0), glm::vec2(0.0,0.0)),
						  Vertex(glm::vec3(-0.5,0.5,0), glm::vec2(0.5,1.0)),
						  Vertex(glm::vec3(0.5,-0.5,0), glm::vec2(1.0,0.0))

							  };
	Mesh mesh(vertices, sizeof(vertices) / sizeof(vertices[0]));
	Vertex vertices2[] = { Vertex(glm::vec3(0.0,0.5,0.0), glm::vec2(0.5,1.0)),
						  Vertex(glm::vec3(0.5,-0.5,0.0), glm::vec2(0.0,0.0)),
						  Vertex(glm::vec3(-0.5,-0.5,0.0), glm::vec2(1.0,0.0))

	};
	Mesh mesh2(vertices2, sizeof(vertices2) / sizeof(vertices2[0]));

	Camera camera(glm::vec3(0, 0, -2), 70.0f, 1920 / 1080, 0.01f, 1000.0f);
	float counter = 0.0f;
	Transform transform;

	while (!window.IsClosed()) {
		
		std::cout << counter << std::endl;
		window.Clear(1.0f, 1.0f, 1.0f, 1.0f);
		transform.GetPos().z = cosf(counter);
		//transform.GetPos().x = counter/2;
		//transform.GetPos().y = counter/2;
		//transform.GetRotation().y = counter;
		transform.GetRotation().x = -counter / 2;
		//transform.GetRotation().x = cosf(counter / 10);
		
		//transform.GetScale().x = cosf(counter);
		//transform.GetScale().x = cosf(counter);
		//
		//transform.GetScale().z = cosf(counter);
		shader.BindShader();
		texture.Bind(0);
		shader.Update(transform, camera);
		//mesh.Draw();
		
		mesh2.Draw();

		window.Update();
		counter += 0.1f;
	}
	return 0;
}