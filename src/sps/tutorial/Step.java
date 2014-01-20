package sps.tutorial;

import sps.core.Point2;

public class Step {

    private Point2 _arrowLocation;
    private String _message;

    public Step(int arrowX, int arrowY, String message) {
        _arrowLocation = new Point2(arrowX, arrowY);
        _message = message;
    }

    public Point2 getArrowLocation() {
        return _arrowLocation;
    }

    public String getMessage() {
        return _message;
    }
}
