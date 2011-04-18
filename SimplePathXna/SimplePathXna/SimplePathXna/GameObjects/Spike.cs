using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.Management;
using SimplePathXna.Collision;
using SimplePathXna.Sprites;

namespace SimplePathXna.GameObjects
{
    class Spike:GameplayObject
    {
        public Spike(int x, int y)
        {
            Initialize(x, y, SpriteType.SPIKE);
        }
        public override void Update()
        {
            base.Update();
            if (null != GameplayObjectManager.GetObject(SpriteType.PLAYER_STAND))
            {
                if (HitTest.IsTouching(this, GameplayObjectManager.GetObject(SpriteType.PLAYER_STAND)))
                {
                    GameplayObjectManager.GetObject(SpriteType.PLAYER_STAND).SetInactive();
                }
            }
        }
    }
}
