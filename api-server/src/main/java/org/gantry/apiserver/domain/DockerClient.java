package org.gantry.apiserver.domain;

import org.springframework.stereotype.Component;

// TODO: Not implemented yet.
@Component
public class DockerClient {
    public long run(Application application) {
        return 0;
    }

    public void stop(Long containerId) {
    }

    public void remove(Long containerId) {
    }

    public ContainerInfo getStatus(long containerId) {
        return null;
    }
}
