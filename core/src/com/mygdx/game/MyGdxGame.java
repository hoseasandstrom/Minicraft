package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	//Character
    TextureRegion rightFacing;
    TextureRegion upFacing;
    TextureRegion downFacing;
    TextureRegion leftFacing;
    TextureRegion down;
    TextureRegion up;
    TextureRegion left;
    TextureRegion right;
    TextureRegion zombieImg;

    Rectangle zombie;
    long lastZombieTime;


    //Landscape
    TextureRegion tree;
    TextureRegion cactus;
    TextureRegion water;
    TextureRegion sandpit;


    float x, y, xv, yv;
    Animation walkRight;
    Animation walkUp;
    Animation walkDown;
    Animation walkLeft;


    static final int WIDTH = 16;
    static final int HEIGHT = 20;
    static final float MAX_VELOCITY = 100;
    static final float DECELERATION = 0.95f;
    static final float SUPER_VELOCITY = 300;
    float time;


    Random random = new Random();
    int img;
    TextureRegion positionImg;

    @Override
	public void create () {
		batch = new SpriteBatch();
        Texture tiles = new Texture("tiles.png");

        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);

        //Down
        downFacing = grid[6][0];
        down = new TextureRegion(downFacing);
        down.flip(true,false);
        walkDown = new Animation(0.2f, grid[6][0], down);

        //Up
        upFacing = grid[6][1];
        up = new TextureRegion(upFacing);
        up.flip(true,false);
        walkUp = new Animation(0.2f, grid[6][1], up);

        //Right
        rightFacing = grid[6][2];
        right = new TextureRegion(rightFacing);
        right.flip(true,false);
        walkRight = new Animation(0.2f, grid[6][2], grid[6][3]);

        //Left
        leftFacing = new TextureRegion(rightFacing);
        leftFacing.flip(true,false);
        walkLeft = new Animation(0.2f,leftFacing, left);

        zombieImg = grid[6][5];
        zombie = new Rectangle();
        spawnZombie();

	}
    public void spawnZombie() {
        Rectangle zombie = new Rectangle();
        zombie.x = MathUtils.random();
        zombie.y = MathUtils.random();
        zombie.width = WIDTH;
        zombie.height = HEIGHT;
    }

	@Override
	public void render () {
        move();

        time += Gdx.graphics.getDeltaTime();

        if (yv > 0) {
            upFacing = walkUp.getKeyFrame(time, true);

        }
        if (yv < 0) {
            downFacing = walkDown.getKeyFrame(time, true);

        }
        if (xv > 0) {
            rightFacing = walkRight.getKeyFrame(time, true);

        }
        if (xv < 0) {
            leftFacing = walkLeft.getKeyFrame(time, true);

        }


        Gdx.gl.glClearColor(0, .5f, 0, .6f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Rectangle zombie = new Rectangle();

        batch.begin();

        if (img == 1){
            positionImg = upFacing;

        }
        if (img == 2) {
            positionImg = downFacing;

        }
        if (img == 3) {
            positionImg = rightFacing;

        }
        else {
            positionImg = leftFacing;

        }
        batch.draw(positionImg, x, y, HEIGHT * 2, WIDTH * 2);
        batch.end();
    }

    public void move() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                yv = SUPER_VELOCITY;
            }
            img = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yv = -MAX_VELOCITY;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                yv = -SUPER_VELOCITY;
            }
            img = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xv = SUPER_VELOCITY;
            }
            img = 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = -MAX_VELOCITY;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xv = -SUPER_VELOCITY;
            }
            img = 4;
        }
        if(x>Gdx.graphics.getWidth()){
            x = -10;
        }
        if(x<-5){
            x = Gdx.graphics.getWidth();
        }
        if(y<-10){
            y = Gdx.graphics.getHeight();
        }
        if (y>Gdx.graphics.getHeight()){
            y=-10;
        }
        if((x>=1000 && x<=1000) && (y<=1000 && y>=1000)){
            x = 0;
            y = 0;
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
