package Service;

import java.util.List;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public Message postMessage(Message message)
    {
        if(!message.getMessage_text().trim().equals("")
        && message.getMessage_text().length() < 255
        && messageDAO.getUserByMessage(message.getPosted_by()) != null)
        {
            Message newMessage = messageDAO.postMessage(message);
            System.out.println("Posting new Message");
            return newMessage;
        }
        System.out.println("Failed to Post Message");
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }
}
