package org.gantry.apiserver.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
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

    @Builder
    public Container(String id, Application application) {
        this.id = id;
        this.application = application;
    }
}
