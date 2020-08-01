package com.purge.core;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import bagel.core.Bagel;
import bagel.core.BasicGame;
import bagel.entities.Entity;
import bagel.entities.npc;
import bagel.math.AABB;
import bagel.rendering.Graphics;
import bagel.util.Inputer;
import bagel.util.Rectf;
import bagel.util.Shaker;
import bagel.util.world;

public class Hero extends Entity {
    world w;
    BasicGame g;
    Rectf talkBounds;
    public boolean frozen = false, jumping = false;
    public int hp = 3, maxHp = 3;
    Shaker shaker;
    Color oc;
    
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;
    Animation walkAnimation;
    Texture walkSheet;
    TextureRegion[] walkFrames;
    TextureRegion currentFrame;
    float stateTime;
    Sprite backwards, dead;
    
    private static final int FRAME_COLS2 = 4;
    private static final int FRAME_ROWS2 = 1;

    Animation walkAnimation2;
    Texture walkSheet2;
    TextureRegion[] walkFrames2;
    TextureRegion currentFrame2;

    float stateTime2;
    
    Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
    Sound hurt = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
    Sound heal = Gdx.audio.newSound(Gdx.files.internal("heal.wav"));
    Sound death = Gdx.audio.newSound(Gdx.files.internal("death.wav"));


    
    public Hero(BasicGame g, Vector2 pos, Vector2 size) {
    	this.g = g;
        this.pos = pos;
        this.size = size;
        talkBounds = new Rectf(0,0,0,0);
        vel = 3.6f;
        shaker = new Shaker(g.cam_ortho);
        
        dir.x = 1;
        
        walkSheet = new Texture(Gdx.files.internal("hero.png")); // #9
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
        
        walkSheet2= new Texture(Gdx.files.internal("hero_jump.png"));
        TextureRegion[][] tmp2 = TextureRegion.split(walkSheet2, walkSheet2.getWidth()/FRAME_COLS2, walkSheet2.getHeight()/FRAME_ROWS2);
        walkFrames2 = new TextureRegion[FRAME_COLS2 * FRAME_ROWS2];
        int index2 = 0;
        for (int i = 0; i < FRAME_ROWS2; i++) {
            for (int j = 0; j < FRAME_COLS2; j++) {
                walkFrames2[index2++] = tmp2[i][j];
            }
        }
        walkAnimation2 = new Animation(0.165f, walkFrames2);
        stateTime2 = 0f;      
        
        backwards = new Sprite(new Texture("hero_back.png"));
        backwards.setSize(size.x*2, size.y*2);
        
        dead = new Sprite(new Texture("hero_dead.png"));
        dead.setSize(size.x*2, size.y*2);
    }

    public void update() {    
    	
    	backwards.setPosition(pos.x-15, pos.y);
    	dead.setPosition(pos.x-15, pos.y);
    	if(alive && !Game.win) {
    		keyDir();
    		dir.nor();
    		pos.x += dir.x * vel;
    		checkCollision("lr");
    		pos.y += dir.y * vel;
    		
    		if(Inputer.tappedKey(Input.Keys.SPACE))
    			jump();
    		
    	} else {
    		if(pos.x < 300)
    			pos.x += 7;
    		if(pos.x > 310)
    			pos.x -= 7;
    		if(pos.x > 210)
    			pos.y -=7;
    		if(pos.y < 200)
    			pos.y += 7;
    	}
    }
    
    
    void jump() {
    	if(!jumping) {
    		jumping = true;
    		wavSound.play();
    		Timer t = new Timer();
    	    t.schedule(new TimerTask() {
    	        public void run() {
    	            jumping = false;
    	        }
    	    }, 700L);
    	}
    }
    
    //treat direction as unit vector
    void keyDir() {
        if(Inputer.pressedKey(Input.Keys.A) || Inputer.pressedKey(Input.Keys.LEFT))
            dir.x = -1;
        else if(Inputer.pressedKey(Input.Keys.D) || Inputer.pressedKey(Input.Keys.RIGHT))
            dir.x = 1;
        else
            dir.x = 0;

        if(Inputer.pressedKey(Input.Keys.W) || Inputer.pressedKey(Input.Keys.UP))
            dir.y = 1;
        else if(Inputer.pressedKey(Input.Keys.S) || Inputer.pressedKey(Input.Keys.DOWN))
            dir.y = -1;
        else
            dir.y = 0;
    }
    
    public void checkCollision(String face) {
    	if(pos.y + size.y>= Game.ground.getY() + Game.ground.getHeight())
    		pos.y -= vel;
    	if(pos.y <= 40)
    		pos.y += vel;
    	if(pos.x + size.x >= Gdx.graphics.getWidth())
    		pos.x -= vel;
    	if(pos.x <= Bagel.TILESIZE) {
    		pos.x += vel;
    	}
    	if(alive &&AABB.collides(Game.e.getBounds(), this.getBounds()))
    	{
    		alive = false;
			death.play();
    	}
    	for(Obstacle o: Game.obs) {
    		if(o.harmful && !invincible && !this.jumping && o.alive && AABB.collides(o.getBounds(), this.getBounds())) {
    			getHit();
    		}
    		
    		if(o.id == 1 && !o.harmful && !this.jumping && o.alive && AABB.collides(o.getBounds(), this.getBounds())) {
    			if(hp < 3)
    				hp++;
    			heal.play();
    			o.dispose();
    		}
    	}
    }
    
    void getHit() {
		invincible = true;
		hurt.play();

    	if(hp > 0) {
    		hp-=1;
    		if(hp == 0) {
    			alive = false;
    			shaker.shake(10.2f, 700f);
    			death.play();
    		}
    	}
    	Timer t = new Timer();
	    t.schedule(new TimerTask() {
	        public void run() {
	        	invincible = false;
	        }
	    }, 1000L);
    }

    public void render(SpriteBatch g, ShapeRenderer sr) {
    	if(invincible) {
   		 g.setColor(1, 0, 0, 0.4f);
   	 }    	
    	
    	if(alive) {
    	
    	 stateTime += Gdx.graphics.getDeltaTime(); 
         currentFrame = walkAnimation.getKeyFrame(stateTime, true);
         if(!jumping) {
         g.begin();
         if(dir.x < 0 || Game.win)
        	 backwards.draw(g);
         else
        	 g.draw(currentFrame, pos.x-15, pos.y, size.x *2, size.y*2);
         g.end();
         
         stateTime2 = 0f;
         } else {
        	 stateTime2 += Gdx.graphics.getDeltaTime();
        	 currentFrame2 = walkAnimation2.getKeyFrame(stateTime2, true);
        	 g.begin();
        	 g.draw(currentFrame2, pos.x - 15, pos.y, size.x*2, size.y*6f);
        	 g.end();
         }
    	}
         
         if(!alive)
         {
        	 g.begin();
        	 dead.draw(g);
        	 g.end();
         }
         
         g.setColor(Color.WHITE);
         
    	shaker.tick(Gdx.graphics.getDeltaTime());

    }

    public void dispose() {
    		backwards.getTexture().dispose();
    		wavSound.dispose();
    		heal.dispose();
    		hurt.dispose();
    		death.dispose();
    }
    
    public Rectf getBounds() {
    	return new Rectf(pos.x, pos.y, size.x, size.y/2);
    }
}