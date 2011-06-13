using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using SimplePathXna.States;

namespace SimplePathXna.Management
{
    class StateManager
    {
        static private State m_state;
        static public void LoadState(State state)
        {
            m_state = state;
        }
    }
}
