/*
 * class GUI
 * Interface of chat program
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import org.jdesktop.xswingx.*;

import javax.swing.*;
import java.awt.GridLayout;

public class GUI extends JFrame implements ActionListener {

    private Session _chatSession;
    private User _user;
    private User _partner;

    private GridLayout g = new GridLayout(3,4);
    private JFrame frame = new JFrame();

    private JTextArea display = new JTextArea();
    private JButton send = new JButton("Send");
    private JTextArea writeHere = new JTextArea();
    private String message;

    public GUI() { }

    // GUI is created by Session
    public GUI (Session chatSession, User user, User partner) {
        _chatSession = chatSession;
        _user = user;
        _partner = partner;
        make();
    }

    public void actionPerformed(ActionEvent event){
	message =  writeHere.getText();
	writeHere.setText("");
	if (message.trim().length() > 0) {

	    // Remove trailing whitespace
	    String msgContent = message.replaceFirst("\\s+$", ""); 

	    // Prepare metadata for Messge object
	    int senderID = _user.getID();
	    int receiverID = _partner.getID();
	    long timestamp = System.currentTimeMillis() / 1000L;

	    // Create new Message instance
	    Message newMessage = new Message(
			 msgContent, senderID, receiverID, senderID);

	    // Pass it on to Session, which will pass it on to Server
	    _chatSession.sendMessage(newMessage);

	    // Add user's message to GUI
	    display.append("You: "+ newMessage + "\n");
	}
    }

    // Called by Session when it is notified by Server of new message
    public void displayMessage(Message msg) {
        display.append("Partner: "+ msg + "\n");
    }

    private void make(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo( null );
        frame.setLayout(g);

        frame.getContentPane().add(new JScrollPane(display));
            display.setLineWrap(true);
            display.setWrapStyleWord(true);
        display.setEditable(false);
        
        frame.getContentPane().add(new JScrollPane(writeHere));
            writeHere.setLineWrap(true);
            writeHere.setWrapStyleWord(true);

        frame.getContentPane().add(send);
        PromptSupport.setPrompt("Type Here!",writeHere);
        send.addActionListener(this);

        frame.setVisible(true);
    }

    public String getMessage(){
        return message;
    }

    public static void main (String[] args){
        GUI testGUI = new GUI();
    }
}
