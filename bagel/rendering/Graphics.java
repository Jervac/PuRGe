package bagel.rendering;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import bagel.core.Bagel;
import bagel.util.Logger;

public class Graphics {

    public static void scaleSprite(float w, float h, Sprite s) {
        float nsize_w = (float)Math.floor(w);
        float nsize_h = (float)Math.floor(w);
        float nw = 0, nh = 0;

        if(nsize_w % 2 == 0) {
            nw = nsize_w;
        } else if((nsize_w + 1) % 2 == 0) {
            nw = nsize_w + 1;
        } else {
            Logger.error(Bagel.class + ": couldn't scale " + s + " to " + nsize_w + " or " + nsize_w + 1, true);
        }

        if(nsize_h % 2 == 0) {
            nh = nsize_h;
        } else if((nsize_h + 1) % 2 == 0) {
            nh = nsize_h + 1;
        } else {
            Logger.error(Bagel.class + ": couldn't scale " + s + " to " + nsize_h + " or " + nsize_h + 1, true);
        }

        s.setSize(nw, nh);
    }

    public static void drawRect(float x, float y, float w, float h, ShapeRenderer s) {
        s.begin(ShapeRenderer.ShapeType.Filled);
        s.rect(x, y, w, h);
        s.end();
    }

    public static void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void clearColor(int r, int g, int b) {
        Gdx.gl.glClearColor(r / 255f, g / 255f, b / 255f, 1);
    }

    public static void clearColor(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b, 1);
    }

    public static void clearColor(Color c) {
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
    }

    public static Color toColor(int r, int g, int b, int a) {
        return new Color(r / 255f, g / 255f, b / 255f, a);
    }

    public static Color toColor(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }
}
