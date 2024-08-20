package org.nmap.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class HostEntity {

    public HostEntity(){

    }

    public HostEntity(String ipAddress, String hostname) {
        this.ipAddress = ipAddress;
        this.hostname = hostname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String hostname;

    @OneToMany(mappedBy = "hostEntity", cascade = CascadeType.ALL)
    private List<ScanResultEntity> scanResultEntities;
}
