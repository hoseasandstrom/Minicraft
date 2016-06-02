package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import sun.jvm.hotspot.memory.Space;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion stand;

    static final int WIDTH = 16;
    static final int HEIGHT = 16;

    float x, y, xv, yv;
    boolean faceRight;
    Animation walk;

    static final float MAX_VELOCITY = 100;
    static final float DECELERATION = 0.95f;
    static final float SUPER_VELOCITY = 200;
    float time;

    @Override
	public void create () {
		batch = new SpriteBatch();
        Texture sheet = new Texture("tiles.png");
        TextureRegion[][] tiles = TextureRegion.split(sheet, HEIGHT, WIDTH);
        stand = tiles[6][2];
        walk = new Animation(0.2f, tiles[6][0], tiles[6][1], tiles[6][3]);
        //left = new TextureRegion(right);
        //left.flip(true, false);
	}

	@Override
	public void render () {
        move();

        time += Gdx.graphics.getDeltaTime();

        TextureRegion img;
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            img = walk.getKeyFrame(time, true);
        }
        else {
            img = stand;
        }
        Gdx.gl.glClearColor(.5f, .5f, .5f, .5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(faceRight) {
            batch.draw(img, x, y, WIDTH * 3, HEIGHT * 3);
        }
        else {
            batch.draw(img, x + WIDTH * 3, y, WIDTH * -3, HEIGHT * 3 );
        }
            batch.end();
    }


    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yv = -MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            faceRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = -MAX_VELOCITY;
            faceRight = false;
        }
        float delta = Gdx.graphics.getDeltaTime();
        y += yv * delta;
        x += xv * delta;

        yv = decelerate(yv);
        xv = decelerate(xv);

    }
    public float decelerate(float velocity) {
        velocity = DECELERATION;
        if(Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }
}
