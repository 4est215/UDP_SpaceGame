import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class PlayerShip extends MobileEntity{
    /* contains...
    protected static int idCount = 0;
    protected int entityID;
    protected float angleInDegrees;
    protected double xPosition;
    protected double yPosition;
    protected int screenXPosition;
    protected int screenYPosition;
    protected Faction faction;
    protected String filePath;
    protected Image sprite;

    protected double xVelocity;
    protected double yVelocity;
    protected double angularThrust;
    protected double cardinalThrust;
    protected double driveSpeed;
    protected double angularVelocity;//clockwise is negative
    */

    private boolean dead = false;
    //buttons
    private ArrayList<Boolean> inputList = new ArrayList<>(6);
    // private boolean torqueClockwise = false;
    // private boolean torqueCounterCl = false;
    // private boolean forwardForce = false;

    public ArrayList<Boolean> getInputList() {
        return inputList;
    }

    public PlayerShip(int x, int y, int width, int height, float angleInDegrees, 
        double xPosition, double yPosition, Faction faction, GameSprite sprite) {

        super(x, y, width, height, angleInDegrees, xPosition, yPosition, faction, sprite);
        
        angularThrust = 0.05d;
        cardinalThrust = 0.1d; //TODO add cardinal controls
        driveSpeed = 0.1d;


        Main.entityList.add(this);
    }

    //TODO needs to be replaced with inputs recived from clients
    // public void keyPressed(KeyEvent e){
    //     if(!dead){//checks if alive
    //         if(e.getKeyCode() == KeyEvent.VK_D){
    //             torqueClockwise = true;
    //         }
    //         if(e.getKeyCode() == KeyEvent.VK_A){
    //             torqueCounterCl = true;
    //         }
    //         if(e.getKeyCode() == KeyEvent.VK_W){//going forward
    //             //add vector math for cordinates
    //             forwardForce = true;
    //             // xVelocity += speed * Math.sin(Math.toRadians(angleInDegrees));
    //             // yVelocity -= speed * Math.cos(Math.toRadians(angleInDegrees));
    //         }
    //     }
    // }
    // public void keyReleased(KeyEvent e){
    //     if(!dead){//checks if alive first
    //         if(e.getKeyCode() == KeyEvent.VK_D){
    //             torqueClockwise = false;
    //         }
    //         if(e.getKeyCode() == KeyEvent.VK_A){
    //             torqueCounterCl = false;
    //         }
    //         if(e.getKeyCode() == KeyEvent.VK_W){//going forward
    //             //add vector math for cordinates
    //             forwardForce = false;
    //             // xVelocity += speed * Math.sin(Math.toRadians(angleInDegrees));
    //             // yVelocity -= speed * Math.cos(Math.toRadians(angleInDegrees));
    //         }
    //     }
    // }


    // private Boolean[] pressedKeys = {forwardForce, leftForce, rightForce, backwardForce, clockwiseTorque, counterClockwiseTorque};
    @Override
    public void move() {
        //rotaion
        if(inputList.get(4)) //clockwiseForce
            angularVelocity -= angularThrust;
        if(inputList.get(5)) //counterClockwiseForce
            angularVelocity += angularThrust;

        angleInDegrees += angularVelocity;
        if(angleInDegrees <= -360)
            angleInDegrees += 360;
        if(angleInDegrees >= 360)
            angleInDegrees -= 360;

        //cardinal FIXME this all needs testing and probaly fixing
        if(inputList.get(0)){ //forwardForce
            xVelocity += cardinalThrust * Math.cos(Math.toRadians(angleInDegrees));
            yVelocity += cardinalThrust * Math.sin(Math.toRadians(angleInDegrees)); //with this as += the coordinates are right but the display is wrong, perhaps its how reletive position is calculated
        }
        if(inputList.get(1)){ //leftForce
            xVelocity -= cardinalThrust * Math.sin(Math.toRadians(angleInDegrees));
            yVelocity += cardinalThrust * Math.cos(Math.toRadians(angleInDegrees));
        }
        if(inputList.get(2)){ //rightForce
            xVelocity += cardinalThrust * Math.sin(Math.toRadians(angleInDegrees));
            yVelocity -= cardinalThrust * Math.cos(Math.toRadians(angleInDegrees));
        }
        if(inputList.get(3)){ //backwardForce
            xVelocity -= cardinalThrust * Math.cos(Math.toRadians(angleInDegrees));
            yVelocity -= cardinalThrust * Math.sin(Math.toRadians(angleInDegrees)); //with this as += the coordinates are right but the display is wrong, perhaps its how reletive position is calculated
        }
        xPosition += xVelocity;
        yPosition += yVelocity;

        if(dead){ //dont know how this is intended to work in this context
            inputList.set(4, false);
            inputList.set(5, false);
            inputList.set(0, false);
            angularVelocity = 11000;
        }
        
    }
    
    //TODO implement collisions for moving objects, may need hitbox field
    @Override
    public void checkCollisions() {

    }


    //idk if we need this server side since theres no visual
    // @Override
    // public void draw(Graphics g) {
    //     Graphics2D g2d = (Graphics2D)g;
    //     AffineTransform tr = new AffineTransform();
    //     tr.translate((MyPanel.GAME_WIDTH/2) - (width/2) , (MyPanel.GAME_HEIGHT/2) -(height/2));
    //     tr.rotate(Math.toRadians(-angleInDegrees), sprite.getWidth(null)/2, sprite.getHeight(null)/2);
    //     g2d.drawImage(sprite, tr, null);
    // }
    // public void draw(Graphics g){}

    
}