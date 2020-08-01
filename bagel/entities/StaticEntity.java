package bagel.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import bagel.core.BasicGame;
import bagel.core.Bagel;
import bagel.math.Vec2f;
import bagel.util.Rectf;

public class StaticEntity {

	private float x, y;
	public Vec2f size, bsize;
	public int id;

	Sprite sprite;
	ArrayList<String> tex;
	public String path;
	float tsize;

	BasicGame game;

    Vector3 mousePos = new Vector3(0, 0, 0);

	public StaticEntity(BasicGame game, float x, float y, int id, ArrayList<String> tex, float tsize) {
		this.game = game;
		this.id = id;
		this.x = x;
		this.y = y;
		this.tex = tex;
		this.tsize = tsize;

		init();

	}

	private void init() {
        for (int i = 0; i < tex.size(); i++) {
			String a = tex.get(i);
			String[] split = a.split(":");

			// id:path
			int sid = Integer.parseInt(split[0]);
			String spath = split[1];

			// size,size
			String sizes = split[2];
			String[] ssplit = sizes.split(",");
			int sizew = Integer.parseInt(ssplit[0]);
			int sizeh = Integer.parseInt(ssplit[1]);
			size = new Vec2f(sizew * tsize, sizeh * tsize);

			// bounds
			String bsizes = split[3];
			String[] sbsizes = bsizes.split(",");
			int sizewb = Integer.parseInt(sbsizes[0]);
			int sizehb = Integer.parseInt(sbsizes[1]);
			bsize = new Vec2f(sizewb, sizehb);

			if (sid == id) {
				path = spath;
				break;
			}
		}

		if (path != null) {
			sprite = new Sprite(new Texture(Gdx.files.internal("tiles/"+path)));
			sprite.setPosition(x, y);
			sprite.setSize(size.x, size.y);
			sprite.flip(false, true);
			if (size.y > Bagel.TILESIZE) {
				sprite.setPosition(sprite.getX(), sprite.getY() -  Bagel.TILESIZE * bsize.y);
			}
		} else {
			System.out.println("unknow path: " + path);
		}
	}

	public void update() {
	    mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();

        game.cam_ortho.unproject(mousePos);

        if (game.editMode == 0 && game.editorMode && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)
				&& mousePos.x > x && mousePos.x < x + bounds().width
				&& mousePos.y > y && mousePos.y < y + bounds().height) {
			this.id = 0;
		}

		if (game.editMode == 0 && game.editorMode && Gdx.input.isButtonPressed(Input.Buttons.LEFT)
				&& mousePos.x > x && mousePos.x < x + bounds().width
				&& mousePos.y > y && mousePos.y < y + bounds().height) {
		    id = game.currentEditorId;
				init();
		}
	}

	public void render(SpriteBatch gl, ShapeRenderer sr) {
		if (id != 0 && id != 3) {
			if (bounds().collides(game.view)) {
				if (Bagel.debugging) {
					sr.setColor(Color.YELLOW);
					sr.begin(ShapeType.Line);
					sr.rect(bounds().x, bounds().y, bounds().width,
							bounds().height);
					sr.end();
				}

				gl.begin();
				sprite.draw(gl);
				gl.end();
			}

		}
	}

	public void dispose() {
		sprite.getTexture().dispose();
	}

	public Rectf bounds() {
		return new Rectf(x, y, bsize.x * Bagel.TILESIZE, bsize.y * Bagel.TILESIZE);
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