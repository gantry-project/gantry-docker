package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
@Builder
@Data
@ToString
public class ContainerLogResponse {
    private String id;
    private String log;
}
