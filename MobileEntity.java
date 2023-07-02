import java.awt.Graphics;

public abstract class MobileEntity extends GameEntity{
    protected double xVelocity;
    protected double yVelocity;
    protected double angularThrust;
    protected double cardinalThrust;
    protected double driveSpeed;
    protected double angularVelocity;//clockwise is negative

    public MobileEntity(int x, int y, int width, int height, float angleInDegrees, double xPosition, double yPosition,
            Faction faction, GameSprite sprite) {
        super(x, y, width, height, angleInDegrees, xPosition, yPosition, faction, sprite);
        
    }

    @Override
    public abstract void move();

    // @Override
    // public abstract void draw(Graphics g);

    @Override
    public abstract void checkCollisions();

    @Override
    public void tick() {
        move();
        checkCollisions();
    }
    
}