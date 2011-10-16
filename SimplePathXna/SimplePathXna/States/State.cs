using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using SimplePathXna.GameObjects;
using SimplePathXna.Management;


namespace SimplePathXna.States
{
    public abstract class State
    {
        public abstract void Draw();
        public abstract void Update();
        public abstract void LoadContent();
    }
}
