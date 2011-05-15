using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.Management;
using SimplePathXna.Collision;
using SimplePathXna.Sprites;

namespace SimplePathXna.GameObjects
{
    class Floor:GameplayObject
    {
        public Floor(int gridX, int gridY)
        {
            Initialize(gridX, gridY, SpriteType.FLOOR,GameObjectType.FLOOR);
        }
        public override void Update()
        {
            base.Update();
            GameplayObject player = GameplayObjectManager.GetObject(GameObjectType.PLAYER);
            if(null != player)
            {
                if (HitTest.IsTouching(player, this))
                {
                    player.Move(-5, -5);
                }
            }
        }
    }
}
