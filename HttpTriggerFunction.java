package org.example.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java
     * 2. curl {your host}/api/HttpTrigger-Java?name=HTTP%20Query
     */
    @FunctionName("HttpTrigger-Java")
    @StorageAccount("AzureWebJobsStorage")
    public HttpResponseMessage blobSize(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BlobInput(name = "file",dataType = "binary",
            path = "{Query.container_name}/{Query.blob_name}")byte[] content,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String container = request.getQueryParameters().get("container_name");
       // String blob = request.getQueryParameters().get("blob_name");
        //String name = request.getBody().orElse(blob);

        //String disp = Arrays.toString(content);
        String fileContent = new String(content);
        //String name = content.toString();

        if (container == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a container and blob name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(fileContent).build();
        }
    }
}
