package com.purge.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

import bagel.entities.Entity;
import bagel.math.AABB;
import bagel.rendering.Graphics;

public class beam extends Entity {
	
	boolean active = false;
	
    Sound charge = Gdx.audio.newSound(Gdx.files.internal("charge.wav"));
    Sound shot = Gdx.audio.newSound(Gdx.files.internal("lazer_shot.wav"));

	
	public beam(float y) {
		
		pos.set(30, y);
		size.set(Gdx.graphics.getWidth(), 10);
		charge.play();
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				active = true;
				if(Game.progress.distance > 10)
					shot.play();
				Timer.schedule(new Timer.Task() {
					
					@Override
					public void run() {
						dispose();
					}
				}, 0.5f);
			}
		}, 1.5f);
	}

	@Override
	public void render(SpriteBatch g, ShapeRenderer sr) {
		if(alive) {
			if(active) sr.setColor(Color.RED);
			else sr.setColor(Color.WHITE);
			Graphics.drawRect(pos.x, pos.y, size.x, size.y, sr);
			checkCollision("test");
		}
	}

	@Override
	public void checkCollision(String dir) {
		if(!Game.p.invincible && !Game.p.jumping &&alive && active && AABB.collides(Game.p.getBounds(), this.getBounds())) {
			Game.p.getHit();
			dispose();
		}
	}

	@Override
	public void dispose() {
		alive = false;
		shot.dispose();
		charge.dispose();
	}

}