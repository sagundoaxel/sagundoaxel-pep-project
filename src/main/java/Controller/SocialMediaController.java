package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::accountRegistrationHandler);
        app.post("login", this::accountLoginHandler);
        app.post("messages", this::messageCreationHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void accountRegistrationHandler(Context ctx){
        ObjectMapper om = new ObjectMapper();
        AccountService accountService = new AccountService();
        String jsonString = ctx.body();

        try {
            Account account = om.readValue(jsonString, Account.class);

            if (account.getUsername() != "" && account.getUsername() != null && account.getPassword().length() > 4){
                Account dbAccount = accountService.insertAccount(account);
                if (dbAccount != null){
                    ctx.json(dbAccount); 
                    ctx.status(200);
                    return;
                } 
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }    
        ctx.status(400);
    }

    private void accountLoginHandler(Context ctx){
        ObjectMapper om = new ObjectMapper();
        AccountService accountService = new AccountService();
        String jsonString = ctx.body();

        try {
            Account account = om.readValue(jsonString, Account.class);

            Account dbAccount = accountService.getAccountByUsernameAndPassword(account);

            if (dbAccount != null){
                ctx.json(dbAccount); 
                ctx.status(200);
                return;
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ctx.status(401);
    }

    private void messageCreationHandler(Context ctx){
        ObjectMapper om = new ObjectMapper();
        MessageService messageService = new MessageService();
        String jsonString = ctx.body();

        try {
            Message msg = om.readValue(jsonString, Message.class);
            Message dbMsg = messageService.insertMessage(msg);
            
            if (dbMsg != null){
                ctx.json(dbMsg);
                ctx.status(200);
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ctx.status(400);
    }


}