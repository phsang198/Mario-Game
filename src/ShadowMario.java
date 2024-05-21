import bagel.*;
import java.util.Properties;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2024
 *
 * Please enter your name below
 * @author
 */
public class ShadowMario extends AbstractGame {

    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
                message_props.getProperty("title"));

        newGame(game_props,message_props);
        
    }
    public void newGame(Properties game_props, Properties message_props)
    {
        obm = new ObjectsManager(game_props) ; 
        screen = new Screen(game_props,message_props); 
        score = new Score(game_props,message_props) ;
        gameState = GameState.START;
    }
    private ObjectsManager obm = null ;
    private Screen screen = null ; 
    private Score score = null ;
    private GameState gameState = GameState.START;
    private GameRound gameRound = GameRound.ONE;
   
    public class Objects 
    {
        public Objects()
        {
            
        }
        public Objects(Integer _X, Integer _Y)
        {
            X = _X;
            Y = _Y;
        }
        protected Integer X = 0 ; 
        protected Integer Y = 0 ;
        public Integer speed = 0 ; 
        public Double radius = 0.0 ; 


        public Image IMAGE ; 
        protected double Calculate(Integer x1 , Integer y1, Integer x2, Integer y2)
        {
            double dodaiAB = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
            return dodaiAB;
        }
        public void Draw(Input input) 
        {
            if (gameState == GameState.PLAY)
            {
                if (input.isUp(Keys.LEFT) || input.isUp(Keys.RIGHT)) {
                    speed = 0;
                }
                if (input.isDown(Keys.LEFT)) {
                    speed += 5;
                    X += speed;
                }
                if (input.isDown(Keys.RIGHT)) {
                    speed += 5;
                    X -= speed;
                }
            }
            IMAGE.draw(X,Y);
        }
    }
    private class Enemy extends Objects
    {
        public Enemy(Integer _X, Integer _Y, Image _image, Integer _speed, Double _radius, Integer _damage, Integer _randomSpeed, Integer _maxRange) {
            super(_X, _Y); 
            IMAGE = _image ;
            speed = _speed ;
            radius = _radius; 
            damage = _damage;
            randomSpeed = _randomSpeed;
            direction = Math.random() < 0.5;
            countDirect = 0;
            maxRange = _maxRange;
            countTime = 0;
        }
        public Boolean isDamaged = false; 
        public Integer damage = 1;
        public Integer randomSpeed = 1;
        public Boolean direction = false;
        public Integer countDirect = 0;
        public Integer maxRange = 50;
        public Integer countTime = 0;

        @Override
        public void Draw(Input input) 
        {
            countDirect += 1;
            if (countTime == 1)
                maxRange = 100; 
            if ( countDirect == maxRange)
            {
                countDirect = 0;
                countTime = 1; 
                direction = !direction;
            }
            if (gameState == GameState.PLAY)
            {
                if (input.isUp(Keys.LEFT) || input.isUp(Keys.RIGHT)) {
                    speed = 0;
                }
                if (input.isDown(Keys.LEFT)) {
                    speed += 5;
                    X += speed;
                }
                if (input.isDown(Keys.RIGHT)) {
                    speed += 5;
                    X -= speed;
                }
                
                if (direction == false)
                        X -= randomSpeed;
                else
                    X += randomSpeed;
            }
            IMAGE.draw(X,Y);
        }
    }
    private class Coin extends Objects
    {
        public Coin(Integer _X, Integer _Y, Image _image, Integer _speed, Double _radius, Integer _value) {
            super(_X, _Y); 
            IMAGE = _image ;
            speed = _speed ; 
            radius = _radius; 
            value = _value; 
        }
        public Integer value = 0 ; 
        public Boolean isScored = false; 
    }

    private class Platform extends Objects {
        public Platform(Integer _X, Integer _Y) {
            super(_X, _Y);
        }
    }

    private class Fly_Platform extends Objects {
        public Fly_Platform(Integer _X, Integer _Y, Image _image, Integer _speed, Integer _lenght, Integer _height, Integer _randomSpeed, Integer _maxRange) {
            super(_X, _Y);

            IMAGE = _image ;
            speed = _speed ;
            lenght = _lenght; 
            height = _height;
            randomSpeed = _randomSpeed;
            direction = Math.random() < 0.5;
            countDirect = 0;
            countTime = 0;
            maxRange = _maxRange ;
        }
        public Integer lenght = 200;
        public Integer height = 50;
        public Integer randomSpeed = 1;
        public Boolean direction = false;
        public Integer countDirect = 0;
        public Integer countTime = 0;
        public Integer maxRange = 50;
        @Override
        public void Draw(Input input) 
        {
            countDirect += 1;
            if (countTime == 1)
                maxRange = 100; 
            if ( countDirect == maxRange)
            {
                countDirect = 0;
                countTime = 1; 
                direction = !direction;
            }
            if (gameState == GameState.PLAY)
            {
                if (input.isUp(Keys.LEFT) || input.isUp(Keys.RIGHT)) {
                    speed = 0;
                }
                if (input.isDown(Keys.LEFT)) {
                    speed += 5;
                    X += speed;
                }
                if (input.isDown(Keys.RIGHT)) {
                    speed += 5;
                    X -= speed;
                }
                if (direction == false)
                    X -= randomSpeed;
                else
                    X += randomSpeed;
            }
            IMAGE.draw(X,Y);
        }
    }

    private class invinciblePower extends Objects {
        public invinciblePower(Integer _X, Integer _Y, Image _image, Integer _speed, Double _radius, Integer _maxFrames) {
            super(_X, _Y); 
            IMAGE = _image ;
            speed = _speed ; 
            radius = _radius; 
            maxFrames = _maxFrames; 
        }
        public Integer maxFrames = 0 ; 
        public Boolean isScored = false; 
    }

    private class doubleScore extends Objects {
        public doubleScore(Integer _X, Integer _Y, Image _image, Integer _speed, Double _radius, Integer _maxFrames) {
            super(_X, _Y); 
            IMAGE = _image ;
            speed = _speed ; 
            radius = _radius; 
            maxFrames = _maxFrames; 
        }
        public Integer maxFrames = 0 ; 
        public Boolean isScored = false; 
    }

    private class fireball extends Objects {
        public fireball(Integer _X, Integer _Y, Image _image, Integer _speed, Double _radius, Integer _damage, Boolean _isBossDame) {
            super(_X, _Y);
            IMAGE = _image;
            speed = _speed;
            radius = _radius;
            damage = _damage;
            isBossDame = _isBossDame;
            isDamaged = false; 
        }

        public Integer damage = 1;
        public Boolean direction = false;
        public boolean isDamaged = false; 

        public Boolean isBossDame = false ; 
        @Override
        public void Draw(Input input)
        {
            if (isDamaged == false)
            {
                if (direction == false) {
                    X -= speed;
                } else {
                    X += speed;
                }
                if (X >= 0 && X < 1024) {
                    IMAGE.draw(X, Y);
                }
                else
                    isDamaged = true; 
        }
        }
    }
    private class Boss extends Objects
    {
        public Boss(Integer _X, Integer _Y) {
            super(_X, _Y); 
            speed = 0 ; 
        }

        public Double activedRadius = 0.0;
        public Integer randomFrame = 0;
        @Override
        public void Draw(Input input)
        {
            if (score.bossHealth <= 0)
                Y += 2;
            super.Draw(input);
        }
    }
    private class Player extends Objects
    {
        public Player(Integer _X, Integer _Y) {
            super(_X, _Y); 
            saveY = _Y ; 
            speed = 0;
        }
        public Integer saveY = 0 ;
        public Image IMAGE_LEFT ; 
        public Image IMAGE_RIGHT ;
        public Integer isJumped = 0;
        public Boolean isRight = true ;  
        public int isPowered = 0 ; 
        public int isX2 = 0 ; 
        public int isFly = 0;
        
        
        public void Intersection(Input input)
        {
            for (Enemy enemy : obm.enemies)
                if (enemy.isDamaged == false) {
                    double curDis = radius + enemy.radius;
                    double dis = enemy.Calculate(enemy.X, enemy.Y, X, Y);
                    if (dis <= curDis - 15) {
                        if (isPowered == 0) {
                            enemy.isDamaged = true;
                            score.updatePlayerHealth(enemy.damage);
                        }
                    }
                }
            for (Coin coin : obm.coins)
                if (coin.isScored == false) {
                    double curDis = radius + coin.radius;
                    double dis = coin.Calculate(coin.X, coin.Y, X, Y);
                    if (dis <= curDis - 15) {
                        if (isX2 == 0)
                            score.updateScore(coin.value);
                        else
                            score.updateScore(coin.value * 2);
                        coin.isScored = true;
                        break;
                    }
                }
            if (obm.dScores != null) {
                for (doubleScore _dScore : obm.dScores)
                    if (_dScore.isScored == false) {
                        double curDis = radius + _dScore.radius;
                        double dis = _dScore.Calculate(_dScore.X, _dScore.Y, X, Y);
                        if (dis <= curDis - 15) {
                            isX2 = _dScore.maxFrames;
                            _dScore.isScored = true;
                            break;
                        }
                    }
            }
            if (obm.inPowers != null) {
                for (invinciblePower _inPower : obm.inPowers)
                    if (_inPower.isScored == false) {
                        double curDis = radius + _inPower.radius;
                        double dis = _inPower.Calculate(_inPower.X, _inPower.Y, X, Y);
                        if (dis <= curDis - 15) {
                            isPowered = _inPower.maxFrames;
                            _inPower.isScored = true;
                            break;
                        }
                    }
            }

            if (obm.boss != null) {
                obm.boss.randomFrame++;
                if (obm.boss.randomFrame == 100) {
                    if (Math.random() < 0.5) {
                        double curDis = radius + obm.boss.activedRadius;
                        double dis = obm.boss.Calculate(obm.boss.X, obm.boss.Y, X, Y);
                        if (dis <= curDis - 15) {
                            Boolean direct = false;

                            if (obm.boss.X >= X)
                                direct = false;
                            else
                                direct = true;

                            fireball _ball = new fireball(obm.boss.X, obm.boss.Y, obm.fireballInit.IMAGE,
                                    obm.fireballInit.speed,
                                    obm.fireballInit.radius, obm.fireballInit.damage, true);
                            _ball.direction = direct;
                            obm.fireballs.add(_ball);
                        }
                    }
                    obm.boss.randomFrame = 0;
                }
                if (input.wasPressed(Keys.S)) {
                    double dis = obm.boss.Calculate(obm.boss.X, obm.boss.Y, X, Y);
                    if (dis <= obm.boss.activedRadius) {
                        Boolean direct = false;

                        if (obm.boss.X >= X)
                            direct = true;
                        else
                            direct = false;

                        fireball _ball = new fireball(obm.player.X, obm.player.Y, obm.fireballInit.IMAGE,
                                obm.fireballInit.speed,
                                obm.fireballInit.radius, obm.fireballInit.damage, false);

                        _ball.direction = direct;
                        obm.fireballs.add(_ball);
                    }
                }
            }

            EndFlag endFlag = obm.endFlag;
            double curDis = radius + endFlag.radius;
            double dis = endFlag.Calculate(endFlag.X, endFlag.Y, X, Y);
            if (dis <= curDis - 15) {
                if (gameRound != GameRound.THREE)
                    gameState = GameState.WIN;
                else {
                    if (score.bossHealth <= 0)
                        gameState = GameState.WIN;
                }
            }
        }

        public void Find()
        {
            if (obm.flyPlatforms != null) 
            {
                isFly = 0;
                for (Fly_Platform _flyP : obm.flyPlatforms) {
                    if (Math.abs(X - _flyP.X) < _flyP.lenght &&
                            Math.abs(Y - _flyP.Y) <= _flyP.height &&
                            Math.abs(Y - _flyP.Y) > _flyP.height - 1) {
                        isFly = _flyP.Y;
                        isJumped = 0;
                        speed = 0; 
                        break;
                    }
                }
                if (isFly == 0)
                {
                    isJumped = -1;
                    speed = 8;
                }
            }
        }
        @Override
        public void Draw(Input input)
        {   
            if ( gameState == GameState.PLAY)
            {
                if ( isPowered > 0 ) isPowered-- ;
                if ( isX2 > 0 ) isX2-- ;
                Intersection(input) ; 
                if (gameRound == GameRound.ONE)
                {
                    if ( input.isDown(Keys.LEFT)) 
                    {
                        isRight = false ; 
                    }
                    if ( input.isDown(Keys.RIGHT)) 
                    {
                        isRight = true ; 
                    }
                    if ( input.isDown(Keys.UP) && isJumped == -1) 
                    {
                        isJumped = 1 ; 
                        speed = 13 ;
                    }
                    if ( isJumped == 1 )
                    {
                        if ( Y >= 510) 
                        {
                            Y -= speed ; 
                            //speed += 1 ;
                        }
                        else 
                        {
                            speed = 8 ;
                            isJumped = 0 ; 
                        }
                    }
                    if (  isJumped == 0) 
                    {
                        Y += speed ; 
                        //speed += 1 ;
                        if ( Y >= saveY)
                        {
                            speed = 0 ; 
                            Y = saveY ; 
                            isJumped = -1 ;
                        }
                    }
                }
                else
                {
                    if (input.isDown(Keys.LEFT)) {
                        isRight = false;
                    }
                    if (input.isDown(Keys.RIGHT)) {
                        isRight = true;
                    }

                    if (input.wasPressed(Keys.UP) && (Y == 687 || (Y == 505))) {
                        isJumped = 1;
                        speed = 13;
                    }

                    if (isJumped == 1) {
                        Y -= speed;
                    }
                    if (Y == 505 || Y <= 305) {
                        Find();
                    }
                    if (isJumped == -1) {
                        Y += speed;

                        if (Y >= saveY) {
                            speed = 0;
                            Y = saveY;
                            isJumped = 0;
                        }
                    }

                }
            }
            
            if (gameState == GameState.END)
            {
                Y += 2;
                if ( Y >= 768 )
                {
                    gameState = GameState.LOSE ;
                }
            }
            if ( !isRight) IMAGE_LEFT.draw(X,Y);
            else IMAGE_RIGHT.draw(X, Y);
        }
    }
    private class EndFlag extends Objects
    {
        public EndFlag(Integer _X, Integer _Y) {
            super(_X, _Y); 
        }

    }
    
    private enum GameState {
        START, PLAY, END, WIN, LOSE
    }

    private enum GameRound {
        ONE, TWO, THREE
    }
    private class Screen
    {
        private Screen(Properties _game_props, Properties _message_props)
        {
            message_props = _message_props ;
            game_props = _game_props ;

            BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));

            FONT_TITLE       = new Font(game_props.getProperty("font"), 
                                        Integer.parseInt(game_props.getProperty("title.fontSize")));
            FONT_MSG       = new Font(game_props.getProperty("font"), 
                                        Integer.parseInt(game_props.getProperty("message.fontSize")));
            FONT_INSTRUCT       = new Font(game_props.getProperty("font"), 
                                        Integer.parseInt(game_props.getProperty("instruction.fontSize")));                         

        }
        Properties message_props ; 
        Properties game_props ;
        private final Image BACKGROUND_IMAGE ;
        private final Font  FONT_TITLE ;
        private final Font  FONT_MSG;
        private final Font  FONT_INSTRUCT;

        private void Draw(Input input) 
        {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            if (input.wasPressed(Keys.ESCAPE)) 
            {
                Window.close();
            }

            switch (gameState) {
            case START:
            {
                FONT_TITLE.drawString(message_props.getProperty("title"), 
                                        Double.parseDouble(game_props.getProperty("title.x")),
                                        Double.parseDouble(game_props.getProperty("title.y")));

                FONT_INSTRUCT.drawString(message_props.getProperty("instruction"), 
                                         Window.getWidth()/2 - FONT_INSTRUCT.getWidth(message_props.getProperty("instruction"))/2,
                                         Double.parseDouble(game_props.getProperty("instruction.y")));
                
                if ( input.wasPressed(Keys.NUM_1)) 
                {
                    gameRound = GameRound.ONE;
                    newGame(game_props,message_props);
                    gameState = GameState.PLAY;
                }
                else if ( input.wasPressed(Keys.NUM_2)) 
                {
                    gameRound = GameRound.TWO;
                    newGame(game_props,message_props);
                    gameState = GameState.PLAY;
                }
                else if ( input.wasPressed(Keys.NUM_3)) 
                {
                    gameRound = GameRound.THREE;
                    newGame(game_props,message_props);
                    gameState = GameState.PLAY;
                }
               
                break ; 
            }
            case PLAY:
            {
                score.Draw();
                obm.Draw(input);  
                break;
            }
            case WIN:
            {
                FONT_MSG.drawString(message_props.getProperty("gameWon"), 
                                         Window.getWidth()/2 - FONT_INSTRUCT.getWidth(message_props.getProperty("gameWon"))/2,
                                         Double.parseDouble(game_props.getProperty("message.y")));
                if ( input.wasPressed(Keys.SPACE)) 
                {
                    newGame(game_props, message_props);
                }
                break;
            }
            case LOSE:
            {
                FONT_MSG.drawString(message_props.getProperty("gameOver"), 
                                         Window.getWidth()/2 - FONT_INSTRUCT.getWidth(message_props.getProperty("gameOver"))/2,
                                         Double.parseDouble(game_props.getProperty("message.y")));
                if ( input.wasPressed(Keys.SPACE)) 
                {
                    newGame(game_props, message_props);
                }
                break;
            }
            case END:
            {
                score.Draw();
                obm.Draw(input);  
                break;
            }
            default:
                break;
            }
        }
    }
    private class Score
    {
        private Score(Properties _game_props, Properties _message_props)
        {
            message_props = _message_props ;
            game_props = _game_props ;

            FONT_SCORE  = new Font(game_props.getProperty("font"), 
                    Integer.parseInt(game_props.getProperty("score.fontSize")));
                                        
            FONT_PLAYER_HEALTH       = new Font(game_props.getProperty("font"), 
                                        Integer.parseInt(game_props.getProperty("playerHealth.fontSize")));
            
            FONT_BOSS_HEALTH       = new Font(game_props.getProperty("font"), 
                                        Integer.parseInt(game_props.getProperty("enemyBossHealth.fontSize")));  

            playerHealth = (int) Double.parseDouble(game_props.getProperty("gameObjects.player.health")) * 100;
            bossHealth   = (int) Double.parseDouble(game_props.getProperty("gameObjects.enemyBoss.health")) * 100;  
        }
        
        Properties message_props ; 
        Properties game_props ;
        private final Font  FONT_PLAYER_HEALTH;
        private final Font  FONT_BOSS_HEALTH;
        private final Font  FONT_SCORE;

        private Integer currentScore = 0; 
        private Integer playerHealth = 0;
        private Integer bossHealth = 0;

        private void showScore()
        {
            FONT_SCORE.drawString(message_props.getProperty("score") + " " + currentScore.toString(), 
                                    Double.parseDouble(game_props.getProperty("score.x")),
                                    Double.parseDouble(game_props.getProperty("score.y")));
        }
        private void showHealth()
        {
            
            FONT_PLAYER_HEALTH.drawString(message_props.getProperty("health") + " " + playerHealth.toString(), 
                                    Double.parseDouble(game_props.getProperty("playerHealth.x")),
                                    Double.parseDouble(game_props.getProperty("playerHealth.y")));
                                    
            if (gameRound == GameRound.THREE)
            {
                DrawOptions options = new DrawOptions(); 
                options.setBlendColour(255, 0, 0);
                FONT_BOSS_HEALTH.drawString(message_props.getProperty("health") + " " + bossHealth.toString(), 
                                    Double.parseDouble(game_props.getProperty("enemyBossHealth.x")),
                                    Double.parseDouble(game_props.getProperty("enemyBossHealth.y")),options);
            }
        }
        private void updateScore(Integer score)
        {
            currentScore += score ; 
        }
        private void updatePlayerHealth(int damage )
        {
            playerHealth -= damage;
            if (playerHealth <= 0) {
                playerHealth = 0;
                gameState = GameState.END;
            }
        }
        private void updateBossHealth(int damage )
        {
            if ( bossHealth > 0 )
                bossHealth -= damage ; 
        }
        private void Draw()
        {
            showScore() ; 
            showHealth() ;
        }
    }
    
    private class ObjectsManager
    {
        private void readPlatform(Properties game_props,String levelFile)
        {
            List<Integer[][]>  input_Platform = IOUtils.readCsv(game_props.getProperty(levelFile),"PLATFORM");
            Integer[][] _platform = input_Platform.get(0) ; 
            platform = new Platform(_platform[0][0],_platform[0][1]);
            platform.speed = Integer.parseInt(game_props.getProperty("gameObjects.platform.speed"));

            platform.IMAGE = new Image(game_props.getProperty("gameObjects.platform.image")) ; 
        }
        private void readPlayer(Properties game_props,String levelFile)
        {
            List<Integer[][]>  input_Player = IOUtils.readCsv(game_props.getProperty(levelFile),"PLAYER");
            Integer[][] _player = input_Player.get(0) ; 
            player = new Player(_player[0][0],_player[0][1]);
            player.radius = Double.parseDouble(game_props.getProperty("gameObjects.player.radius"));
            
            player.IMAGE_RIGHT = new Image(game_props.getProperty("gameObjects.player.imageRight")) ; 
            player.IMAGE_LEFT = new Image(game_props.getProperty("gameObjects.player.imageLeft")) ; 
        }
        private void readEnemies(Properties game_props,String levelFile)
        {
            Double ENEMY_RADIUS = Double.parseDouble(game_props.getProperty("gameObjects.enemy.radius"));
            Integer ENEMY_DAMAGE = (int) (Double.parseDouble(game_props.getProperty("gameObjects.enemy.damageSize")) * 100);
            Integer ENEMY_SPEED = Integer.parseInt(game_props.getProperty("gameObjects.enemy.speed"));
            Integer ENEMY_RANDOM_SPEED = Integer.parseInt(game_props.getProperty("gameObjects.enemy.randomSpeed")) ; 
            Integer ENEMY_MAX_RANGE = Integer.parseInt(game_props.getProperty("gameObjects.enemy.maxRandomDisplacementX")) ; 
            Image ENEMY_IMAGE = new Image(game_props.getProperty("gameObjects.enemy.image")) ; 
            List<Integer[][]>  input_Enemy = IOUtils.readCsv(game_props.getProperty(levelFile),"ENEMY");
            for (Integer[][] _enemy : input_Enemy) 
            {
                enemies.add(new Enemy(_enemy[0][0], _enemy[0][1],ENEMY_IMAGE,ENEMY_SPEED,ENEMY_RADIUS,ENEMY_DAMAGE,ENEMY_RANDOM_SPEED,ENEMY_MAX_RANGE)) ;
            }
        }
        private void readFlyPlatforms(Properties game_props,String levelFile)
        {
            Integer FLYP_SPEED = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.speed"));
            Integer FLYP_RANDOM_SPEED = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.randomSpeed")) ; 
            Integer FLYP_MAX_RANGE = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX")) ; 
            Integer FLYP_HALF_LENGTH = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.halfLength")) ; 
            Integer FLYP_HALF_HEIGHT = Integer.parseInt(game_props.getProperty("gameObjects.flyingPlatform.halfHeight")) ; 
            Image FLYP_IMAGE = new Image(game_props.getProperty("gameObjects.flyingPlatform.image")) ; 
            List<Integer[][]>  input_FLYP = IOUtils.readCsv(game_props.getProperty(levelFile),"FLYING_PLATFORM");
            flyPlatforms = new ArrayList<>(); 
            for (Integer[][] _flyP : input_FLYP) 
            {
                flyPlatforms.add(new Fly_Platform(_flyP[0][0], _flyP[0][1],FLYP_IMAGE,FLYP_SPEED,FLYP_HALF_LENGTH,FLYP_HALF_HEIGHT,FLYP_RANDOM_SPEED,FLYP_MAX_RANGE)) ;
            }
        }
        private void readDoubleScore(Properties game_props,String levelFile)
        {
            double RADIUS = Double.parseDouble(game_props.getProperty("gameObjects.doubleScore.radius"));
            Integer SPEED = Integer.parseInt(game_props.getProperty("gameObjects.doubleScore.speed"));
            Integer MAXFRAME = Integer.parseInt(game_props.getProperty("gameObjects.doubleScore.maxFrames"));

            Image IMAGE = new Image(game_props.getProperty("gameObjects.doubleScore.image")) ; 
            List<Integer[][]>  input_dScore = IOUtils.readCsv(game_props.getProperty(levelFile),"DOUBLE_SCORE");
            dScores = new ArrayList<>();
            for (Integer[][] _dScore : input_dScore) 
            {
                dScores.add(new doubleScore(_dScore[0][0], _dScore[0][1],IMAGE,SPEED,RADIUS,MAXFRAME)) ;
            }
        }
        private void readInvinciblePower(Properties game_props,String levelFile)
        {
            double RADIUS = Double.parseDouble(game_props.getProperty("gameObjects.invinciblePower.radius"));
            Integer SPEED = Integer.parseInt(game_props.getProperty("gameObjects.invinciblePower.speed"));
            Integer MAXFRAME = Integer.parseInt(game_props.getProperty("gameObjects.invinciblePower.maxFrames"));

            Image IMAGE = new Image(game_props.getProperty("gameObjects.invinciblePower.image")) ; 
            List<Integer[][]>  input_InPower = IOUtils.readCsv(game_props.getProperty(levelFile),"INVINCIBLE_POWER");
            inPowers = new ArrayList<>();
            for (Integer[][] _inpower : input_InPower) 
            {
                inPowers.add(new invinciblePower(_inpower[0][0], _inpower[0][1],IMAGE,SPEED,RADIUS,MAXFRAME)) ;
            }
        }
        private void readBoss(Properties game_props,String levelFile)
        {
            List<Integer[][]>  input_Boss = IOUtils.readCsv(game_props.getProperty(levelFile),"ENEMY_BOSS");
            Integer[][] _boss = input_Boss.get(0) ; 
            boss = new Boss(_boss[0][0],_boss[0][1]);
            boss.radius = Double.parseDouble(game_props.getProperty("gameObjects.enemyBoss.radius"));
            boss.activedRadius = Double.parseDouble(game_props.getProperty("gameObjects.enemyBoss.activationRadius"));

            boss.IMAGE = new Image(game_props.getProperty("gameObjects.enemyBoss.image")) ;  
        }
        private void readCoins(Properties game_props,String levelFile)
        {
            double COIN_RADIUS = Double.parseDouble(game_props.getProperty("gameObjects.coin.radius"));
            Integer COIN_SPEED = Integer.parseInt(game_props.getProperty("gameObjects.coin.speed"));
            Integer COIN_VALUE = Integer.parseInt(game_props.getProperty("gameObjects.coin.value"));

            Image COIN_IMAGE = new Image(game_props.getProperty("gameObjects.coin.image"));
            List<Integer[][]> input_Coin = IOUtils.readCsv(game_props.getProperty(levelFile), "COIN");
            for (Integer[][] _coin : input_Coin) {
                coins.add(new Coin(_coin[0][0], _coin[0][1], COIN_IMAGE, COIN_SPEED, COIN_RADIUS, COIN_VALUE));
            }
        }
        private void readFireball(Properties game_props,String levelFile)
        {
            double RADIUS = Double.parseDouble(game_props.getProperty("gameObjects.fireball.radius"));
            Integer DAMAGE = (int) (Double.parseDouble(game_props.getProperty("gameObjects.fireball.damageSize")) * 100);
            Integer SPEED = Integer.parseInt(game_props.getProperty("gameObjects.fireball.speed"));

            Image IMAGE = new Image(game_props.getProperty("gameObjects.fireball.image")) ; 
            fireballs = new ArrayList<>();
            fireballInit = new fireball(0, 0, IMAGE, SPEED, RADIUS, DAMAGE, false);
        }
        private void readFlag(Properties game_props,String levelFile)
        {
            List<Integer[][]>  input_EndFlag= IOUtils.readCsv(game_props.getProperty(levelFile),"END_FLAG");
            Integer[][] _endFlag = input_EndFlag.get(0) ; 
            endFlag = new EndFlag(_endFlag[0][0],_endFlag[0][1]);
            endFlag.speed = Integer.parseInt(game_props.getProperty("gameObjects.endFlag.speed"));
            endFlag.radius = Double.parseDouble(game_props.getProperty("gameObjects.endFlag.radius"));

            endFlag.IMAGE = new Image(game_props.getProperty("gameObjects.endFlag.image")) ; 
        }
        private ObjectsManager(Properties game_props)
        {
            String levelFile = "";
            if (gameRound == GameRound.ONE)
                levelFile = "level1File";
            else if (gameRound == GameRound.TWO)
                levelFile = "level2File";
            else if (gameRound == GameRound.THREE)
                levelFile = "level3File";

            readPlatform(game_props,levelFile);
            readPlayer(game_props,levelFile);
            readEnemies(game_props,levelFile);
            readCoins(game_props,levelFile);
            readFlag(game_props,levelFile);
            if (gameRound != GameRound.ONE)
            {
                readFlyPlatforms(game_props,levelFile);
                readDoubleScore(game_props,levelFile);
                readInvinciblePower(game_props,levelFile);
            }
            if (gameRound == GameRound.THREE)
            {
                readBoss(game_props, levelFile);
                readFireball(game_props, levelFile);
            }
        }
        
        private List<Enemy> enemies = new ArrayList<>();
        private List<Coin> coins = new ArrayList<>(); 
        private Platform platform = null ; 
        private Player player = null ; 
        private EndFlag endFlag = null;

        private Boss boss = null;
        private List<Fly_Platform> flyPlatforms = null;
        private List<doubleScore> dScores = null;
        private List<invinciblePower> inPowers = null;
        private List<fireball> fireballs = null;
        fireball fireballInit = null; 

        public void DrawCoin(Input input)
        {
            for( Coin coin : coins)
            {
                if ( coin.isScored) coin.Y -= 10 ; 
                coin.Draw(input);
            }
        }
        public void DrawEnemy(Input input)
        {
            for( Enemy _enemy : enemies)
            _enemy.Draw(input);
        }
        public void DrawFlyPlatform(Input input)
        {
            for( Fly_Platform _flyP : flyPlatforms)
                _flyP.Draw(input);
        }
        public void DrawDScore(Input input)
        {
            for( doubleScore _dScore : dScores)
            {
                if ( _dScore.isScored) _dScore.Y -= 10 ; 
                    _dScore.Draw(input);
            }
        }
        public void DrawInPower(Input input)
        {
            for (invinciblePower _Power : inPowers) {
                if (_Power.isScored)
                    _Power.Y -= 10;
                _Power.Draw(input);
            }
        }
        public void DrawFireBall(Input input)
        {
            if ( fireballs != null)
            {
                for( fireball _ball: fireballs)
                    if ( _ball.isDamaged == false)
                    {
                        if (_ball.isBossDame == true) {
                            double curDis = player.radius + _ball.radius;
                            double dis = _ball.Calculate(_ball.X, _ball.Y, player.X, player.Y);
                            if (dis <= curDis - 15) {
                                if (player.isPowered == 0)
                                {
                                    _ball.isDamaged = true;
                                    _ball.X = -1;
                                    _ball.Y = -1;
                                    score.updatePlayerHealth(_ball.damage);
                                }
                            }
                        }
                        if (_ball.isBossDame == false) {
                            double curDis = boss.radius + _ball.radius;
                            double dis = _ball.Calculate(_ball.X, _ball.Y, boss.X, boss.Y);
                            if (dis <= curDis ) {
                                _ball.isDamaged = true;
                                _ball.X = -1;
                                _ball.Y = -1;
                                score.updateBossHealth(_ball.damage);
                            }
                        }

                    }
                for( fireball _fball : fireballs)
                {
                    _fball.Draw(input);
                }
            }
        }
        public void Draw(Input input) 
        {
            platform.Draw(input);
            endFlag.Draw(input);
            DrawCoin(input);
            DrawEnemy(input);
            if (flyPlatforms != null)
                DrawFlyPlatform(input);
            if (dScores != null)
                DrawDScore(input);
            if (inPowers != null)
                DrawInPower(input);
            if (boss != null)
                boss.Draw(input);
            if (fireballs != null)
                DrawFireBall(input);

            player.Draw(input);
            
        }
    }
    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        screen.Draw(input);
    }

}
