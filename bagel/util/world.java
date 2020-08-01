package bagel.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class world {

    public ArrayList<TileFloor> tfloors = new ArrayList<TileFloor>();
    public ArrayList<EntityFloor> efloors = new ArrayList<EntityFloor>();
    public int currentFloor = 0;

    public world() {
    }

    public void render_layer(int id, SpriteBatch g, ShapeRenderer s, Rectf view) {
        getTileFloor(id).render(g, s, view);
    }

    public void update_layer(int id, SpriteBatch g, ShapeRenderer s) {
        getTileFloor(id).update();
    }

    public TileFloor getCurrentTileFloor() {
        return getTileFloor(currentFloor);
    }
    public EntityFloor getCurrentEntityFlor() {
        return getEntityFloor(currentFloor);
    }

    public void setCurrentFloor(int id) {
        currentFloor = id;
    }

    public void addTileFloor(TileFloor layer) {
        tfloors.add(layer);
    }

    public void addEntityFloor(EntityFloor layer) {
        efloors.add(layer);
    }

    public TileFloor getTileFloor(int id) {
        for(TileFloor t: tfloors) {
            if (t.id == id) {
                return t;
            }
        }
        Logger.error("Couldn't find Tile Layer with id: " + id + "\nClass: " + this.getClass().toString() + "\nLine: " + Thread.currentThread().getStackTrace()[2].getLineNumber(), true);
        return null;
    }
    public EntityFloor getEntityFloor(int id) {
        for(EntityFloor t: efloors) {
            if (t.id == id) {
                return t;
            }
        }
        Logger.error("Couldn't find Tile Layer with id: " + id + "\nClass: " + this.getClass().toString() + "\nLine: " + Thread.currentThread().getStackTrace()[2].getLineNumber(), true);
        return null;
    }

    public void save() {
        for(TileFloor t: tfloors)
            t.save();
    }
    public void dispose() {
        getCurrentTileFloor().dispose();
    }

}
