using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SPXDemo.GameObjects;
using SPXDemo.Management;
using SPX.GameObjects;
using SPX.Management;

namespace SPXDemo.Factory
{
    class GameplayObjectFactory
    {
        static private int s_playerCount = 0;
        static private GameplayObject Construct(string type, int x, int y)
        {
            switch (type)
            {
                case GameObjectType.PLAYER:
                    return new Player(x, y, s_playerCount++);
                case GameObjectType.WALL:
                    return new Wall(x, y);
                case GameObjectType.SPIKE:
                    return new Spike(x, y);
                default:
                    throw new Exception("An undefined string case was passed into the GameplayObjectFactory.");
            }
        }
        static public GameplayObject Create(string type, int x, int y)
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
