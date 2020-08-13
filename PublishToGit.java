package org.example.functions;

import java.io.IOException;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.kohsuke.github.*;



/**
 * Azure Functions with HTTP Trigger.
 */
public class PublishToGit {
    /**
     * This function listens at endpoint "/api/publishToGit". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/publishToGit
     * 2. curl {your host}/api/publishToGit?name=HTTP%20Query
     */
    @FunctionName("publishToGit")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("blob_name");
        String name = request.getBody().orElse(query);
        name = "before try";
        try {
            name="entered try";
            //GitHub github = new GitHubBuilder().withOAuthToken("8a8132a30eae7e315f0d2729bebdd139328f31a3", "divyankitm19").build();
            GitHub github = new GitHubBuilder().withPassword("alok1203","hack@1203").build();
            name="after line1";
            GHRepository gitRepo = github.getRepository("alok1203/hackathon");

            // GHRepository gitRepo = github.getRepository("divyankitm19/testrepository");
            name = "after line2";
            GHContentBuilder content = gitRepo.createContent().branch("master");
            name = "after line3";
            content.content("testing again").path("files/test2.txt").branch("master").message("committing to master again").commit();
            name= "exit try";
        }catch (IOException e) {
            System.out.print(e.getMessage());
            name="ranu";
        }finally {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
//            if (name == null) {
//                return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
//            } else {
//                return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
//            }



    }
}
