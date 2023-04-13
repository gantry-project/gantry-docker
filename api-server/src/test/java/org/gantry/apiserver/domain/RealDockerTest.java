package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import org.gantry.apiserver.domain.docker.DockerClientConnect;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RealDockerTest {
    @Autowired
    private DockerClientConnect dockerClientConnect;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private DockerClient dockerClient;

    private Application application;
    private Container container;

    @BeforeEach
    public void beforeEach(){
        dockerClientConnect.setDockerClient(dockerClient);
        application = Application.builder().title("hello").image("docker/getting-started").build();
        container = Container.builder().id("bean12").application(application).build();

        applicationRepository.save(application);

    }
    @Test
    public void test(){
        dockerClientConnect.run(application);
    }
    @Test
    public void process() throws InterruptedException {
        List<String> logs = dockerClientConnect.log("c34e");
        for(String log : logs){
            System.out.println(log);
        }
    }
}
