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
        public int SpriteIndex, MaxFrame;

        public SpriteInfo(int spriteIndex,int maxFrame)
        {
            SpriteIndex = spriteIndex;
            MaxFrame = maxFrame;
        }

        public static float NormalizeDistance(float amount)
        {
            var isNeg = (amount < 0) ? -1 : 1;
            amount = Math.Abs(amount);
            var factorsOfSpriteHeight = (int)Math.Floor(amount / SpriteInfo.Height);
            factorsOfSpriteHeight = (factorsOfSpriteHeight == 0 && amount != 0) ? 1 : factorsOfSpriteHeight;
            return (SpriteInfo.Height * factorsOfSpriteHeight * isNeg);
        }
    }
}
