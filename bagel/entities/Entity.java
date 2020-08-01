package bagel.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import bagel.core.BasicGame;
import bagel.util.Rectf;

public abstract class Entity {
	
	
	public BasicGame g;
	
    public boolean invincible;
    public boolean frozen;
    public boolean alive = true;
    public boolean visible = true;

    public float vel, max_vel;

    public int layer = 0;

    public Vector2 dir  = new Vector2(0, 0);
    public Vector2 pos  = new Vector2(0, 0);
    public Vector2 size = new Vector2(0, 0);

    public abstract void render(SpriteBatch g, ShapeRenderer sr);
    public abstract void checkCollision(String dir);
    public abstract void dispose();

    public Rectf getBounds() {
        return new Rectf(pos.x, pos.y, size.x, size.y);
    }

    public Rectf getBounds(float x, float y, float w, float h) {
        return new Rectf(x, y, w, h);
    }

}
