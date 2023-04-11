package org.gantry.apiserver.domain.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DockerClientFactory {

    @Value("${gantry.docker.host}")
    private String dockerHost;

    private static DockerClient dockerClient;

    private static DockerInformationProvider defaultProvider;


    public DockerClientFactory() {
        defaultProvider = () -> dockerHost;
    }

    public DockerClientFactory(DockerInformationProvider provider) {
        defaultProvider = provider;
    }

    private DockerClient createInstance(DockerInformationProvider provider){
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(provider.getDockerHost())
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        return DockerClientImpl.getInstance(config, httpClient);
    }

    public DockerClient getInstance() {
        if(dockerClient == null) {
            synchronized (this) {
                if (dockerClient == null) {
                    dockerClient = createInstance(defaultProvider);
                }
            }
        }
        return dockerClient;
    }

     public DockerClient refreshAndGetInstanceWith(DockerInformationProvider provider){
        synchronized (this) {
            dockerClient = createInstance(provider);
        }
        return dockerClient;

    }
}
