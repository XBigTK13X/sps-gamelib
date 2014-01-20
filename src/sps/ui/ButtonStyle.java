package sps.ui;

import sps.display.Screen;

public class ButtonStyle {
    private int _x;
    private int _y;
    private int _width;
    private int _height;
    private int _margins;

    public ButtonStyle(int percentX, int percentY, int widthPercentX, int heightPercentY, int margins) {
        _x = (int) Screen.width(percentX);
        _y = (int) Screen.height(percentY);
        _width = (int) Screen.width(widthPercentX);
        _height = (int) Screen.height(heightPercentY);
        _margins = margins;
    }

    public void apply(UIButton button, int horizontalOrder, int verticalOrder) {
        button.setXY((_width * horizontalOrder + _x) + (_margins * horizontalOrder), (_height * verticalOrder + _y) + (_margins * verticalOrder));
        button.setPixelSize(_width, _height);
        button.layout();
    }
}
