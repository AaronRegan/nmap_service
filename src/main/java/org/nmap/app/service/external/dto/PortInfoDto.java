package org.nmap.app.service.external.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PortInfoDto {
    private String port;
    private String state;
    private String service;

    public PortInfoDto(String port, String state, String service) {
        this.port = port;
        this.state = state;
        this.service = service;
    }
}
