package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    //Zombie
    TextureRegion zRightFacing;
    TextureRegion zUpFacing;
    TextureRegion zDownFacing;
    TextureRegion zLeftFacing;
    TextureRegion zPositionImg;
    TextureRegion zDown;
    TextureRegion zUp;
    TextureRegion zLeft;
    TextureRegion zRight;
    TextureRegion spawnZombie;

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

    Animation zWalkRight;
    Animation zWalkLeft;
    Animation zWalkDown;
    Animation zWalkUp;
    Animation zWalk;


    static final int WIDTH = 16;
    static final int HEIGHT = 20;
    static final float MAX_VELOCITY = 100;
    static final float DECELERATION = 0.95f;
    static final float SUPER_VELOCITY = 300;
    float time;
    float zTime;

    Random random = new Random();
    int img;
    int zImg;
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

        //Zombie
        //Down
        zDownFacing = grid[6][5];
        //zDown = new TextureRegion(zDownFacing);
        //zDownFacing.flip(true,false);
        //zWalkDown = new Animation(0.2f, zDownFacing, zDown);
        //Up
        zUpFacing = grid[6][6];
        //zUp = new TextureRegion(zUpFacing);
        //zUp.flip(true,false);
        //zWalkUp = new Animation(0.2f, zUpFacing, zUp);
        //Right
        zRightFacing = grid[6][7];
        //zRight = new TextureRegion(zRightFacing);
        //zRight.flip(true,false);
        //zWalkRight = new Animation(0.2f,zRightFacing, grid[6][7]);
        //Left
        zLeftFacing = new TextureRegion(zRightFacing);
        zLeftFacing.flip(true,false);
        //zWalkLeft = new Animation(0.2f,zLeftFacing, zLeft);
        zWalk = new Animation(0.2f, grid[6][5], grid[6][6], grid[6][7]);


	}

	@Override
	public void render () {
        move();

        time += Gdx.graphics.getDeltaTime();
        zTime -= Gdx.graphics.getDeltaTime();

        if (yv > 0) {
            upFacing = walkUp.getKeyFrame(time, true);
            //zUpFacing = zWalkUp.getKeyFrame(time, true);
            zUpFacing = zWalk.getKeyFrame(zTime,true);
        }
        if (yv < 0) {
            downFacing = walkDown.getKeyFrame(time, true);
            //zDownFacing = zWalkDown.getKeyFrame(time, true);
            zDownFacing = zWalk.getKeyFrame(zTime, true);
        }
        if (xv > 0) {
            rightFacing = walkRight.getKeyFrame(time, true);
            //zRightFacing = zWalkRight.getKeyFrame(time, true);
            zRightFacing = zWalk.getKeyFrame(zTime, true);
        }
        if (xv < 0) {
            leftFacing = walkLeft.getKeyFrame(time, true);
            //zLeftFacing = zWalkLeft.getKeyFrame(time,true);
            zLeftFacing = zWalk.getKeyFrame(zTime,true);
        }


        Gdx.gl.glClearColor(0, .5f, 0, .6f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (img == 1 && zImg == 1){
            positionImg = upFacing;
            zPositionImg = zUpFacing;
        }
        if (img == 2 && zImg == 2) {
            positionImg = downFacing;
            zPositionImg = zDownFacing;
        }
        if (img == 3 && zImg == 3) {
            positionImg = rightFacing;
            zPositionImg = zDownFacing;
        }
        else {
            positionImg = leftFacing;
            zPositionImg = zLeftFacing;
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
            zImg = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yv = -MAX_VELOCITY;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                yv = -SUPER_VELOCITY;
            }
            img = 2;
            zImg = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xv = SUPER_VELOCITY;
            }
            img = 3;
            zImg = 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = -MAX_VELOCITY;
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                xv = -SUPER_VELOCITY;
            }
            img = 4;
            zImg = 4;
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
