import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client implements Runnable{
    // fields ------------------------------------------
    private String clientName;
    private InetAddress inetAddress;
    private int port;
    private DatagramSocket datagramSocket; // may need 2 like in the clientHandler/ server
    private byte[] sendingBytes = new byte[1024];
    private byte[] receivingBytes = new byte[1024];

    static String[][] spritesToDraw;
    
    private Boolean forwardForce;
    private Boolean leftForce;
    private Boolean rightForce;
    private Boolean backwardForce;
    private Boolean clockwiseTorque;
    private Boolean counterClockwiseTorque;
    private Boolean autoStopping;

    private Boolean[] pressedKeys = {forwardForce, leftForce, rightForce, backwardForce, clockwiseTorque, counterClockwiseTorque, autoStopping};

    // constructor -----------------------------------------
    public Client(String clientName, InetAddress inetAddress, int port){
        this.clientName = clientName;
        this.inetAddress = inetAddress;
        this.port = port;

        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void run() {
        sendMessage();
        reciveFromServer();
    }

    private void sendMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //TODO send messages to server in the form of boolean list, refer to clientHandler 
                while(true){
                    long currentTime = System.currentTimeMillis();
                    while(System.currentTimeMillis() - currentTime < 250){} //waits for 250 millisecods

                    String sendingString = new String("02"+clientName);
                    for(Boolean b : pressedKeys){
                        sendingString += b; //may need casting
                    }
                    sendingBytes = sendingString.getBytes();
                    DatagramPacket packet = new DatagramPacket(sendingBytes, 0, sendingBytes.length, inetAddress, port);
                    try {
                        datagramSocket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void reciveFromServer() {
        //TODO recvieve list of sprites and their ablsoute position, then calculate reletive postion for the screen
        while(true){
            DatagramPacket packetFromServer = new DatagramPacket(receivingBytes, port, port, inetAddress, port);
            try {
                datagramSocket.receive(packetFromServer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String stringFromServer = new String(receivingBytes, 0, receivingBytes.length);
            String[] spriteArray = stringFromServer.split(":");
            spritesToDraw = new String[spriteArray.length][];
            for (int i=0; i<spriteArray.length; i++){
                spritesToDraw[i] = spriteArray[i].split(",");                
            }
        }

    }

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                forwardForce = true;
                break;
            case KeyEvent.VK_A:
                leftForce = true;
                break;
            case KeyEvent.VK_S:
                backwardForce = true;
                break;
            case KeyEvent.VK_D:
                rightForce = true;
                break;
            case KeyEvent.VK_E:
                clockwiseTorque = true;
                break;
            case KeyEvent.VK_Q:
                counterClockwiseTorque = true;
                break;
            case KeyEvent.VK_B:
                autoStopping = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                forwardForce = false;
                break;
            case KeyEvent.VK_A:
                leftForce = false;
                break;
            case KeyEvent.VK_S:
                backwardForce = false;
                break;
            case KeyEvent.VK_D:
                rightForce = false;
                break;
            case KeyEvent.VK_E:
                clockwiseTorque = false;
                break;
            case KeyEvent.VK_Q:
                counterClockwiseTorque = false;
                break;
            case KeyEvent.VK_B:
                autoStopping = true;
                break;
        }
    }
    
}
