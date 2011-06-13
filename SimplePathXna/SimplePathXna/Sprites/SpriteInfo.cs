﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SimplePathXna.Sprites
{
    public class SpriteInfo
    {
        public static int Height = 33;
        public static int Width = 33;
        public static double Radius = Math.Sqrt(Math.Pow(Height / 2, 2) + Math.Pow(Width / 2, 2));
        public int X, Y, SpriteIndex, MaxFrame;

        public SpriteInfo(int spriteIndex,int maxFrame)
        {
            X = Width;
            Y = Height;
            SpriteIndex = spriteIndex;
            MaxFrame = maxFrame;
        }

        public static void SetDimensions(int height,int width)
        {
            Height = height;
            Width = width;
            Radius = Math.Sqrt(Math.Pow(Height / 2, 2) + Math.Pow(Width / 2, 2));
        }
    }
}
