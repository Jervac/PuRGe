package com.purge.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import bagel.core.Bagel;
import bagel.core.BasicGame;
import bagel.entities.Entity;
import bagel.math.MathUtil;
import bagel.rendering.Graphics;

public class Obstacle extends Entity {

	BasicGame g;
	boolean harmful = true;

	Sprite sprite;
	
	int id = 69;

	public Obstacle(BasicGame g) {
		alive = true;

		vel = 7;

		int log = MathUtil.randInt(0, 10);
		if(Game.progress.distance < 30) {
		if (log >= 2)
			size = new Vector2(Bagel.TILESIZE, Bagel.TILESIZE);
		else
			size = new Vector2(Bagel.TILESIZE, Bagel.TILESIZE * 4);
		
		pos = new Vector2(MathUtil.randInt(Gdx.graphics.getWidth(),
				Gdx.graphics.getWidth() * 2), MathUtil.randInt(40,
				(int) (Game.ground.getY() + Game.ground.getHeight() - size.y)));

		if (log < 2)
			sprite = new Sprite(new Texture("log1.png"));
		else
			sprite = new Sprite(new Texture("rock.png"));
		} else {
			if (log >= 4)
				size = new Vector2(Bagel.TILESIZE, Bagel.TILESIZE);
			else
				size = new Vector2(Bagel.TILESIZE, Bagel.TILESIZE * 4);
			pos = new Vector2(MathUtil.randInt(Gdx.graphics.getWidth(),
					Gdx.graphics.getWidth() * 2), MathUtil.randInt(40,
					(int) (Game.ground.getY() + Game.ground.getHeight() - size.y)));

			if (log < 4)
				sprite = new Sprite(new Texture("log1.png"));
			else
				sprite = new Sprite(new Texture("rock.png"));
		}
		
		sprite.setPosition(pos.x, pos.y);
		sprite.setSize(size.x, size.y);
	}

	public Obstacle(BasicGame g, int id) {
		this.id = id;
		if (id == 0) {
			alive = true;
			vel = 7;
			harmful = false;
			size = new Vector2(Bagel.TILESIZE, Bagel.TILESIZE);
			pos = new Vector2(MathUtil.randInt(Gdx.graphics.getWidth(),
					Gdx.graphics.getWidth() * 2),
					MathUtil.randInt(40, (int) (Game.ground.getY()
							+ Game.ground.getHeight() - size.y)));
			sprite = new Sprite(new Texture("grass.png"));
			sprite.setPosition(pos.x, pos.y);
			sprite.setSize(size.x, size.y);
		} else if(id == 1) {
			alive = true;
			vel = 7;
			harmful = false;
			size = new Vector2(Bagel.TILESIZE, Bagel.TILESIZE);
			pos = new Vector2(MathUtil.randInt(Gdx.graphics.getWidth(),
					Gdx.graphics.getWidth() * 2),
					MathUtil.randInt(40, (int) (Game.ground.getY()
							+ Game.ground.getHeight() - size.y)));
			sprite = new Sprite(new Texture("heart.png"));
			sprite.setPosition(pos.x, pos.y);
			sprite.setSize(size.x, size.y);
			
		}

	}

	void repos() {
		pos = new Vector2(MathUtil.randInt(Gdx.graphics.getWidth(),
				Gdx.graphics.getWidth() * 2), MathUtil.randInt(
				Gdx.graphics.getHeight() / 4 + 10, Gdx.graphics.getHeight()
						- 80 - (int) size.y));
	}

	void update() {
		if(Game.progress.distance >= 20)
			vel = 8;
		if (alive) {
			for (int i = 0; i < Game.obs.size(); i++) {
				if ( Game.obs.get(i).alive
						&& Game.obs.get(i).harmful
						&& Game.obs.get(i).getBounds()
								.collides(this.getBounds())) {
					if (Game.obs.get(i) != this)
						dispose();
					// repos();
				}
			}
			for (int i = 0; i < Game.obs.size(); i++) {
				if (!this.harmful && Game.obs.get(i).alive
						&& !Game.obs.get(i).harmful
						&& Game.obs.get(i).getBounds()
								.collides(this.getBounds())) {
					if (Game.obs.get(i) != this)
						dispose();
					// repos();
				}
			}	
		}

		if (alive) {
			pos.x -= vel;

			if (pos.x + size.x <= 0)
				dispose();
		}
	}

	@Override
	public void render(SpriteBatch g, ShapeRenderer sr) {
		if (alive) {
			g.begin();
			sprite.setPosition(pos.x, pos.y);
			sprite.draw(g);
			g.end();
		}
	}

	@Override
	public void checkCollision(String dir) {

	}

	@Override
	public void dispose() {
		alive = false;
	}

}