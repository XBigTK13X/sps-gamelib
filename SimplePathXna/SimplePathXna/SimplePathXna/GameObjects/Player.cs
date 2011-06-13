﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SimplePathXna.Management;
using SimplePathXna.Collision;
using SimplePathXna.Sprites;

namespace SimplePathXna.GameObjects
{
    class Player:GameplayObject
    {
        private int m_playerIndex;

        private float m_health = 100;
        private int m_moveCooldown = COOLDOWN_TIME;

        private void Setup(int x, int y,int playerIndex)
        {
            Initialize(x, y,SpriteType.PLAYER_STAND,GameObjectType.PLAYER);
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
            int xVel = ((InputManager.IsPressed(InputManager.Commands.MoveLeft, m_playerIndex)) ? -10 : 0) + ((InputManager.IsPressed(InputManager.Commands.MoveRight, m_playerIndex)) ? 10 : 0);
            int yVel = ((InputManager.IsPressed(InputManager.Commands.MoveDown, m_playerIndex)) ? 10 : 0) + ((InputManager.IsPressed(InputManager.Commands.MoveUp, m_playerIndex)) ? -10 : 0);
            MoveIfPossible(xVel, yVel);
        }

        private void CheckForDamage()
        {
            if (m_health <= 0)
            {
                m_isActive = false;
            }
        }

        protected void MoveIfPossible(int xVel, int yVel)
        {
            m_moveCooldown--;
            if ((xVel != 0 || yVel != 0) && m_moveCooldown <= 0)
            {
                Move(xVel, yVel);
                m_moveCooldown = COOLDOWN_TIME;
            }
            else
            {
                //This is how an animation can be changed
                //SetSpriteInfo(SpriteSheetManager.GetSpriteInfo(SpriteType.PLAYER_STAND));
            }
        }
    }
}
