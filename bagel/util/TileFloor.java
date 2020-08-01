package bagel.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bagel.core.BasicGame;
import bagel.entities.Tile;
import bagel.math.AABB;

public class TileFloor {

    public int id;
    public float size;
    public Tile[][] map;
    public ArrayList<Tile> tiles = new ArrayList<Tile>();

    public TileFloor(int id, float size, FileHandle fileHandle, BasicGame game) {
        this.size = size;
        this.id = id;
        Logger.info("hai");
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileHandle + ""));
            if(!fileHandle.exists())
                Logger.error("Couldn't load map: " + fileHandle.toString(), true);
            int w = Integer.parseInt(br.readLine());
            int h = Integer.parseInt(br.readLine());
            int[][] map = new int[w][h];
            
            for (int row = 0; row < w; row++) {
                String line = br.readLine();
                if (line == null || line.isEmpty()) {
                    System.out.println("Line is empty or null");
                } else {
                    String[] tileValues = line.split("-");
                    for (int col = 0; col < h; col++) {
                        map[row][col] = Integer.parseInt(tileValues[col]);
                    }
                }
            }
            
            ArrayList<String> textures = new ArrayList<String>();
            
            while (true) {
            	String sprite = br.readLine();
            	if(sprite == null)
            		break;
            	else {
            		textures.add(sprite);
                    game.tileIds = textures;
            	}
            }

            
            for(int i = 0; i< map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    tiles.add(new Tile(i * size,j * size, size, map[j][i], textures, game));
                }
            }
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void render(SpriteBatch gl, ShapeRenderer sr, Rectf view) {
        for(Tile t: tiles) {
        	if(AABB.collides(t.bounds(), view))
        		t.render(gl, sr, view);
            
        }
    }
    
    public void render(SpriteBatch gl, ShapeRenderer sr) {
        for(Tile t: tiles) {
        		t.render(gl, sr);
            
        }
    }

    public void update() {
        for(Tile t: tiles)
            t.update();
    }

    public void dispose() {
        for(Tile t: tiles)
            t.dispose();
    }

    public void save() {

    }

}
