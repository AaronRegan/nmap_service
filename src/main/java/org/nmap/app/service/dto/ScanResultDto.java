package org.nmap.app.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;
import org.nmap.app.model.HostEntity;
import org.nmap.app.model.PortEntity;
import org.nmap.app.model.ScanResultEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ScanResultDto {

    private String host;
    private String ipAddress;
    private LocalDateTime timestamp;
    private List<PortInfoDto> ports;
    private List<PortChangeDto> changes;

    public ScanResultDto(ScanResultEntity scanResultEntity, HostEntity hostEntity, List<PortEntity> portEntities, List<PortChangeDto> changes) {
        this.host = hostEntity.getHostname();
        this.ipAddress = hostEntity.getIpAddress();
        this.timestamp = scanResultEntity.getTimestamp();
        this.ports = new ArrayList<>();
        this.changes = changes;

        for(PortEntity portEntity: portEntities){
            ports.add(new PortInfoDto(
                    portEntity.getPort(),
                    portEntity.getState()
            ));
        }
    }

    public ScanResultDto(ScanResultEntity scanResultEntity, HostEntity hostEntity, List<PortEntity> portEntities) {
        this.host = hostEntity.getHostname();
        this.ipAddress = hostEntity.getIpAddress();
        this.timestamp = scanResultEntity.getTimestamp();
        this.ports = new ArrayList<>();

        for(PortEntity portEntity: portEntities){
            ports.add(new PortInfoDto(
                    portEntity.getPort(),
                    portEntity.getState()
            ));
        }
    }
}
