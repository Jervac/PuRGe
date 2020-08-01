package bagel.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bagel.core.BasicGame;
import bagel.entities.StaticEntity;
import bagel.entities.Tile;

public class EntityFloor {

    public int id;
    public float size;
    public Tile[][] map;
    public ArrayList<StaticEntity> visibleEntities = new ArrayList<StaticEntity>();

    public EntityFloor(int id, float size, FileHandle fileHandle, BasicGame game) {
        this.size = size;
        this.id = id;
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
                    game.entityIds = textures;
                }
            }


            for(int i = 0; i< map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    visibleEntities.add(new StaticEntity(game, i * size,j * size, map[j][i], textures, size));

                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void render(SpriteBatch gl, ShapeRenderer sr, Rectf view) {
        for(StaticEntity t: visibleEntities)
            t.render(gl, sr);
    }

    public void update() {
        for(StaticEntity t: visibleEntities)
            t.update();
    }

    public void dispose() {
        for(StaticEntity t: visibleEntities)
            t.dispose();
    }

    public void save() {

    }

}
