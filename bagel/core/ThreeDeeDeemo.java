package bagel.core;


import bagel.rendering.Graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class ThreeDeeDeemo extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite a;

    PerspectiveCamera cam;
    public Model model;
    public ModelInstance instance;
    public ModelBatch modelBatch;

    public Environment environment;

    public CameraInputController camController;

    @Override
    public void create () {
        batch = new SpriteBatch();

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        a = new Sprite(new Texture("badlogic.jpg"));
        a.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        Graphics.scaleSprite(240.72452f, 240.339751f, a);
    }

    @Override
    public void render () {
        Graphics.clearScreen();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camController.update();

        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();

        batch.begin();
        a.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        a.getTexture().dispose();
        model.dispose();
        modelBatch.dispose();
    }
}
