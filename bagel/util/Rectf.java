package bagel.util;

import bagel.math.AABB;

public class Rectf {

    public float x, y, z;
    public float width, height, depth;

    public Rectf(float x, float y, float z, float width, float height, float depth) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.width  = width;
        this.height = height;
        this.depth  = depth;
    }

    public Rectf(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;

        this.width  = width;
        this.height = height;
    }

    public boolean collides(Rectf b) {
        return AABB.collides(this, b);
    }
    
}