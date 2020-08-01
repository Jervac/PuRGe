package com.purge.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bagel.core.Bagel;
import bagel.entities.Entity;

public class Mech extends Entity{
	
	Sprite sprite;
	
	public Mech() {
		vel = 7;
		sprite = new Sprite(new Texture("mech.png"));
		sprite.setSize(Bagel.TILESIZE, Bagel.TILESIZE);
		sprite.setPosition(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
		pos.set(sprite.getX(), sprite.getY());
	}

	@Override
	public void render(SpriteBatch g, ShapeRenderer sr) {
		if(Game.win) {
			sprite.setPosition(pos.x, pos.y);
			if(pos.x > Gdx.graphics.getWidth()/2)
				pos.x -= vel;
		g.begin();
		sprite.draw(g);
		g.end();
		}
	}

	@Override
	public void checkCollision(String dir) {
		
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

}
