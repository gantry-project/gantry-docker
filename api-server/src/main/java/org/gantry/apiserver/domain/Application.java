package org.gantry.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class Application extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private long id;

    @Column(nullable = false)
    private String title;

    @OneToOne(mappedBy = "application", orphanRemoval = true)
    private Container container;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @Builder
    public Application(long id, String title, Container container, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.container = container;
    }
}
