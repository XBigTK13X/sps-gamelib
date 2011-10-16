using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.GameObjects;
using SimplePathXna.Collision;
using SimplePathXna.Sprites;

namespace SimplePathXna.Management
{
    public static class GameplayObjectManager
    {
        private static List<GameplayObject> m_contents = new List<GameplayObject>();
        private static bool m_gameOver = false;

        public static GameplayObject AddObject(GameplayObject gameplayObject)
        {
            gameplayObject.LoadContent();
            m_contents.Add(gameplayObject);
            return gameplayObject;
        }

        //GOT Accessors
        public static GameplayObject GetObject(GameObjectType type)
        {
            if (m_contents != null)
            {
                return m_contents.FirstOrDefault(item => item.GetObjectType() == type);
            }
            return null;
        }

        public static IEnumerable<GameplayObject> GetObjects(GameObjectType type, Point2 target)
        {
            if (m_contents != null)
            {
                return GetObjects(type).Where(o => o.Contains(target));
            }
            return null;
        }

        public static IEnumerable<GameplayObject> GetObjects(GameObjectType type)
        {
            return m_contents.Where(item => item.GetObjectType() == type);
        }

        public static IEnumerable<GameplayObject> GetObjectsToCache()
        {
            return m_contents.Where(o => o.GetObjectType() != GameObjectType.FLOOR);
        }

        public static void AddObjects(IEnumerable<GameplayObject> cache)
        {
            m_contents.AddRange(cache);
        }

        public static void RemoveObject(GameplayObject target)
        {
            m_contents.Remove(target);
        }

        public static void Clear()
        {
            m_contents = new List<GameplayObject>();
        }

        public static void Reset()
        {
            m_contents = new List<GameplayObject>();
            LoadContent();
            Draw();
        }

        public static void Update()
        {
            for (var ii = 0; ii < m_contents.Count; ii++)
            {
                if (ii >= m_contents.Count)
                {
                    return;
                }
                if (!m_contents[ii].IsActive())
                {
                    m_contents.Remove(m_contents[ii]);
                    ii--;
                    continue;
                }
                m_contents[ii].Update();
            }
        }

        public static void Draw()
        {
            if (XnaManager.GetRenderTarget() != null)
            {
                foreach (var component in m_contents)
                {
                    component.Draw();
                }
            }
        }

        public static void LoadContent()
        {
            if (XnaManager.GetRenderTarget() != null)
            {
                foreach (var component in m_contents)
                {
                    component.LoadContent();
                }
            }
        }
    }
}
