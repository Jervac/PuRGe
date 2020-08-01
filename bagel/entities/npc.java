package bagel.entities;

import java.util.ArrayList;

import bagel.core.Bagel;
import bagel.core.BasicGame;
import bagel.rendering.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class npc extends Entity implements Runnable { //TODO: do i need the runnable thing?
	
	public String name = "????";
    public ArrayList<String> actions = new ArrayList<String>();
    public Thread actionThread;
    
    public boolean talking = false;
	
    public npc(BasicGame g) {this.g =g;}
    public void update() {
    }
    public void render(SpriteBatch g, ShapeRenderer s) {
    	s.setColor(Color.YELLOW);
		Graphics.drawRect(pos.x, pos.y, size.x, size.y, s);
    }
    public void dispose()  {}
    
    public void setName(String name) {this.name = name;}
    
    public void say( final String txt ) {
    	System.out.println(name + ": " + txt);
    	g.textBox.showText(txt);
    }
    
    public void setPos(Vector2 p) {
    	pos.set(p);
    }
    
    public void setSize(Vector2 s) {
    	size.set(s);
    }
	@Override
	public void checkCollision(String dir) {
		
	}
	
	public void run() {
		perform(0);
	}
	
	 public void perform(int cur) {
	        //	Seperate Actions //
	        if (actions.get(cur).startsWith("say:")) {
	            String[] splity = actions.get(cur).split(":");
	            say(splity[1]);
	            //making thread sleeps prevents input from going to fast/skipping
	            try {
	                Thread.sleep(10);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            do {
	                try {
	                    Thread.sleep(10);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            } while (!Gdx.input.isKeyJustPressed(Input.Keys.U));
	            try {
	                Thread.sleep(10);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        } else {
	        	g.textBox.setVisible(false);
	        }
	        
	        if (actions.get(cur).startsWith("move:")) {
	            String[] splity = actions.get(cur).split(":");
	            String[] moves = splity[1].split(",");
	            float endx = pos.x + (Float.parseFloat(moves[0]) * Bagel.TILESIZE);
	            float endy = pos.y + (Float.parseFloat(moves[1]) * Bagel.TILESIZE);

	            do {
	                if(pos.x < endx)
	                    pos.x += vel;
	                if(pos.y < endy)
	                    pos.y += vel;
	                if(pos.x > endx)
	                    pos.x -= vel;
	                if(pos.y > endy)
	                    pos.y -= vel;
	                try {
	                    Thread.sleep(10);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            } while ((Math.floor(pos.x) != endx || Math.floor(pos.y) != endy) );
	        }

	        if(actions.get(cur).startsWith("die")) {
	            alive = false;
	        }
			
	        if (actions.size() - 1 > cur)
	            perform(cur + 1);
	        else {
	        	g.textBox.setVisible(false);
	        	talking = false;
	            actionThread.interrupt();
	        }

	    }

}
