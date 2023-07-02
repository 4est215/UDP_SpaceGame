import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    // fields -----------------------------------------------
    static final int GAME_WIDTH = 1600; //may change to full screen
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (5f/9f));//change for different dimensions
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private boolean running = true; // probably not nessary
    private Image image;
    private Graphics graphics;
    // private GameSprite[] sprites; //may be easier as arrayList
    private Client client;

    public GamePanel(){ //TODO add args for image, graphics, and sprites, and client
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
    }

    public void paint(Graphics g){
        //TODO figure out what this does
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    public void draw(Graphics g){
        // TODO draws all the sprites
        String[][] drawSprites = Client.spritesToDraw;
        for(String[] s : drawSprites) {
            String spriteName = s[0];
            for (GameSprite gs : GameSprite.spriteList){
                if(gs.toString().equals(spriteName)){
                    gs.drawSprite(g, Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]));
                }
            }
        }
    }

    
    // inner class ---------------------------------------
    class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            //TODO what to do when keys pressed
            client.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            // TODO same as above
            client.keyReleased(e);
        }
    }
}
