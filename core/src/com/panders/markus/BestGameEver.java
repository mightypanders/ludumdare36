package com.panders.markus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

import java.util.Iterator;

public class BestGameEver extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture bucketimage;
    private Texture raindropimage;
    private Sound soundOne;
    private Music musicOne;
    private Rectangle bucket;
    private Vector3 touchpos = new Vector3();
    private Array<Rectangle> raindrops;
    private long lastdroptime;
    private Random rnd;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        soundOne = Gdx.audio.newSound(Gdx.files.internal("Collect_Point_00.mp3"));
        bucketimage = new Texture(Gdx.files.internal("bucket.png"));
        raindropimage = new Texture(Gdx.files.internal("droplet.png"));
        musicOne = Gdx.audio.newMusic(Gdx.files.internal("ambience.ogg"));
        bucket = new Rectangle();
        bucket.x = 800/2-64/2;
        bucket.y = 20;
        bucket.width=64;
        bucket.height=64;
        musicOne.setLooping(true);
        musicOne.play();
        raindrops = new Array<Rectangle>();
        spawnRaindrops();
        rnd = new Random();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bucketimage, bucket.x, bucket.y);
        for (Rectangle raindrop: raindrops){
            batch.draw(raindropimage,raindrop.x,raindrop.y);
        }
        batch.end();

        if(Gdx.input.isTouched()){
            touchpos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touchpos);
            bucket.x=touchpos.x-64/2;
        }


        if(TimeUtils.nanoTime() - lastdroptime >1000000000) spawnRaindrops();
        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y -= rnd.nextInt(251)*Gdx.graphics.getDeltaTime();
            if(raindrop.y+64<0) iter.remove();
            if(raindrop.overlaps(bucket)){
                soundOne.play();
                iter.remove();
            }
        }
    }

    public void spawnRaindrops(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0,800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastdroptime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bucketimage.dispose();
        soundOne.dispose();
        musicOne.dispose();
        raindropimage.dispose();
    }
}
