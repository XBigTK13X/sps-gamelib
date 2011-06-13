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
            GameplayObjectFactory.Create(GameObjectType.PLAYER, 100, 100);
            GameplayObjectFactory.Create(GameObjectType.FLOOR, 200, 200);
            GameplayObjectFactory.Create(GameObjectType.SPIKE, 300, 300);
        }
    }
}
