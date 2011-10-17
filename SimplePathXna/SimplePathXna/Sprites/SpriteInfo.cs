using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SimplePathXna.Sprites
{
    public class SpriteInfo
    {
        public static int Height = 64;
        public static int Width = 64;
        public static double Radius = Math.Sqrt(Math.Pow(Height / 2, 2) + Math.Pow(Width / 2, 2));
        public int X, Y, SpriteIndex, MaxFrame;

        public SpriteInfo(int spriteIndex,int maxFrame)
        {
            X = Width;
            Y = Height;
            SpriteIndex = spriteIndex;
            MaxFrame = maxFrame;
        }
    }
}
