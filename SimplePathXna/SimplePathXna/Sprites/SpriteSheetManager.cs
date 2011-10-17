using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SimplePathXna.GameObjects;

namespace SimplePathXna.Sprites
{
    class SpriteSheetManager
    {
        private static Dictionary<SpriteType, SpriteInfo> m_manager = new Dictionary<SpriteType, SpriteInfo>()
        {
            {SpriteType.EMPTY,new SpriteInfo(0,1)},
            {SpriteType.PLAYER_STAND,new SpriteInfo(1,2)},
            {SpriteType.WALL,new SpriteInfo(2,1)},
            {SpriteType.SPIKE,new SpriteInfo(3,1)}
        };
        public static SpriteInfo GetSpriteInfo(SpriteType spriteName)
        {
            return m_manager[spriteName];
        }
        public static void Add(SpriteType type,SpriteInfo info)
        {
            m_manager.Add(type, info);
        }
    }
}
