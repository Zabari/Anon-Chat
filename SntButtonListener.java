import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SentButtonListener extends GUI implements ActionListener{
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
}
