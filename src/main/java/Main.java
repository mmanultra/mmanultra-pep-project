import Controller.SocialMediaController;
import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Util.ConnectionUtil;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        ConnectionUtil.resetTestDatabase();
        SocialMediaController controller = new SocialMediaController();
        HttpClient webClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        Javalin app = controller.startAPI();
        app.start(8080);
        System.out.println("Hello");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"testuser1\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
        try{
            HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            System.out.println("Status code " + status);
            Account actualAccount = objectMapper.readValue(response.body().toString(), Account.class);
            System.out.println(actualAccount.getAccount_id() + " " + actualAccount.getUsername() + " " + actualAccount.getPassword());
        } catch(Exception e){
            System.out.println("Error");
        }
        
        
    }
    
}
