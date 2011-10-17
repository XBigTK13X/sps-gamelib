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
using SPX.States;
using SPX.GameObjects;
using SPX.Management;

namespace SPXDemo.States
{
    class GameplayState:State
    {
        public GameplayState()
        {
            GameplayObjectFactory.Create(GameObjectType.PLAYER, 100, 100);
            GameplayObjectFactory.Create(GameObjectType.WALL, 200, 200);
            GameplayObjectFactory.Create(GameObjectType.SPIKE, 300, 300);
        }
        public override void Draw()
        {
            GameplayObjectManager.Draw();
        }
        public override void LoadContent()
        {
            GameplayObjectManager.LoadContent();
        }
        public override void Update()
        {
            Console.WriteLine("LOAD");
            GameplayObjectManager.Update();
        }
    }
}
