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

    public Message getMessageByID(int message_id) {
        System.out.println("Looking for message " + message_id);
        Message message = messageDAO.getMessageByID(message_id);
        return message;
    }

    public Message deleteMessageByID(int message_id) {
        System.out.println("Deleting message " + message_id);
        Message message = messageDAO.getMessageByID(message_id);
        messageDAO.deleteMessageByID(message_id);
        return message;
    }

    public Message updateMessage(String message_text, int message_id)
    {
        if(!message_text.trim().equals("")
        && message_text.length() < 255
        && messageDAO.getMessageByID(message_id) != null)
        {
            Message updatedMessage = messageDAO.updateMessage(message_text, message_id);
            System.out.println("Updating Message");
            return updatedMessage;
        }
        System.out.println("Failed to Update Message");
        return null;
    }

    public List<Message> getMessagesByUser(int posted_by) {
        List<Message> messages = messageDAO.getMessagesByUser(posted_by);
        return messages;
    }
}
