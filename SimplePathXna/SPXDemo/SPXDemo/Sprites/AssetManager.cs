using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.Sprites;
using SimplePathXna.Management;
using Microsoft.Xna.Framework.Input;
using SPXDemo.IO;

namespace SPXDemo.Sprites
{
    public class AssetManager
    {
        public static void Initialize()
        {
            InputManager.Add(Input.MOVE_UP,Keys.Up,Buttons.DPadUp);
            InputManager.Add(Input.MOVE_DOWN,Keys.Down,Buttons.DPadDown);
            InputManager.Add(Input.MOVE_RIGHT,Keys.Right,Buttons.DPadRight);
            InputManager.Add(Input.MOVE_LEFT,Keys.Left,Buttons.DPadLeft);
            InputManager.Add(Input.CONFIRM,Keys.Space,Buttons.A);

            SpriteSheetManager.Add(SpriteType.EMPTY, 1);
            SpriteSheetManager.Add(SpriteType.PLAYER, 2);
            SpriteSheetManager.Add(SpriteType.WALL,1);
            SpriteSheetManager.Add(SpriteType.SPIKE, 1);
        }
    }
}
