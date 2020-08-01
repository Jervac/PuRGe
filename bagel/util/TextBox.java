package bagel.util;

import bagel.rendering.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class TextBox {

	public Sprite sprite;
	public Vector2 pos, size;
	public String text;
	public boolean visible = false;
	
	BitmapFont font;

	public TextBox(Sprite sprite, Vector2 size) {
		this.sprite = sprite;
		this.size = size;
		if(sprite != null) {
			sprite.setSize(size.x, size.y);

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size.x / 2, 0);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SourceSansPro-Regular.otf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (40 * Gdx.graphics.getDensity());
        font = generator.generateFont(parameter);
		}
	}

	public void update() {

	}

	public void render(SpriteBatch g, ShapeRenderer sr) {
		if (visible) {
			if (sprite != null) {
				g.begin();
				sprite.setPosition(pos.x, pos.y);
				sprite.draw(g);
				font.draw(g, " " +text, pos.x, pos.y + size.y - font.getLineHeight());
				g.end();
			} else {
				sr.setColor(Color.BROWN);
				Graphics.drawRect(pos.x, pos.y, size.x, size.y, sr);
			}
		}
	}

	public void dispose() {
		if(sprite != null)
			sprite.getTexture().dispose();
	}

	public void setVisible(boolean vis) {
		this.visible = vis;
	}

	public void showText(String text) {
		this.text = text;
		visible = true;
	}

}