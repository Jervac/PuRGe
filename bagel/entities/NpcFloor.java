package bagel.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bagel.core.BasicGame;

public class NpcFloor {
	
	public ArrayList<npc> npcs = new ArrayList<npc>();
	public int id;
	BasicGame g;
	float size;
	
	public NpcFloor(int id, String path, BasicGame g) {
		this.id = id;
		this.g = g;
		
		init();
	}	
	
	void init() {
		
	}
	
	public void update() {
		for(npc n: npcs)
			n.update();
	}
	
	public void render(SpriteBatch g, ShapeRenderer s) {
		for(npc n: npcs)
			n.render(g,  s);
	}
	
	public void dispose() {
		for(npc n: npcs)
			n.dispose();
	}
	
}