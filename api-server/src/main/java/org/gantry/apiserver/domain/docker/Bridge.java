package org.gantry.apiserver.domain.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Bridge {

    @JsonProperty("NetworkID")
    private String networkid;
    @JsonProperty("EndpointID")
    private String endpointid;
    @JsonProperty("Gateway")
    private String gateway;
    @JsonProperty("IPAddress")
    private String ipaddress;
    @JsonProperty("IPPrefixLen")
    private int ipprefixlen;
    @JsonProperty("IPv6Gateway")
    private String ipv6gateway;
    @JsonProperty("GlobalIPv6Address")
    private String globalipv6address;
    @JsonProperty("GlobalIPv6PrefixLen")
    private int globalipv6prefixlen;
    @JsonProperty("MacAddress")
    private String macaddress;

}