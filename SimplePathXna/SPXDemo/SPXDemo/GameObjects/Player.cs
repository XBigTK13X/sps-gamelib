﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SPXDemo.Management;
using SPX.Collision;
using SPXDemo.Sprites;
using SPX.GameObjects;
using SPX.Management;
using SPXDemo.IO;

namespace SPXDemo.GameObjects
{
    class Player:GameplayObject
    {
        private int m_playerIndex;

        private float m_health = 100;
        private int m_moveCooldown = 0;
        private static int s_maxCooldown = 1;
        private int m_moveSpeed = 10;
        private Point2 m_velocity = new Point2(0, 0);

        private void Setup(int x, int y,int playerIndex)
        {
            Initialize(x, y,SpriteType.PLAYER,GameObjectType.PLAYER);
            m_playerIndex = playerIndex;
            m_isBlocking = true;
            AIManager.Add(this);
        }

        public Player(int x, int y,int playerIndex)
        {
            Setup(x, y,playerIndex);
        }
        public override void Update()
        {
            base.Update();
            Run();
            CheckForDamage();
        }
        public virtual void Run() 
        {
            m_velocity.SetX(((InputManager.IsPressed(Input.MOVE_LEFT, m_playerIndex)) ? -m_moveSpeed : 0)
                        +
                       ((InputManager.IsPressed(Input.MOVE_RIGHT, m_playerIndex)) ? m_moveSpeed : 0));
            m_velocity.SetY(((InputManager.IsPressed(Input.MOVE_DOWN, m_playerIndex)) ? m_moveSpeed : 0)
                        +
                       ((InputManager.IsPressed(Input.MOVE_UP, m_playerIndex)) ? -m_moveSpeed : 0));
            if (m_velocity.X != 0 || m_velocity.Y!= 0)
            {
                MoveIfPossible();
            }
        }

        private void CheckForDamage()
        {
            if (m_health <= 0)
            {
                m_isActive = false;
            }
        }

        protected void MoveIfPossible()
        {
            m_moveCooldown--;
            if ((m_velocity.X != 0 || m_velocity.Y != 0) && m_moveCooldown <= 0)
            {
                Move(m_velocity);
                m_moveCooldown = s_maxCooldown;
            }
            else
            {
                //This is how an animation can be changed
                //SetSpriteInfo(SpriteSheetManager.GetSpriteInfo(SpriteType.PLAYER_STAND));
            }
        }

        public Point2 GetVelocity()
        {
            return m_velocity;
        }
    }
}
