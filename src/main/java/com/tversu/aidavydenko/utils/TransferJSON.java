package com.tversu.aidavydenko.utils;

public class TransferJSON {
    private Point point;
    private int P;

    public TransferJSON(Point point, int p) {
        this.point = point;
        P = p;
    }

    public TransferJSON(int x, int y, int p) {
        this.point = new Point(x, y);
        P = p;
    }

    public Point getPoint() {
        return new Point(point);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getP() {
        return P;
    }

    public void setP(int p) {
        P = p;
    }
}
