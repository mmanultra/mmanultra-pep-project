package Service;

import java.util.List;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account)
    {
        if(!account.getUsername().trim().equals("") 
        && account.getPassword().length() >= 4 
        && accountDAO.getAccountByUsername(account.getUsername()) == null)
        {
            Account newAccount = accountDAO.registerAccount(account);
            System.out.println("Creating new account " + account.getAccount_id());
            return newAccount;
        }
        System.out.println("Registration failed for account");
        return null;
    }

    public Account login(Account account)
    {
        Account loggedAccount = accountDAO.login(account.getUsername(), account.getPassword());
        if(loggedAccount != null)
        {
            System.out.println("Login Successful");
            return loggedAccount;
        }
        System.out.println("Login Failed");
        return null;
    }
}
