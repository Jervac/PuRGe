package com.purge.core;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bagel.entities.Entity;
import bagel.rendering.Graphics;
import bagel.util.Rectf;

public class ProgressBar extends Entity {
	
	
	Rectf bar, dot;
	public int distance = 0;
	
	public ProgressBar() {
		bar = new Rectf(0, 0, 0, 0);
		
		bar.width = Gdx.graphics.getWidth()/1.5f;
		bar.height = 10;
		bar.x = Gdx.graphics.getWidth()/2 - bar.width/2;
		bar.y = 20;
		
		dot = new Rectf(0, 0, 0, 0);
		dot.width = 20;
		dot.height = 20;
		dot.x = bar.x + distance;
		dot.y = bar.y - bar.height/2;
		tick();
	}
	
	public void update() {
		if(Game.p.alive)
			dot.x = bar.x + distance *2;
		
	}

	@Override
	public void render(SpriteBatch g, ShapeRenderer sr) {
		sr.setColor(new Color(51/255f, 51/255f, 51/255f, 1));
		Graphics.drawRect(0, 0, Gdx.graphics.getWidth(), Game.ground.getY(),sr);
		
		sr.setColor(Color.GOLD);
		Graphics.drawRect(bar.x, bar.y, bar.width, bar.height, sr);
		
		sr.setColor(Color.WHITE);
		Graphics.drawRect(dot.x, dot.y, dot.width, dot.height, sr);
		
	}

	@Override
	public void checkCollision(String dir) {
		
	}

	@Override
	public void dispose() {
		
	}
	
	void tick() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				if(Game.p.alive)
					distance+=2;
				tick();
			}
		}, 800L);
	}

}
