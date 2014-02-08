package sps.util;

import sps.core.Logger;
import sps.states.GameSystem;

public class GenericFactory {
    public static <E extends GameSystem> E newInstance(Class<E> type) {
        try {
            return type.newInstance();
        }
        catch (Exception e) {
            Logger.exception(e);
        }
        return null;
    }
}
