using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using SPX.Collision;
using SPX.Sprites;
using SPX.Management;

namespace SPX.GameObjects
{
    public class GameplayObject
    {
        protected AnimatedTexture m_graphic = new AnimatedTexture();

        protected bool m_isActive = true;
        protected bool m_isBlocking = false;
        protected Enum m_assetName;
        protected Enum m_objectType;
        protected bool m_isOnBoard = true;
        private bool m_isInteracting = false;
        protected Point2 m_location;

        //Load the texture for the sprite using the Content Pipeline
        public void LoadContent()
        {
            m_graphic.LoadContent(m_assetName);
        }

        //Draw the sprite to the screen
        public virtual void Draw()
        {
            if (m_isOnBoard)
            {
                m_graphic.Draw();
            }
        }

        public void Hide()
        {
            m_isOnBoard = false;
        }

        public void Show()
        {
            m_isOnBoard = true;
        }

        protected void Initialize(Point2 location, Enum spriteType, Enum objectType)
        {
            m_assetName = spriteType;
            m_objectType = objectType;
            m_location = new Point2(location);
            m_graphic.SetPosition(m_location);
        }

        protected void Initialize(float x, float y, Enum spriteType, Enum objectType)
        {
            Initialize(new Point2(x, y), spriteType, objectType);
        }

        public virtual void Update()
        {
        }

        public void SetLocation(Point2 location)
        {
            m_graphic.SetPosition(location);
            m_location = new Point2(location);
        }

        public void UpdateLocation(Point2 location)
        {
            m_graphic.SetPosition(location);
            m_location = new Point2(location);
        }

        public void Move(Point2 velocity)
        {
            var target = new Point2(m_location.PosX + velocity.X, m_location.PosY + velocity.Y);
            if (CoordVerifier.IsValid(target))
            {
                UpdateLocation(target);
            }
        }

        public bool IsActive()
        {
            return m_isActive;
        }

        public void SetInactive()
        {
            m_isActive = false;
        }

        public bool IsBlocking()
        {
            return m_isBlocking;
        }

        public Enum GetAssetType()
        {
            return m_assetName;
        }

        public Enum GetObjectType()
        {
            return m_objectType;
        }

        public Point2 GetLocation()
        {
            return m_location;
        }

        public bool IsGraphicLoaded()
        {
            return (m_graphic != null);
        }

        protected void SetSpriteInfo(SpriteInfo sprite)
        {
            m_graphic.SetSpriteInfo(sprite);
        }

        public bool Contains(Point2 target)
        {
            return target.GridX == GetLocation().GridX && target.GridY == GetLocation().GridY;
        }

        public void SetInteraction(bool isInteracting)
        {
            m_isInteracting = isInteracting;
        }

        public bool IsInteracting()
        {
            return m_isInteracting;
        }
    }
}