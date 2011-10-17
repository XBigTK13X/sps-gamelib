using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SimplePathXna.GameObjects;

namespace SimplePathXna.Sprites
{
    public class SpriteSheetManager
    {
        private static Dictionary<string, SpriteInfo> m_manager = new Dictionary<string, SpriteInfo>();
        public static SpriteInfo GetSpriteInfo(string spriteName)
        {
            return m_manager[spriteName];
        }
        public static void Add(string type,int framesOfAnimation)
        {
            m_manager.Add(type, new SpriteInfo(m_manager.Keys.Count(),framesOfAnimation));
        }
    }
}
