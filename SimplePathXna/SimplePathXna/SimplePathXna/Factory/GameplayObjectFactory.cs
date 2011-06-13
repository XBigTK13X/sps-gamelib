using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.GameObjects;
using SimplePathXna.Sprites;
using SimplePathXna.Management;

namespace SimplePathXna.Factory
{
    class GameplayObjectFactory
    {
        static private int s_playerCount = 0;
        static private GameplayObject Construct(GameObjectType type, int x, int y)
        {
            switch (type)
            {
                case GameObjectType.PLAYER:
                    return new Player(x, y, s_playerCount++);
                case GameObjectType.FLOOR:
                    return new Floor(x, y);
                case GameObjectType.SPIKE:
                    return new Spike(x, y);
                default:
                    throw new Exception("An undefined GameObjectType case was passed into the GameplayObjectFactory.");
            }
        }
        static public GameplayObject Create(GameObjectType type, int x, int y)
        {
            return GameplayObjectManager.AddObject(Construct(type, x, y));
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
