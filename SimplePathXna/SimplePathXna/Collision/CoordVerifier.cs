using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.GameObjects;
using SimplePathXna.Management;

namespace SimplePathXna.Collision
{
    public static class CoordVerifier
    {
        public static bool IsValid(Point2 position)
        {
            return (position.PosCenterX > 0 && position.PosCenterY > 0 && position.PosCenterX < XnaManager.WindowWidth && position.PosCenterY < XnaManager.WindowHeight);
        }
    }
}
