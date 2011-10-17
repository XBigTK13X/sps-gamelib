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
        private static readonly List<string> m_playerInputDevices = new List<string>()
        {
            "KEYBOARD",
            "GAMEPAD"
        };
        private static readonly Dictionary<string, Keys> m_keyboardMapping = new Dictionary<string, Keys>();

        private static readonly Dictionary<string, Buttons> m_gamePadMapping = new Dictionary<string, Buttons>();

        private static readonly List<PlayerIndex> m_playerIndex = new List<PlayerIndex>()
        {
            PlayerIndex.One,
            PlayerIndex.Two,
            PlayerIndex.Three,
            PlayerIndex.Four
        };

        public static bool IsPressed(string command,int playerIndex)
        {
            string inputMechanism = m_playerInputDevices[playerIndex];
            bool isInputActive = false;
            switch (inputMechanism)
            {
                case "KEYBOARD":
                    isInputActive = Keyboard.GetState().IsKeyDown(m_keyboardMapping[command]);
                    break;
                case "GAMEPAD":
                    isInputActive = GamePad.GetState(m_playerIndex[playerIndex]).IsButtonDown(m_gamePadMapping[command]);
                    break;
                default:
                    throw new Exception("What were you smoking that brought up this error?");
            }
            return isInputActive;
        }

        public static void Add(string command, Keys keyboardKey, Buttons gamepadButton)
        {
            m_gamePadMapping.Add(command, gamepadButton);
            m_keyboardMapping.Add(command, keyboardKey);
        }
    }
}
