sps-gamelib is a game engine powered by libgdx.

It aims to make developing for Lin/Mac/Win take as little time as possible.

Non-desktop platforms are not supported at this time.

New features and improvements to sps-gamelib are pulled from each Simple Path Studios game immediately following it's 1.0.0 release.

I strive to keep code reuse high between games to ease prototyping. That means projects can be thrown together quickly, but limits many configuration options.

Currently it provides:
 - Command based input detection system
 - Input agnostic player management
 - Supports both keyboard chords and controllers for player input
 - State handling architecture with isolated state components (Text, Audio, Graphics)
 - Preloading framework, for both the engine itself and arbitrary states
 - Autoloaded sprite animations
 - Asset management (sound, music, graphics, game data, and user data)
 - Music and Sound Effects players
 - Entity management for centralized access to game objects
 - Efficient text rendering and effects
 - Easily editable and in-game configurable OpenGL vertex and fragment shaders
 - Procedural texture generation (Perlin noise, radial gradients, linear gradients)
 - Integrated tutorials
 - In-game Tutorial, Pause Menu, and Exit Menu prompts
 - User accessible control configuration menu
 - User accessible player selection menu
 - Default main menu system
 - Deferred ordering on render calls
 - Fixed and moveable rendering viewports
 - Lightweight UI elements with straightforward styling
 - On-the-fly font cache generation, no more ugly stretched and squished bitmap fonts


This framework uses the MIT license. See LICENSE.txt for terms of that license.
