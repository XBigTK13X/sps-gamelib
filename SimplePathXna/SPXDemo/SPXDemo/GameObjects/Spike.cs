﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SPXDemo.Management;
using SPX.GameObjects;
using SPX.Management;
using SPX.Collision;
using SPXDemo.Sprites;

namespace SPXDemo.GameObjects
{
    class Spike:GameplayObject
    {
        public Spike(int x, int y)
        {
            Initialize(x, y, SpriteType.SPIKE,GameObjectType.SPIKE);
        }
        public override void Update()
        {
            base.Update();
            if (null != GameplayObjectManager.GetObject(GameObjectType.PLAYER))
            {
                if (HitTest.IsTouching(this, GameplayObjectManager.GetObject(GameObjectType.PLAYER)))
                {
                    GameplayObjectManager.GetObject(GameObjectType.PLAYER).SetInactive();
                }
            }
        }
    }
}
