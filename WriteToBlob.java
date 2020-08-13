package org.example.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class WriteToBlob {
    /**
     * This function listens at endpoint "/api/writeToBlob". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/writeToBlob
     * 2. curl {your host}/api/writeToBlob?name=HTTP%20Query
     */
    @FunctionName("writeToBlob")
    @StorageAccount("AzureWebJobsStorage")
    public HttpResponseMessage copyBlobHttp(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BlobOutput(name="target",path = "{Query.container_name}/{Query.blob_name}")OutputBinding<String> out,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");


        String data = request.getBody().orElse("Didn't receive data from client");

        out.setValue(data);

        // Parse query parameter
        //String query = request.getQueryParameters().get("name");
        //String name = request.getBody().orElse(query);

        if (data == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(data).build();
        }
    }
}
