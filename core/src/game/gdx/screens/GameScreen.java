package game.gdx.screens;

import static objects.BallType.DEFAULT;
import static objects.BallType.SLOWING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Random;

import Skills.Paralysis;
import Skills.Skill;
import game.gdx.GameSettings;
import objects.AreaLoader;
import game.gdx.MyGdxGame;
import objects.Area;
import objects.Ball;
import objects.Hero;
import objects.MoveInfo;
import objects.Pellet;
import Skills.Warp;


public class GameScreen implements Screen {
    private final MyGdxGame myGdxGame;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture img;
    Area currentArea;
    public Hero player;
    Random random = new Random();
    boolean isWDown;
    boolean isADown;
    boolean isSDown;
    boolean isDDown;
    boolean isShift;
    Vector2 lastDirection = null;
    BitmapFont hotBarFont;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        batch = new SpriteBatch();
        img = new Texture("ball.png");
        shapeRenderer = new ShapeRenderer();
        Array<Area> areas = AreaLoader.loadAllAreas();
        currentArea = areas.get(0);

        hotBarFont = new BitmapFont();


//        player = new PlayerBall(currentArea);

        player = new Hero(currentArea, "Rime");
        Warp warpSkill = new Warp(player);
        Paralysis paralysis = new Paralysis(player);
        player.setSkill1(warpSkill);
        player.setSkill2(paralysis);
        player.area = currentArea;
        initBalls();


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                player.toggleMovement();
                return true;
            }
            @Override
            public boolean keyDown(int keycode) {
                // Реакция на нажатие клавиш
                switch (keycode) {
                    case Input.Keys.W:
                        isWDown = true;
                        break;
                    case Input.Keys.S:
                        isSDown = true;
                        break;
                    case Input.Keys.A:
                        isADown = true;
                        break;
                    case Input.Keys.D:
                        isDDown =true;
                        break;
                    case Input.Keys.ESCAPE:
                        Gdx.app.exit();
                        break;
                    case Input.Keys.SHIFT_LEFT:
                        player.maxSpeed /= 2f;
                        break;
                    case Input.Keys.F:
                        player.areaId++;
                        loadAreaById(player.areaId);
                        break;
                    case Input.Keys.G:
                        player.toggleGodMode();
                        break;
                    case Input.Keys.Z:
                        player.useSkill1();
                        break;
                    case Input.Keys.X:
                        player.useSkill2();
                        break;
                    case Input.Keys.NUM_1:
                        if (player.levelPoints > 0 && player.maxSpeed < 17){
                            player.levelPoints--;
                            player.maxSpeed += 0.5;
                        }
                        break;
                    case Input.Keys.NUM_2:
                        if (player.levelPoints > 0 && player.maxMana < 300){
                            player.levelPoints--;
                            player.maxMana += 5;
                        }
                        break;
                    case Input.Keys.NUM_3:
                        if (player.levelPoints > 0 && player.regen < 7){
                            player.levelPoints--;
                            player.regen += 0.3f;
                        }
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                // Реакция на отпускание клавиш (например, остановка движения)
                switch (keycode) {
                    case Input.Keys.W:
                        isWDown = false;
                        break;
                    case Input.Keys.S:
                        isSDown = false;
                        break;
                    case Input.Keys.A:
                        isADown = false;
                        break;
                    case Input.Keys.D:
                        isDDown = false;
                        break;
                    case Input.Keys.SHIFT_LEFT:
                        player.maxSpeed *= 2;
                }
                return true;
            }
        });
        initBalls();
    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        for (Ball ball : currentArea.balls) {
            updateBall(ball);
        }
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        Vector3 mousePosition = myGdxGame.camera.unproject(new Vector3(mouseX, mouseY, 0));
        updatePlayer(mousePosition, delta);
        myGdxGame.camera.position.x = player.position.x;
        myGdxGame.camera.position.y = player.position.y;

        myGdxGame.camera.update();
        shapeRenderer.setProjectionMatrix(myGdxGame.camera.combined);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.LIGHT_GRAY);
        if(currentArea.isWinArea){
            shapeRenderer.setColor(Color.YELLOW);
        }
        shapeRenderer.rect(0, 0, currentArea.safeZone, currentArea.height);
        shapeRenderer.rect(currentArea.width - currentArea.safeZone, 0, currentArea.safeZone, currentArea.height);

        shapeRenderer.setColor(Color.WHITE);
        if(currentArea.isWinArea){
            shapeRenderer.setColor(Color.YELLOW);
        }
        shapeRenderer.rect(currentArea.safeZone, 0, currentArea.width - currentArea.safeZone * 2, currentArea.height);

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(0, 0, currentArea.backZone, currentArea.height);
        shapeRenderer.rect(currentArea.width - currentArea.winZone, 0, currentArea.winZone, currentArea.height);
        shapeRenderer.end();
        int cellSize = 40;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0.6f, 0.6f, 0.6f, 1));

        // Draw vertical and horizontal grid lines
        for (int x = 0; x <= currentArea.width; x += cellSize) {
            shapeRenderer.line(x, 0, x, currentArea.height);  // Draw vertical lines
        }

        for (int y = 0; y <= currentArea.height; y += cellSize) {
            shapeRenderer.line(0, y, currentArea.width, y);  // Draw horizontal lines
        }

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Pellet pellet : currentArea.pellets){
            shapeRenderer.setColor(pellet.color);
            shapeRenderer.circle(pellet.position.x, pellet.position.y, pellet.radius);
        }
        shapeRenderer.end();
