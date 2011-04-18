using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using SimplePathXna.GameObjects;
using SimplePathXna.Management;
using SimplePathXna.Factory;
using SimplePathXna.Sprites;

namespace SimplePathXna.States
{
    class GameplayState:State
    {
        public GameplayState()
        {
            Add(GameplayObjectFactory.Create(SpriteType.PLAYER_STAND, 100, 100));
            Add(GameplayObjectFactory.Create(SpriteType.FLOOR,200,200));
            Add(GameplayObjectFactory.Create(SpriteType.SPIKE,300, 300));
        }
    }
}
