package org.nmap.app.service.external.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class NmapResponseDto {
    private String host;
    private String ipAddress;
    private String latency;
    private List<PortInfoDto> ports;

    public NmapResponseDto() {
        this.ports = new ArrayList<>();
    }
}
