package org.gantry.apiserver.config.docker;

import com.github.dockerjava.api.DockerClient;
import org.gantry.apiserver.domain.docker.DockerClientFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DockerClientFactoryTest {

    @Test
    void getDockerClientWithNonNull() {
        DockerClientFactory factory = new DockerClientFactory(() -> "tcp://localhost:1234");
        DockerClient client = factory.getInstance();
        assertThat(client).isNotNull();
    }

    @Test
    void newDockerClient() {
        DockerClientFactory factory = new DockerClientFactory(() -> "tcp://localhost:1234");
        DockerClient firstClient = factory.getInstance();

        DockerClient secondClient = factory.refreshAndGetInstanceWith(() -> "tcp://remotehost:1234");
        assertThat(secondClient).isNotNull();
        assertThat(secondClient).isNotEqualTo(firstClient);

        DockerClient thirdClient = factory.getInstance();
        assertThat(thirdClient).isEqualTo(secondClient);
    }
}