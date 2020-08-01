package bagel.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import bagel.entities.npc;
import bagel.util.Rectf;
import bagel.util.TextBox;

import java.util.ArrayList;

public abstract class BasicGame extends ApplicationAdapter implements InputProcessor {

    public OrthographicCamera cam_ortho;
    public SpriteBatch g, ui_g;
    public ShapeRenderer sr, ui_sr;
    public Rectf view;

    // for map editor
    public ArrayList<String> entityIds = new ArrayList<String>();
    public ArrayList<String> tileIds = new ArrayList<String>();
    public boolean editorMode;
    public int editMode =1; // 0-entity mode 1-tile mode
    public int currentEditorId = 0;
    
    public int gamePhase = 0;
    
    public ArrayList<npc> npcs = new ArrayList<npc>();
    
    public TextBox textBox;

    public abstract void init();
    public abstract void rsize(int w, int h);
    public abstract void render();
    public abstract void dispose();


    public void clean() {
        g.dispose();
        ui_g.dispose();
        ui_sr.dispose();
        sr.dispose();
        textBox.dispose();
    }

    public void create() {
        g = new SpriteBatch();
        ui_g = new SpriteBatch();
        sr = new ShapeRenderer();
        ui_sr = new ShapeRenderer();
        cam_ortho = new OrthographicCamera(800, 600);
        cam_ortho.setToOrtho(true);
        view = new Rectf(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        textBox = new TextBox(null, new Vector2(100, 100));

        Gdx.input.setInputProcessor(this);
        init();
    }



    public void pause() {}

    public void resume() {}

    @Override public boolean mouseMoved (int screenX, int screenY) {
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer) {
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override public void resize (int width, int height) {
        rsize(width, height);
    }

    @Override public boolean keyDown (int keycode) {
        return false;
    }

    @Override public boolean keyUp (int keycode) {
        return false;
    }

    @Override public boolean keyTyped (char character) {
        return false;
    }

    @Override public boolean scrolled (int amount) {
        return false;
    }

}