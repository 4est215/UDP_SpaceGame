//this will do the math for the game and give the server the results to send to the clients

import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends WindowAdapter {
    static ArrayList<GameEntity> entityList;
    static Server server; 
    static Client client;
    
    public static void main(String[] args){
        int a = JOptionPane.showConfirmDialog(null, "Start server?");
        if (a == 0){
            try {
                int port = Integer.parseInt(JOptionPane.showInputDialog("Enter port"));
                server = new Server(new DatagramSocket(port)); 
            } catch (HeadlessException | SocketException e) {
                e.printStackTrace();
            }
        }

        int b = JOptionPane.showConfirmDialog(null, "Start Client?");
        if (b == 0){
            try {
                String userName = JOptionPane.showInputDialog("Enter Username");
                InetAddress address = InetAddress.getByName(JOptionPane.showInputDialog("Enter IP Address"));
                int port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port"));
                // TODO start the Client for this machine 
                client = new Client(userName, address, port);

            } catch (HeadlessException | UnknownHostException e) {
                e.printStackTrace();
            }

            //set up the game
            new GameFrame(new GamePanel()); //dont think this will work
        }
        
    }

}
