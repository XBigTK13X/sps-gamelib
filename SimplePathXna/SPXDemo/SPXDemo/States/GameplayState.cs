using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using SPXDemo.GameObjects;
using SPXDemo.Management;
using SPXDemo.Factory;
using SimplePathXna.States;
using SimplePathXna.GameObjects;

namespace SPXDemo.States
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
