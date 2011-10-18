using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace SPX.Management
{
    public class InputManager
    {
        private enum Device
        {
            KEYBOARD,
            GAMEPAD
        }
        private static readonly List<Enum> m_playerInputDevices = new List<Enum>()
        {
            Device.KEYBOARD,
            Device.GAMEPAD
        };
        private static readonly Dictionary<Enum, Keys> m_keyboardMapping = new Dictionary<Enum, Keys>();

        private static readonly Dictionary<Enum, Buttons> m_gamePadMapping = new Dictionary<Enum, Buttons>();

        private static readonly List<PlayerIndex> m_playerIndex = new List<PlayerIndex>()
        {
            PlayerIndex.One,
            PlayerIndex.Two,
            PlayerIndex.Three,
            PlayerIndex.Four
        };

        public static bool IsPressed(Enum command,int playerIndex)
        {
            Device inputMechanism = (Device)m_playerInputDevices[playerIndex];
            bool isInputActive = false;
            switch (inputMechanism)
            {
                case Device.KEYBOARD:
                    isInputActive = Keyboard.GetState().IsKeyDown(m_keyboardMapping[command]);
                    break;
                case Device.GAMEPAD:
                    isInputActive = GamePad.GetState(m_playerIndex[playerIndex]).IsButtonDown(m_gamePadMapping[command]);
                    break;
                default:
                    throw new Exception("What were you smoking that brought up this error?");
            }
            return isInputActive;
        }

        public static void Add(Enum command, Keys keyboardKey, Buttons gamepadButton)
        {
            m_gamePadMapping.Add(command, gamepadButton);
            m_keyboardMapping.Add(command, keyboardKey);
        }
    }
}
