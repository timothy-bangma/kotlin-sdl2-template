package bangma

import io.github.libsdl4j.api.Sdl
import io.github.libsdl4j.api.SdlSubSystemConst.SDL_INIT_EVERYTHING
import io.github.libsdl4j.api.error.SdlError
import io.github.libsdl4j.api.event.SDL_Event
import io.github.libsdl4j.api.event.SDL_EventType
import io.github.libsdl4j.api.event.SdlEvents
import io.github.libsdl4j.api.event.events.SDL_KeyboardEvent
import io.github.libsdl4j.api.keycode.SDL_Keycode
import io.github.libsdl4j.api.render.SDL_RendererFlags
import io.github.libsdl4j.api.render.SdlRender
import io.github.libsdl4j.api.video.SDL_WindowFlags
import io.github.libsdl4j.api.video.SdlVideo
import io.github.libsdl4j.api.video.SdlVideoConst.SDL_WINDOWPOS_CENTERED

const val WINDOW_HEIGHT = 768
const val WINDOW_WIDTH = 1024
const val WINDOW_PROPERTIES = SDL_WindowFlags.SDL_WINDOW_SHOWN or SDL_WindowFlags.SDL_WINDOW_RESIZABLE
const val WINDOW_TITLE = "Kotlin SDL2"

fun main() {
    Sdl.SDL_Init(SDL_INIT_EVERYTHING).also {
        if (it != 0) return println("bad lib init. Error code: $it")
    }

    val window = SdlVideo.SDL_CreateWindow(
        WINDOW_TITLE, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_PROPERTIES
    ).also {
        if (it == null) return println("bad window init. ${SdlError.SDL_GetError()}")
    }

    val renderer = SdlRender.SDL_CreateRenderer(
        window, -1, SDL_RendererFlags.SDL_RENDERER_ACCELERATED
    ).also {
        if (it == null) return println("bad renderer init ${SdlError.SDL_GetError()}")
    }

    // set renderer color to green
    SdlRender.SDL_SetRenderDrawColor(renderer, 0, 0xF, 0, 0xF)
    // clear window and make it red
    SdlRender.SDL_RenderClear(renderer)
    // render changes to screen
    SdlRender.SDL_RenderPresent(renderer)

    eventLoop()

    Sdl.SDL_Quit()
}

fun eventLoop() {
    val event = SDL_Event()
    var quit = false

    while (!quit) {
        while (SdlEvents.SDL_PollEvent(event) != 0) {
            when (event.type) {
                SDL_EventType.SDL_QUIT -> quit = true
                SDL_EventType.SDL_KEYDOWN -> handleInput(event.key)
                SDL_EventType.SDL_WINDOWEVENT -> println("Window event! ${event.window.event}")
            }
        }
    }
}

fun handleInput(event: SDL_KeyboardEvent) {
    println("Input received!")
    when (event.keysym.sym) {
        SDL_Keycode.SDLK_SPACE -> println("SPACE pressed.")
        else -> println("something else pressed.")
    }
}