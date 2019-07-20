#pragma once

#include <iostream>
#include <SDL2/SDL.h>

class Display 
{
public:
	Display(int width, int height, const std::string& title);

	void Clear(float r, float g, float b, float a);
	void Update();

	bool IsClosed();
	virtual ~Display();

private:

	SDL_Window* m_window;
	SDL_GLContext m_context;
	bool m_isClosed;
};