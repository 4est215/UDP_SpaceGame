import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler{ // doesnt need to be runnable if the runnable server calls its methods
    
    private String clientsName;
    private PlayerShip playerControlledEntity;
    private DatagramSocket sendingSocket;
    private InetAddress clientsAddress;
    private int clientsPort;

    private byte[] sendingArray;

    //needs booleans for buttons pressed
    private Boolean forwardForce;
    private Boolean leftForce;
    private Boolean rightForce;
    private Boolean backwardForce;
    private Boolean clockwiseTorque;
    private Boolean counterClockwiseTorque;
    private Boolean autoStopping;
    //probaaly dont need array
    // private Boolean[] pressedKeys = {forwardForce, leftForce, rightForce, backwardForce, clockwiseTorque, counterClockwiseTorque};

    public GameEntity getPlayerControlledEntity(){
        return playerControlledEntity;
    }

    public ClientHandler(String clientsName, PlayerShip playerControlledEntity, InetAddress clientsAddress, int clientsPort) {
        this.clientsName = clientsName;
        this.playerControlledEntity = playerControlledEntity;
        this.clientsAddress = clientsAddress;
        this.clientsPort = clientsPort;

        try {
            sendingSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }




    

    public void setSendingArray(){ //find the nearby entities and primitvly sends the info to the client
        double playerX = playerControlledEntity.getCenterX();
        double playerY = playerControlledEntity.getCenterY();

        String stringToSend = new String();
        for(GameEntity e : Main.entityList){
            double entityX = e.getCenterX();
            double entityY = e.getCenterY();
            if(Point2D.distance(entityX, entityY, playerX, playerY) >= 2000){ //if within 2000 pixles
                stringToSend += e.getSprite().toString() + "," + entityX + "," + entityY + e.getAngleInDegrees() +":";
            }
        }

        stringToSend = stringToSend.substring(0, stringToSend.length()); //should remove extraneous colon
        sendingArray = stringToSend.getBytes();
    }


    public void sendToClient() {
        setSendingArray(); 
        try {
            sendingSocket.send(new DatagramPacket(sendingArray, sendingArray.length, clientsAddress, clientsPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInputBooleans(Boolean[] booleans) {
        ArrayList<Boolean> entityInputList = playerControlledEntity.getInputList();
        for(int i=0; i<booleans.length; i++){
            entityInputList.set(i, booleans[i]);
        }
    }

    public void clientMove(){ // TODO
        // use movement from first space game and boolean values in this

    }
    
}