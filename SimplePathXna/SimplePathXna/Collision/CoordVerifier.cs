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
            return (position.PosX > 0 && position.PosY > 0 && position.PosX < XnaManager.WindowWidth && position.PosY < XnaManager.WindowHeight);
        }
    }
}
