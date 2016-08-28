package com.panders.markus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sun.org.apache.regexp.internal.RECompiler;

public class BestGameEver extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture TextureOne;
    private Texture TextureTwo;
    private Sound soundOne;
    private Music musicOne;
    private Rectangle bucket;
    Vector3 touchpos = new Vector3();


    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        soundOne = Gdx.audio.newSound(Gdx.files.internal("Jingle_Achievement_00.wav"));
        TextureOne = new Texture(Gdx.files.internal("bucket.png"));
        TextureTwo = new Texture(Gdx.files.internal("droplet.png"));
        musicOne = Gdx.audio.newMusic(Gdx.files.internal("ambience.ogg"));
        bucket = new Rectangle();
        bucket.x = 800/2-64/2;
        bucket.y = 20;
        bucket.width=64;
        bucket.height=64;
        musicOne.setLooping(true);
        musicOne.play();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(TextureOne, bucket.x, bucket.y);
        batch.end();

        if(Gdx.input.isTouched()){
            touchpos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touchpos);
            bucket.x=touchpos.x-64/2;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
