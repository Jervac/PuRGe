package bagel.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import bagel.core.BasicGame;
import bagel.core.Bagel;
import bagel.util.Rectf;

public class Tile {

    Sprite sprite;
    BasicGame game;
    Vector3 mousePos = new Vector3(0, 0, 0);
    ArrayList<String> tex;

	private float x, y;
	public float size;
    int id;
	
    public Tile(float x, float y, float size, int id, ArrayList<String> tex, BasicGame game) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.size = size;
        this.tex = tex;
        this.game = game;
        init();
	}

	void init() {
        String path = null;

        for (int i = 0; i < tex.size(); i++) {
            String a = tex.get(i);
            String[] split = a.split(":");
            int sid = Integer.parseInt(split[0]);
            String spath = split[1];

            if (sid == id) {
                path = spath;
                break;
            }
        }

        if (path != null) {
            sprite =  new Sprite(new Texture(Gdx.files.internal("tiles/" + path)));
            sprite.setPosition(x, y);
            sprite.setSize(size, size);
            sprite.flip(false, true);
        }
    }

	public void update() {

        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();
        game.cam_ortho.unproject(mousePos);

        if (game.editMode == 1 && game.editorMode && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)
                && mousePos.x > x && mousePos.x < x + bounds().width
                && mousePos.y > y && mousePos.y < y + bounds().height) {
            this.id = 0;
            init();
        }

        if (game.editMode == 1 && game.editorMode && Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                && mousePos.x > x && mousePos.x < x + size
                && mousePos.y > y && mousePos.y < y + size) {
            id = game.currentEditorId;
            init();
        }
	}

	public void render(SpriteBatch gl, ShapeRenderer sr, Rectf view) {
		if(bounds().collides(view)) {
			gl.begin();
			sprite.draw(gl);
			gl.end();

            if (Bagel.debugging) {
                sr.setColor(Color.YELLOW);
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.rect(bounds().x, bounds().y, bounds().width,
                        bounds().height);
                sr.end();
            }
		}
	}
	
	public void render(SpriteBatch gl, ShapeRenderer sr) {
			gl.begin();
			sprite.draw(gl);
			gl.end();

            if (Bagel.debugging) {
                sr.setColor(Color.YELLOW);
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.rect(bounds().x, bounds().y, bounds().width,
                        bounds().height);
                sr.end();
		}
	}

	public void dispose() {
		sprite.getTexture().dispose();
	}

	public Rectf bounds() {
		return new Rectf(x, y, size, size);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}