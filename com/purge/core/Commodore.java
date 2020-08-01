package com.purge.core;

import java.util.ArrayList;

import sun.net.www.content.audio.wav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

import bagel.core.Bagel;
import bagel.entities.Entity;
import bagel.math.MathUtil;
import bagel.rendering.Graphics;

public class Commodore extends Entity {
	
	ArrayList<Bits> bits;
	ArrayList<beam> beams;
	
	float delay = 2;
	
    Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
    Sound fire = Gdx.audio.newSound(Gdx.files.internal("fire.wav"));
    
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;

    Animation walkAnimation;
    Texture walkSheet;
    TextureRegion[] walkFrames;
	TextureRegion currentFrame;

    float stateTime;
    
    
    int attackMode = 0;
    
    Sprite ded;
    
    public void die() {
    	alive = false;
    }
	
	public Commodore() {
		bits = new ArrayList<Bits>();
		beams = new ArrayList<beam>();
		size.set(Bagel.TILESIZE, Bagel.TILESIZE);
		pos.set(10, Gdx.graphics.getHeight()/2);
		vel = 1;
		attackMode = 0;
		
		walkSheet = new Texture(Gdx.files.internal("commodore.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.165f, walkFrames);
        stateTime = 0f;          
        
        ded = new Sprite(new Texture("ded.png"));
        ded.setSize(size.x, size.y);
		
		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				attack();
			}
		}, 2);
	}
	
	void attack() {
		if(Game.win == false) {
			if(attackMode == 0 && Game.p.alive) {
				int a = MathUtil.randInt(1, 10);
				if(a > 3) {
					bits.add(new Bits(pos.y + size.y/1.5f + 24));
					wavSound.play();
				}else {
					bits.add(new Bits(pos.y, 1));
					fire.play();
				}
			}
			
			if(attackMode == 1 && Game.p.alive) {
				beams.add(new beam(pos.y  + size.y/1.5f + 20f ));
				delay = 3;
			}
			
			if(attackMode == 3 && Game.p.alive) {
				int b = MathUtil.randInt(1, 10);
				if(b == 1) {
					beams.add(new beam(pos.y  + size.y/1.5f + 20f ));
					delay = 3;
				} else {
					delay = 1;
					int a = MathUtil.randInt(1, 10);
					if(a > 3) {
						bits.add(new Bits(pos.y + size.y/1.5f + 24));
						wavSound.play();
					}else {
						bits.add(new Bits(pos.y, 1));
						fire.play();
					}
				}
			}
			
			
		Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					attack();
				}
			}, delay);
		}
	}

	
	void update() {
		if(Game.win == false) {
		if(Game.progress.distance > 30)
			delay = 2;
		dir.set(0, Game.p.pos.y - this.pos.y -60);
		dir.nor();
		
		pos.y += Math.ceil((dir.y * vel));
		
		if(Game.progress.distance < 10)
			attackMode = 0;
		if(Game.progress.distance >= 10 && Game.progress.distance <= 14)
			attackMode = 1;
		if(Game.progress.distance > 14 && Game.progress.distance < 25) {
			attackMode = 0;
			delay = 1;
		}
		
		if(Game.progress.distance > 25) {
			attackMode = 3;
		}
//		if(Game.pro)
		} else {
			if(pos.x < 150)
				pos.x += 7;
		}
		
	}
	
	@Override
	public void render(SpriteBatch g, ShapeRenderer sr) {
		update();
		
		stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        
        if(alive) {
        g.begin();
   	 	g.draw(currentFrame, pos.x-15, pos.y, size.x *2, size.y*2.5f);
        g.end();
        }
        
        if(!alive) {
        	ded.setPosition(pos.x, pos.y);
        	ded.setSize(size.x *2, size.y*2.5f);
        	g.begin();
        	ded.draw(g);
        	g.end();
        }
		
		for(Bits b: bits)
			b.render(g, sr);
		for(beam b: beams)
			b.render(g, sr);
		for(beam b: beams)
			b.pos.y = pos.y + size.y/1.5f + 24;
		
	}

	@Override
	public void checkCollision(String dir) {		
	}
	
	public void clean() {
		for(Bits b: bits)
			b.dispose();
	}

	@Override
	public void dispose() {
		for(Bits b: bits)
			b.dispose();
		wavSound.dispose();
		fire.dispose();
		for(beam b: beams)
			b.dispose();
		fire.dispose();
	}

}
