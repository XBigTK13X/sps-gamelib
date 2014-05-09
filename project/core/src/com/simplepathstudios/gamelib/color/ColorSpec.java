package com.simplepathstudios.gamelib.color;

public interface ColorSpec<E extends ColorSpec> {
    public Color toColor();

    public ColorSpec average(E target);

    ColorSpec lerp(float startPercent, E target);
}
