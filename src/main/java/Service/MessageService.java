package Service;

import DAO.MessageDAO;
import Model.Message;

import Model.Account;
import DAO.AccountDAO;

// import java.util.ArrayList;
import java.util.List;


public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
       this.messageDAO = new MessageDAO();
       this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = new AccountDAO();
    }

    public Message insertMessage(Message message){
        Account accountExists = accountDAO.getAccountById(message.getPosted_by());

        if (message.getMessage_text() != null && 
        message.getMessage_text().length() > 0 && 
        message.getMessage_text().length() <= 255 && 
        accountExists != null){
            Message dbMessage = messageDAO.insertMessage(message);
            return dbMessage;
        }
        return null;
    }

    //get all messages 
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id){
        return messageDAO.deleteMessageById(message_id);
    } 

    public Message updateMessageById(int message_id, String newMessage){
        Message messageToUpdate = messageDAO.getMessageById(message_id);
        if (newMessage != null && messageToUpdate != null && newMessage.length() > 0 && newMessage.length() <= 255){
            Message updatedMessage = messageDAO.updateMessageById(message_id, newMessage);
            return updatedMessage;
        }
        
        return null;
    }

    public List<Message> getMessagesFromUser(int user_id){
        return messageDAO.getMessagesFromUser(user_id);
    }

    
}