//        batch.setProjectionMatrix(myGdxGame.camera.combined);
//        batch.begin();
//        for (Ball ball : currentArea.balls) {
//            Texture circleTexture = createCircleTexture((int) ball.radius, new Color(1, 0, 0, 1));
//            batch.draw(circleTexture, ball.position.x - ball.radius, ball.position.y - ball.radius, ball.radius * 2, ball.radius * 2);
//        }
//        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Ball ball : currentArea.balls) {
            shapeRenderer.setColor(ball.color);
            shapeRenderer.circle(ball.position.x, ball.position.y, ball.radius);
            if (ball.ballType != DEFAULT){
                drawBallAura(ball);
            }
        }

        drawSkillAura(player.skill1);
        drawSkillAura(player.skill2);


        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(player.position.x, player.position.y, player.radius);
        shapeRenderer.end();

        drawHotBar();
    }

    private void drawBallAura(Ball ball) {
        switch (ball.ballType){
            case DEFAULT:
                return;
            case SLOWING:
                shapeRenderer.setColor(new Color(1, 0, 0, 0.5f));
                shapeRenderer.circle(ball.position.x, ball.position.y, 250);
                break;
            case DRAINING:
                shapeRenderer.setColor(new Color(0, 0, 1, 0.5f));
                shapeRenderer.circle(ball.position.x, ball.position.y, 250);
                break;
        }
    }

    private void drawHotBar(){
        batch.begin();
        hotBarFont.setColor(Color.WHITE); // Цвет текста

        // Отображение уровня
        String levelText = "Level: " + player.level;
        hotBarFont.draw(batch, levelText, 10, 50);

        // Отображение скорости
        String speedText = "Speed: " + String.format("%.1f", player.maxSpeed);
        hotBarFont.draw(batch, speedText, 10, 20);

        String mana = "Mana: " + (int) player.mana + " / " + (int) player.maxMana;
        hotBarFont.draw(batch, mana, 110, 20);

        String regen = "Regen " + player.regen;
        hotBarFont.draw(batch, regen, 220, 20);


        hotBarFont.setColor(Color.GOLD);
        String levelPointsText = "Points: " + player.levelPoints;
        hotBarFont.draw(batch, levelPointsText, 80, 50);
        batch.end();
    }
    public void drawSkillAura(Skill skill) {
        if (skill.hasAura()) {
            if (skill instanceof Paralysis) {
                ((Paralysis) skill).drawAura(shapeRenderer);
            }
        }
    }
    public void initBalls(){
        if (currentArea.balls != null) {
            currentArea.balls.clear();
        }
        for(int i = 0; i < currentArea.numOfBalls; i++) {
            currentArea.balls.add(new Ball(currentArea.ballRadius, currentArea.ballSpeed, DEFAULT, currentArea));
        }
        for(int i = 0; i < currentArea.numOfSlowings; i++) {
            currentArea.balls.add(new Ball(20, currentArea.ballSpeed - 2, SLOWING, currentArea));
        }
        int pelletAmount = 20;
        currentArea.pellets.clear();
        if(currentArea.isWinArea){
            pelletAmount = 70;
        }
        for(int i = 0; i < pelletAmount; i++){
            currentArea.pellets.add(new Pellet(currentArea));
        }
    }
    private void loadAreaById(int id){
        Array<Area> areas = AreaLoader.loadAllAreas();
        for (Area area : areas){
            if (player.areaId == area.id){
                currentArea = area;
                player.area = currentArea;
                initBalls();
                System.out.println(player.areaId);
                teleportPlayerToStart();
            }
        }
    }
    private void checkCollisions() {
        ArrayList<Float> slowingDistances = new ArrayList<Float>();
        for (Ball ball : currentArea.balls) {
            float distance = player.position.dst(ball.position);
            if (ball.ballType == SLOWING){
                slowingDistances.add(distance);
            }
            if (distance < player.radius + ball.radius) {
                teleportPlayerToStart();
                break;  // Teleport once, no need to check further collisions
            }
        }
        if (!slowingDistances.isEmpty() && Collections.min(slowingDistances) - player.radius <= 250){
            player.isSlow = true;
        } else {
            player.isSlow = false;
        }
        ListIterator<Pellet> iterator = currentArea.pellets.listIterator();
        while (iterator.hasNext()) {
            Pellet pellet = iterator.next();
            float distance = player.position.dst(pellet.position);

            if (distance < player.radius + pellet.radius) {
                iterator.remove();
                // добавить новую пеллету
                iterator.add(new Pellet(currentArea));
                player.xp += currentArea.xpPerPellet;
                checkLevel();
            }
        }
    }
    private void teleportPlayerToStart() {
        float x = player.radius + currentArea.backZone + 10;
        float y = player.position.y;
        player.position.set(x, y);
    }
    private void teleportPlayerToEnd() {
        float x = currentArea.width - currentArea.winZone - player.radius - 10;
        float y = player.position.y;
        player.position.set(x, y);
    }
    public void updateBall(Ball ball) {
        ball.position.add(ball.velocity.x, ball.velocity.y);
        if (ball.position.x < ball.radius + currentArea.safeZone || ball.position.x + currentArea.safeZone >= currentArea.width - ball.radius) {
            ball.velocity.x = -ball.velocity.x;
        }
        if (ball.position.y <= ball.radius || ball.position.y >= currentArea.height - ball.radius) {
            ball.velocity.y = -ball.velocity.y;
        }
    }
    private void updatePlayer(Vector3 mPosition, float delta) {
        if (!player.isGod){
            checkCollisions();
        }
        if (player.mana < player.maxMana) {
            player.mana += delta * player.regen;
        }

        Vector2 direction;
        float speed;
        if (isWDown || isADown || isSDown || isDDown || player.isMouseOn){
            player.isMoving = true;
        } else {
            player.isMoving = false;
        }

        if (player.isMouseOn) {
            Vector2 mousePosition = new Vector2(mPosition.x, mPosition.y);
            float distance = player.position.dst(mousePosition);
            float speedFactor = Math.min(distance / 200f, 1);
            speed = player.minSpeed + (player.maxSpeed - player.minSpeed) * speedFactor;
            direction = new Vector2(mousePosition.x - player.position.x, mousePosition.y - player.position.y);
        } else {
            speed = player.maxSpeed;
            direction = new Vector2(0, 0);
            if (isWDown) {
                direction.y += 1;  // Вверх
            }
            if (isSDown) {
                direction.y -= 1;  // Вниз
            }
            if (isADown) {
                direction.x -= 1;  // Влево
            }
            if (isDDown) {
                direction.x += 1;  // Вправо
            }
        }
        if (direction.len() > 0) {
            direction.nor();
        }
        if (isShift){
            speed /= 2f;
        }
        if (player.isSlow){
            speed = 0.7f * speed;
        }
        player.velocity.set(direction.scl(speed));

        if (player.isMoving){
             lastDirection = direction;
        }
        player.moveInfo = new MoveInfo(isWDown, isADown, isSDown, isDDown, player.isMouseOn, new Vector2(mPosition.x, mPosition.y), lastDirection);
        if(player.skill1 != null){
            player.skill1.update(delta);
        }
        if(player.skill2 != null){
            player.skill2.update(delta);
        }

        player.position.add(player.velocity.x, player.velocity.y);
        if (player.position.x + player.radius >= currentArea.width - currentArea.winZone){
            if (currentArea.isWinArea){ // in victoryZone
                player.areaId = 1;
                loadAreaById(player.areaId);
                player.xp = 0;
                player.level = 0;
                return;
            } else { // in nextAreaZone
            player.areaId++;
            if (player.areaId > player.maxAreaReached){
                player.maxAreaReached = player.areaId;
                player.xp += 50;
                checkLevel();
            }
            loadAreaById(player.areaId);
            }
        }
        if (player.position.x - player.radius <= currentArea.backZone){ // in backZone
            player.areaId--;
            loadAreaById(player.areaId);
            teleportPlayerToEnd();
        }

        if (player.position.x < player.radius) {
            player.position.x = player.radius; // Упираемся в левую стену
        } else if (player.position.x > currentArea.width - player.radius) {
            player.position.x = currentArea.width - player.radius; // Упираемся в правую стену
        }

        if (player.position.y < player.radius) {
            player.position.y = player.radius; // Упираемся в нижнюю стену
        } else if (player.position.y > currentArea.height - player.radius) {
            player.position.y = currentArea.height - player.radius; // Упираемся в верхнюю стену
        }
    }
    private void checkLevel() {
        int x = player.level;
        player.level = (int) (player.xp / 50f);
        player.levelPoints += player.level - x;
        System.out.println(player.levelPoints);
    }
    public Texture createCircleTexture(int radius, Color color) {
        Pixmap pixmap = new Pixmap(radius * 2, radius * 2, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.SourceOver);
        pixmap.setColor(color);
        pixmap.fillCircle(radius, radius, radius);

        Texture texture = new Texture(pixmap);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pixmap.dispose();
        return texture;
    }
    private void drawHotBarBackGround(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
        shapeRenderer.rect((float) GameSettings.SCREEN_WIDTH / 2 - 200, 0, 400, 100);
        shapeRenderer.end();
    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        shapeRenderer.dispose();
    }
}
