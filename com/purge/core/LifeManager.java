package com.purge.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bagel.entities.Entity;

public class LifeManager extends Entity {
	
	Sprite h1,h2,h3;
	
	public LifeManager() {
		h1 = new Sprite(new Texture("heart.png"));
		h1.setSize(32, 32);
		h1.setPosition(5 + h1.getWidth(), Gdx.graphics.getHeight() - h1.getHeight());
		
		h2 = new Sprite(h1.getTexture());
		h2.setSize(h1.getWidth(), h1.getHeight());
		h2.setPosition(5 + h1.getWidth() *2, h1.getY());
		
		h3 = new Sprite(h1.getTexture());
		h3.setSize(h1.getWidth(), h1.getHeight());
		h3.setPosition(5 + h1.getWidth() *3, h1.getY());
	}

	@Override
	public void render(SpriteBatch g, ShapeRenderer sr) {
		g.begin();
		if(Game.p.hp > 0)
			h1.draw(g);
		if(Game.p.hp > 1)
			h2.draw(g);
		if(Game.p.hp > 2)
			h3.draw(g);
		g.end();
	}

	@Override
	public void checkCollision(String dir) {
		
	}

	@Override
	public void dispose() {
		h1.getTexture().dispose();
		h2.getTexture().dispose();
		h3.getTexture().dispose();
	}

}
