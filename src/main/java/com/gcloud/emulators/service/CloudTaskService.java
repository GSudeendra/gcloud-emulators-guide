package com.gcloud.emulators.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.cloud.tasks.v2.CloudTasksClient;
import com.google.cloud.tasks.v2.CloudTasksSettings;
import com.google.cloud.tasks.v2.HttpMethod;
import com.google.cloud.tasks.v2.HttpRequest;
import com.google.cloud.tasks.v2.LocationName;
import com.google.cloud.tasks.v2.OidcToken;
import com.google.cloud.tasks.v2.Queue;
import com.google.cloud.tasks.v2.QueueName;
import com.google.cloud.tasks.v2.Task;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class CloudTaskService {

    private static String endpoint = "localhost:8123";

    public static void createQueue(String projectId, String locationId, String queueId) {
        try (var client = CloudTasksClient.create(CloudTasksSettings.newBuilder().setCredentialsProvider(NoCredentialsProvider.create())
                .setTransportChannelProvider(
                        InstantiatingGrpcChannelProvider.newBuilder()
                                .setEndpoint(endpoint)
                                .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                                .build()).build())) {

            String parent = LocationName.of(projectId, locationId).toString();
            String queuePath = QueueName.of(projectId, locationId, queueId).toString();

            Queue queue = client.createQueue(parent, Queue.newBuilder().setName(queuePath).build());
            System.out.println("Queue created: " + queue.getName());
        } catch (IOException e) {
            System.out.println("Error creating queue: " + e.getMessage());
        }
    }

    public static void createCustomTask(String projectId, String locationId, String queueId, HttpMethod httpMethod) {

        try (var client = CloudTasksClient.create(CloudTasksSettings.newBuilder().setCredentialsProvider(NoCredentialsProvider.create())
                .setTransportChannelProvider(
                        InstantiatingGrpcChannelProvider.newBuilder()
                                .setEndpoint(endpoint)
                                .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                                .build()).build())) {


            Task task =
                    Task.newBuilder().setHttpRequest(HttpRequest.newBuilder()
                            .setBody(ByteString.copyFrom("test", StandardCharsets.UTF_8))
                            .setHttpMethod(httpMethod).setUrl("http://host.docker.internal:8080/api/hello").setOidcToken(OidcToken.newBuilder()
                                    .setServiceAccountEmail("test@gmail.com")
                                    .setAudience("http://host.docker.internal:8080")).build()).build();
            client.createTask(QueueName.newBuilder().setLocation(locationId)
                    .setQueue(queueId)
                    .setProject(projectId)
                    .build(), task);

            System.out.println("task created: " + task.getName());

        } catch (IOException e) {
            System.out.println("error task creating: " + e.getMessage());
        }
    }

}
