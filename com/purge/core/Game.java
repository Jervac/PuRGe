package com.purge.core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import bagel.core.Bagel;
import bagel.core.BasicGame;
import bagel.math.MathUtil;
import bagel.rendering.Graphics;
import bagel.util.Inputer;

public class Game extends BasicGame {

	public static Hero p;

	public static ArrayList<Obstacle> obs;

	public static ProgressBar progress;

	int difficultyTime = 13; // 10 = easy 0 = hardest

	public static Sprite ground, sky;
	Texture skyt;

	boolean spawn = false;

	int sx = 0;

	LifeManager hearts;

	public static Commodore e;

	Music song;
	
	BitmapFont font;
	
	public static boolean win = false, fin = false;
	
	Mech m;
	
    Music winSound;


	@Override
	public void init() {

		Bagel.TILESIZE = 64;
		cam_ortho.setToOrtho(false);

		p = new Hero(this, new Vector2(300, Gdx.graphics.getHeight() / 2),
				new Vector2(Bagel.TILESIZE / 1.5f, Bagel.TILESIZE / 1.5f));

		ground = new Sprite(new Texture("ground.png"));
		ground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 1.5f);
		ground.setPosition(0, 40);

		sky = new Sprite(new Texture("sky.png"));
		sky.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 4);
		sky.setPosition(0, 0);
		skyt = sky.getTexture();
		skyt.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		progress = new ProgressBar();
		obs = new ArrayList<Obstacle>();

		hearts = new LifeManager();

		e = new Commodore();

		song = Gdx.audio.newMusic(Gdx.files.internal("Insane Machine.mp3"));
		song.play();
		song.setPosition(1);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SourceSansPro-Regular.otf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (40 * Gdx.graphics.getDensity());
        font = generator.generateFont(parameter);
        
        m = new Mech();
        
        winSound =  Gdx.audio.newMusic(Gdx.files.internal("win.wav"));

		spawn(this);
	}

	void update() {
		
		p.update();

		if (p.alive && !win) {
			if (!song.isPlaying())
				song.play();
			progress.update();
			sx += 1;

			for (int i = 0; i < obs.size(); i++) {
				obs.get(i).update();
			}

		} else if(!p.alive || win){
			song.stop();
			if (Inputer.tappedKey(Input.Keys.P)) {
				restart();
			}
		}
		
		if(progress.distance >= 255)
			win = true;

		if (Inputer.tappedKey(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}

	@Override
	public void render() {
		update();
		
		if(win && e.alive && progress.distance >= 255)
			Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				e.die();
				fin = true;
			}
		}, 3);
				
		
		
		if(win && !winSound.isPlaying())
			winSound.play();
		
		Graphics.clearScreen();
		Graphics.clearColor(51, 51, 51);
		g.setProjectionMatrix(cam_ortho.combined);
		sr.setProjectionMatrix(cam_ortho.combined);
		cam_ortho.update();
		if (p.alive) {
			g.begin();
			g.draw(skyt, 0,
					Gdx.graphics.getHeight()
							- (Gdx.graphics.getHeight() / 3.2f), sx, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
			ground.draw(g);
			g.end();
			
			
			if(!win) {
				if (spawn)
					obs.add(new Obstacle(this));

				for (int i = 0; i < obs.size(); i++) {
					obs.get(i).render(g, sr);
				}
			}
		}
		
		if(!p.alive) {
			float w = 300, h = 100;
			sr.setColor(Color.WHITE);
			Graphics.drawRect(p.dead.getX() - w/2.5f, p.backwards.getY()- h/2, w, h, sr);
			
			g.begin();
			font.draw(g,"You Traveled " + progress.distance + " Sudo Meters\nPress P to play again", 
					Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight() - 50);
			g.end();
		}

		p.render(g, sr);
		if (p.alive) {
				e.render(g, sr);

			progress.render(g, sr);
			
			hearts.render(g, sr);
		}
		
		m.render(g, sr);
		
		if(fin) {
			g.begin();
			font.setColor(Color.BLACK);
			font.draw(g, "Game Over. You Destroyed all Ancient Tech!\nPress P to play again", 50, 550);
			g.end();
		}
		
		
	}

	@Override
	public void dispose() {
		for (Obstacle o : obs)
			o.dispose();
		p.dispose();
		progress.dispose();
		hearts.dispose();
		e.dispose();
		song.dispose();
		m.dispose();
	}

	void spawn(final BasicGame g) {
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				if (progress.distance < 30) {
					int healer = MathUtil.randInt(1, 10);
					if (healer <= 3)// 30 % chance of potion
						obs.add(new Obstacle(g, 1));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g));
					obs.add(new Obstacle(g));
				} else {
					int healer = MathUtil.randInt(1, 10);
					if (healer <= 2)// 20 % chance of potion
						obs.add(new Obstacle(g, 1));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g, 0));
					obs.add(new Obstacle(g));
					obs.add(new Obstacle(g));
					obs.add(new Obstacle(g));
				}
				spawn(g);
			}
		}, 1);
	}

	void restart() {
		for (Obstacle o : obs)
			o.dispose();
		obs.clear();
		p.pos.set(300, Gdx.graphics.getHeight() / 2 - p.size.y / 2);
		p.hp = p.maxHp;
		p.alive = true;
		progress.distance = 0;
		p.invincible = false;
		win = false;
		song.play();
		song.setPosition(1);
		fin = false;
		e.dispose();
		e = new Commodore();
		e.pos.set(10, Gdx.graphics.getHeight()/2);
		e.alive = true;
	}

	@Override
	public void rsize(int w, int h) {

	}
}
