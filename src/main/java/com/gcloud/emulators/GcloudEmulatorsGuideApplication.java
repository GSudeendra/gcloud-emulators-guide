package com.gcloud.emulators;

import com.google.cloud.tasks.v2.HttpMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.gcloud.emulators.service.CloudTaskService.createCustomTask;
import static com.gcloud.emulators.service.CloudTaskService.createQueue;

@SpringBootApplication
public class GcloudEmulatorsGuideApplication {

    public static void main(String[] args) {
        String projectId = "dev";
        String locationId = "asia-east1";
        String queueId = "testproject2";

        SpringApplication.run(GcloudEmulatorsGuideApplication.class, args);
        //Already created a queue in docker-compose file , just to show how to create using code
        //createQueue(projectId, locationId, queueId);
        createCustomTask(projectId, locationId, queueId, HttpMethod.GET);
    }
}
