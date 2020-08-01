package bagel.math;

import bagel.util.Rectf;
import bagel.util.Recti;

public class AABB {

    public static boolean collides(Recti a, Recti b) {
        int leftA, leftB;
        int rightA, rightB;
        int topA, topB;
        int bottomA, bottomB;

        leftA = a.getX();
        rightA = a.getX() + a.getWidth();
        topA = a.getY();
        bottomA = a.getY() + a.getHeight();

        leftB   = b.getX();
        rightB  = b.getX() + b.getWidth();
        topB    = b.getY();
        bottomB = b.getY() + b.getHeight();

        //check for collisoin
        if(bottomA <= topB) {
            return false;
        }

        if(topA >= bottomB) {
            return false;
        }

        if(rightA <= leftB) {
            return false;
        }

        return leftA < rightB;

    }


    public static boolean collides(Rectf a, Rectf b) {
        float leftA, leftB;
        float rightA, rightB;
        float topA, topB;
        float bottomA, bottomB;

        leftA = a.x;
        rightA = a.x + a.width;
        topA = a.y;
        bottomA = a.y + a.height;

        leftB   = b.x;
        rightB  = b.x + b.width;
        topB    = b.y;
        bottomB = b.y + b.height;

        //posotive width and height collision
        if(b.width > 0) {
            if (rightA <= leftB)
                return false;
            if (leftA >= rightB)
                return false;
        }

        if(b.height > 0) {
            if (bottomA <= topB)
                return false;
            if (topA >= bottomB)
                return false;
        }


        //negative width and height collision
        if(b.height < 0) {
            if (topA >= topB)
                return false;
            if(bottomA <= bottomB)
                return false;
        }

        if(b.width < 0) {
            if(rightA <= rightB)
                return false;
            if(leftA >= leftB)
                return false;
        }

        return true;
    }
}