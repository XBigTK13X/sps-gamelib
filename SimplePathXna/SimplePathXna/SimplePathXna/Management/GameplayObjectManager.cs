using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.GameObjects;
using SimplePathXna.Sprites;

namespace SimplePathXna.Management
{
    static class GameplayObjectManager
    {
        static private List<GameplayObject> m_contents = new List<GameplayObject>();
        static public void AddObject(GameplayObject gameplayObject)
        {
            m_contents.Add(gameplayObject);
        }
        static public GameplayObject GetObject(SpriteType type)
        {
            if (m_contents != null)
            {
                foreach (GameplayObject item in m_contents)
                {
                    if (item.GetAssetType() == type)
                    {
                        return item;
                    }
                }
            }
            return null;
        }
        static public void Clear()
        {
            m_contents.Clear();
        }
    }
}
