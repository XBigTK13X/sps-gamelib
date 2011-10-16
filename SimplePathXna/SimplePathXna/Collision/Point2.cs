﻿using System;
using System.Collections.Generic;
using Microsoft.Xna.Framework;
using SimplePathXna.Sprites;

namespace SimplePathXna.Collision
{
    public class Point2
    {
        private static readonly Point2 Zero = new Point2(0, 0);
        private static readonly float halfHeight = SpriteInfo.Height / 2;
        private static readonly float halfWidth = SpriteInfo.Width / 2;

        public float X { get; private set; }
        public float Y { get; private set; }
        public float Weight { get; private set; }
        public int GridX { get; private set; }
        public int GridY { get; private set; }
        public float PosX { get; private set; }
        public float PosY { get; private set; }
        public float PosCenterX { get; private set; }
        public float PosCenterY { get; private set; }

        public Point2(float x, float y, int weight = 0)
        {
            SetX(x);
            SetY(y);
            Weight = weight;
        }

        public Point2(Point2 target)
        {
            SetX(target.X);
            SetY(target.Y);
            Weight = 0;
        }

        public Point2(Vector2 target)
        {
            SetX(target.X);
            SetY(target.Y);
            Weight = 0;
        }

        public override int GetHashCode()
        {
            return GridX + 1000 * GridY;
        }

        public override bool Equals(object obj)
        {
            if (obj.GetType() == typeof(Point2))
            {
                var target = (Point2)obj;
                return target.GridX == GridX && target.GridY == GridY;
            }
            return false;
        }

        public void SetX(float xValue)
        {
            X = xValue;
            var isGrid = false;
            PosX = (isGrid) ? X * SpriteInfo.Width : X;
            PosCenterX = (isGrid) ? X * SpriteInfo.Width + halfWidth : X + halfWidth;
            GridX = (isGrid) ? (int)X : (int)(X / SpriteInfo.Width);
        }

        public void SetY(float yValue)
        {
            Y = yValue;
            var isGrid = false;
            PosY = (isGrid) ? Y * SpriteInfo.Height : Y;
            PosCenterY = (isGrid) ? Y * SpriteInfo.Height + halfHeight : Y + halfHeight;
            GridY = (isGrid) ? (int)Y : (int)(Y / SpriteInfo.Height);
        }

        public void SetWeight(float weight)
        {
            Weight = weight;
        }

        public bool IsZero()
        {
            return X == 0 && Y == 0;
        }

        public Point2 Multiply(float factor)
        {
            return new Point2(X * factor, Y * factor);
        }

        public static float CalculateDistanceSquared(Point2 source, Point2 target)
        {
            return (float)(Math.Pow(source.PosY - target.PosY, 2) + Math.Pow(source.PosX - target.PosX, 2));
        }

        public IList<Point2> GetNeighbors()
        {
            var result = new List<Point2>();
            for (var ii = -1; ii < 2; ii++)
            {
                for (var jj = -1; jj < 2; jj++)
                {
                    if (ii != 0 || jj != 0)
                    {
                        result.Add(new Point2(GridX + ii, GridY + jj));
                    }
                }
            }
            return result;
        }

        public override string ToString()
        {
            return String.Format("Point2: ({0},{1})", X, Y);
        }

        public static float DistanceSquared(Point2 source, Point2 target)
        {
            return (float)(Math.Pow(source.PosX - target.PosX, 2) + Math.Pow(source.PosY - target.PosY, 2));
        }

        public Point2 RotateClockwise()
        {
            if (GridX == 1)
            {
                if (GridY == -1)
                {
                    return new Point2(1, 0);
                }
                if (GridY == 0)
                {
                    return new Point2(1, 1);
                }
                if (GridY == 1)
                {
                    return new Point2(0, 1);
                }
            }
            if (GridX == -1)
            {
                if (GridY == -1)
                {
                    return new Point2(0, -1);
                }
                if (GridY == 0)
                {
                    return new Point2(-1, -1);
                }
                if (GridY == 1)
                {
                    return new Point2(-1, 0);
                }
            }
            if (GridX == 0)
            {
                if (GridY == 1)
                {
                    return new Point2(-1, 1);
                }
                if (GridY == -1)
                {
                    return new Point2(1, -1);
                }
            }
            return new Point2(0, 0);
            /*
             * This is getting close, but the flipped Y coord is ticking me off.
            var theta = Math.PI / 4f;
            var currentRotation = Math.Atan2(-Y, X);
            Console.WriteLine(currentRotation);
            currentRotation -= theta;
            var x = (float)Math.Cos(currentRotation);
            var y = (float)Math.Sin(currentRotation);
            if(x!=0)
            {
                x = (1 / (Math.Abs(x))) * x;
            }
            if (y != 0)
            {
                y = (1 / (Math.Abs(y))) * y;
            }
            var result = new Point2(x,y);
            Console.WriteLine(result);
            return result;
             * */
        }
    }
}