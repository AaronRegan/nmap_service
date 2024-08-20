package org.nmap.app.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PortChangeDto {

    private String port;
    private String previousState;
    private String currentState;
    private ChangeType changeType;

    public enum ChangeType {
        ADDED, REMOVED, CHANGED
    }
}
