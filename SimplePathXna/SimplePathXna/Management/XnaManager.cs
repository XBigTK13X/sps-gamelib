using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;

namespace SimplePathXna.Management
{
    public static class XnaManager
    {
        static public readonly int WindowHeight = 600;
        static public readonly int WindowWidth = 600;
        static private ContentManager s_assetHandler;
        static private SpriteBatch s_renderTarget;
        static public void SetContentManager(ContentManager assetHandler)
        {
            s_assetHandler = assetHandler;
        }
        static public void SetRenderTarget(SpriteBatch renderTarget)
        {
            s_renderTarget = renderTarget;
        }
        static public ContentManager GetContentManager()
        {
            return s_assetHandler;
        }
        static public SpriteBatch GetRenderTarget()
        {
            return s_renderTarget;
        }
    }
}
