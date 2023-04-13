package org.gantry.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity
public class Container extends  BaseTimeEntity {
    @Id
    @Column(name = "container_id")
    private String id;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Transient
    @Enumerated(EnumType.STRING)
    private ContainerStatus status;
    @Transient
    private String log;

    public Container(String id, Application application) {
        this.id = id;
        this.application = application;
    }
}
