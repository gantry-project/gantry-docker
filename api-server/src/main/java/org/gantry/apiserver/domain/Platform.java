package org.gantry.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity
public class Platform extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platform_id")
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PlatformType type;

    private String url;

    private boolean active;
}
