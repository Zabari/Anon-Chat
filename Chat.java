/*
 * class CHAT
 * Command center of program
*/

// For Socket
import java.net.*;

// For ObjectInputStream, ObjectOutputStream
import java.io.*;

public class Chat {

    private GUI _gui; // Interface
    private Connection _connection;
    private MessageManager _messageManager;

    private Thread _messageSender;
    private Thread _messageReceiver;
    private Thread _messageDisplayer;


    public void start() {
        createGUI();
        createMessageQueue();
        establishConnection();
        spawnThreads();
    }

    public void createGUI() {
        _gui = new GUI(this);
    }

    public void createMessageQueue() {
        _messageManager = new MessageManager();
    }

    public void establishConnection() {
        _connection = new Connection(this);
        _messageManager.setConnection(_connection);
    }

    // Called by Connection.java
    public void createThreads(ObjectOutputStream outStream, ObjectInputStream inStream) {
        _messageSender = new MessageSender(_messageManager, outStream);
        _messageReceiver = new MessageReceiver(_messageManager, inStream);
        // _messageDisplayer is Swing background worker
    }

    public void spawnThreads() {
        _messageSender.start();
        _messageReceiver.start();
        // _messageDisplayer.start();
    }

    // triggered by GUI
    public void sendMessage(String msgContent) {
        _messageManager.sendMessage(msgContent);
    }

    public static void main(String[] args) {
        Chat session = new Chat();
        session.start();
    }
}