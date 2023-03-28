package org.gantry.apiserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Container {
    @Id
    @Column(name = "container_id")
    private String id;
    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;
    private ContainerStatus status;
    @Builder
    public Container(String id, Application application, ContainerStatus status) {
        this.id = id;
        this.application = application;
        this.status = status;
    }
}
