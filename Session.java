/*
 * class SESSION
 * Command center of program
*/

public class Session {
    ChatGUI sessionGUI; // GUI
    Server sessionServer; // Server
    Person user; // Person using program
    Person partner; // Interlocutor

    public void start() {
        createConnection();
        createGUI();
    }

    public int[] createConnection() {
        sessionServer = new Server(this);

        // Returns array of ints, containing IDs of users
        int[] userIDs = Server.establishConnection(); 
        user = new Person(userIDs[0]); // Create user and store ID
        partner = new Person(userIDs[1]); // Create interlocutor and store ID
    }

    public void createGUI() {
        sessionGUI = new sessionGUI(this);
    }

    // triggered by ChatGUI
    public void sendMessage(String msg) { 
        sessionServer.sendMessage(msg);
    }

    // triggered by Server
    public void displayMessage(String msg) {
        sessionGUI.displayMessage(msg);
    }

    public static void main(String[] args) {
        Session chatSession = new Session();
        chatSession.start();
    }
}