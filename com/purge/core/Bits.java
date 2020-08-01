package com.purge.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bagel.core.Bagel;
import bagel.entities.Entity;
import bagel.math.AABB;
import bagel.rendering.Graphics;

public class Bits extends Entity {
	int nothing = 0;
	
	public Bits(float y) {
		vel = 6;
		pos.set(0, y);
		size.set(Bagel.TILESIZE/2, Bagel.TILESIZE/3.8f);
	}
	
public Bits(float y, int nothing) {
		this.nothing = nothing;
		vel = 6;
		size.set(Bagel.TILESIZE/1.5f, Bagel.TILESIZE/1.5f);
		pos.set(0, y + size.y/2);
	}
		
	
	void update() {
		pos.x += vel;
		if(pos.x > Gdx.graphics.getWidth()) {
			dispose();
		}
		
		if(!Game.p.jumping && this.alive && AABB.collides(Game.p.getBounds(), this.getBounds())) {
			Game.p.getHit();
			dispose();
		}
	}
	
	@Override
	public void render(SpriteBatch g, ShapeRenderer sr) {
		if(alive) {
			update();
			if(nothing == 0)
				sr.setColor(new Color(0/255f, 136/255f, 255/255f, 1f));
			else
				sr.setColor(new Color(136/255f, 0, 0, 1));
			Graphics.drawRect(pos.x, pos.y, size.x, size.y, sr);
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
