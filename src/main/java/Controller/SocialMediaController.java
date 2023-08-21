package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController()
    {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messageHandler);
        app.get("/messages", this::getMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        System.out.println(account.getAccount_id() + " " + account.getUsername() + " " + account.getPassword() + " controller");
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount!=null){
            context.status(200);
            System.out.println("Success");
            context.json(mapper.writeValueAsString(registeredAccount));
        }else{
            System.out.println("Failure");
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        System.out.println(account.getAccount_id() + " " + account.getUsername() + " " + account.getPassword() + " controller");
        Account loggedAccount = accountService.login(account);
        if(loggedAccount!=null){
            context.status(200);
            System.out.println("Success");
            context.json(mapper.writeValueAsString(loggedAccount));
        }else{
            System.out.println("Failure");
            context.status(401);
        }
    }

    private void messageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        System.out.println(message.getMessage_id() + " " + message.getMessage_text() + " " + message.getPosted_by() + " " + message.getTime_posted_epoch());
        Message post = messageService.postMessage(message);
        if(post!=null){
            context.status(200);
            System.out.println("Success");
            context.json(mapper.writeValueAsString(post));
        }else{
            System.out.println("Failure");
            context.status(400);
        }
    }

    public void getMessageHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

}