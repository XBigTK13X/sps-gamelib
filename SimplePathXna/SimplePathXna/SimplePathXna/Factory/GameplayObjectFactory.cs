using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.GameObjects;
using SimplePathXna.Sprites;

namespace SimplePathXna.Factory
{
    class GameplayObjectFactory
    {
        static private int s_playerCount = 0;
        static public GameplayObject Create(SpriteType type, int x, int y)
        {
            switch (type)
            {
                case SpriteType.PLAYER_STAND:
                    return new Player(x, y,s_playerCount++);
                case SpriteType.FLOOR:
                    return new Floor(x, y);
                case SpriteType.SPIKE:
                    return new Spike(x, y);
                default:
                    throw new Exception("An undefined SpriteType case was passed into the GameplayObjectFactory.");
            }
        }
        static public void ResetPlayerCount()
        {
            s_playerCount = 0;
        }
        static public void IncreasePlayerCount()
        {
            s_playerCount ++;
        }
    }
}
