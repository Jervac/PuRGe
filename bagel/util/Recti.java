package bagel.util;

import bagel.math.AABB;

public class Recti {

    private int x, y, z;
    private int width, height, depth;

    public Recti(int x, int y, int z, int width, int height, int depth) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.width  = width;
        this.height = height;
        this.depth  = depth;
    }

    public Recti(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

        this.width  = width;
        this.height = height;
    }

    public boolean collides(Recti a) {
        return AABB.collides(this, a);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}