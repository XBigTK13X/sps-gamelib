using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SPXDemo.Management;
using SimplePathXna.Collision;
using SPXDemo.Sprites;
using SimplePathXna.GameObjects;
using SimplePathXna.Management;

namespace SPXDemo.GameObjects
{
    class Wall:GameplayObject
    {
        public Wall(int gridX, int gridY)
        {
            Initialize(gridX, gridY, SpriteType.WALL,GameObjectType.WALL);
        }
        public override void Update()
        {
            base.Update();
            var player = GameplayObjectManager.GetObject(GameObjectType.PLAYER) as Player;
            if(null != player)
            {
                if (HitTest.IsTouching(player, this))
                {
                    player.Move(new Point2(-player.GetVelocity().X,-player.GetVelocity().Y));
                }
            }
        }
    }
}
