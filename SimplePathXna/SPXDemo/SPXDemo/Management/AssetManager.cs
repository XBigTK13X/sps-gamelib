﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.Sprites;

namespace SPXDemo.Management
{
    public class AssetManager
    {
        public static void Initialize()
        {
            SpriteSheetManager.Add(SpriteType.EMPTY, 1);
            SpriteSheetManager.Add(SpriteType.PLAYER, 2);
            SpriteSheetManager.Add(SpriteType.WALL,1);
            SpriteSheetManager.Add(SpriteType.SPIKE, 1);
        }
    }
}
