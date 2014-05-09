package com.simplepathstudios.gamelib.util;

import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.states.GameSystem;

public class GenericFactory {
    public static <E> E newInstance(Class<E> type) {
        try {
            return type.newInstance();
        } catch (Exception e) {
            Logger.exception(e);
        }
        return null;
    }

    public static <E extends GameSystem> E newGameSystem(Class<E> type) {
        try {
            return type.newInstance();
        } catch (Exception e) {
            Logger.exception(e);
        }
        return null;
    }
}
