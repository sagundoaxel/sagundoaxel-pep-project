package Service;

import DAO.MessageDAO;
import Model.Message;

import Model.Account;
import DAO.AccountDAO;

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

    
}
