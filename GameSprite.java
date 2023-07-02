import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GameSprite {
    // static arrayList may be better for sending the id of the sprite so the client knows what to display
    static ArrayList<GameSprite> spriteList = new ArrayList<>();
    private String spriteName; 
    private Image[] idleImages;
    private int idleImageCount;
    // private double angleInDegrees;


    public GameSprite(String spriteName,Image[] images){
        this.spriteName = spriteName;
        idleImages = images;
        idleImageCount = 0;
        spriteList.add(this);
    }

    public Image getNextImage(){
        return idleImages[idleImageCount++];
    }

    @Override
    public String toString(){
        return spriteName;
    }

    public void drawSprite(Graphics g, double angleInDegrees, double reletiveXPos, double reletiveYPos) {
        //TODO should add the spite to the screen, will exapnd on later for differnt animations
        Image currentImage = getNextImage();
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform transform = new AffineTransform();

        //these postions will be calculated in client using info recived from server/clientHandler
        transform.rotate(Math.toRadians(angleInDegrees), reletiveXPos + (currentImage.getWidth(null)/2), reletiveYPos + (currentImage.getHeight(null)/2)); 
        
        transform.translate(reletiveXPos, reletiveYPos);

        g2d.drawImage(currentImage, transform, null);
    }
}
