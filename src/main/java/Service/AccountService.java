package Service;

import DAO.AccountDAO;
import Model.Account;

// import java.util.List;


public class AccountService {

    public AccountDAO accountDAO;


    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account insertAccount(Account account){
        if(this.accountDAO.getAccountByUsername(account.getUsername()) == null){
            return this.accountDAO.insertAccount(account);
        }
        return null;
    }

    public Account getAccountByUsernameAndPassword(Account account){
        Account dbAccount =  accountDAO.getAccountByUsername(account.getUsername());

        if (dbAccount.getUsername().equals(account.getUsername()) && dbAccount.getPassword().equals(account.getPassword())){
            return dbAccount;
        }
        
        return null;
    }


    
}
