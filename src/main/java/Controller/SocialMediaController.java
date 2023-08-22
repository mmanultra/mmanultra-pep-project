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
        app.post("/messages", this::CreateMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);
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

    private void CreateMessageHandler(Context context) throws JsonProcessingException {
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

    public void getMessageByIDHandler(Context ctx){
        System.out.println("Hello");
        String id = ctx.pathParam("message_id");
        int messageID = Integer.parseInt(id);
        Message message = messageService.getMessageByID(messageID);
        if(message != null)
            ctx.json(message);
        else
            System.out.println("No Message Found");
    }

    public void deleteMessageByIDHandler(Context ctx){
        System.out.println("Hello");
        String id = ctx.pathParam("message_id");
        int messageID = Integer.parseInt(id);
        Message message = messageService.deleteMessageByID(messageID);
        if(message != null)
            ctx.json(message);
        else
            System.out.println("No Message Found");
    }

    private void updateMessageByIDHandler(Context context) throws JsonProcessingException {
        String id = context.pathParam("message_id");
        int messageID = Integer.parseInt(id);
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        String message_text = message.getMessage_text();
        Message update = messageService.updateMessage(message_text, messageID);
        if(update!=null){
            context.status(200);
            System.out.println("Success");
            context.json(mapper.writeValueAsString(update));
        }else{
            System.out.println("Failure");
            context.status(400);
        }
    }

    public void getMessagesByUserHandler(Context ctx){
        System.out.println("Hello");
        String id = ctx.pathParam("account_id");
        int posted_by = Integer.parseInt(id);
        List<Message> messages = messageService.getMessagesByUser(posted_by);
        if(messages != null)
            ctx.json(messages);
        else
            System.out.println("No Messages Found");
    }
}