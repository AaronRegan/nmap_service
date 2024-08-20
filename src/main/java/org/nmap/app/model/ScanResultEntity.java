package org.nmap.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class ScanResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id")
    private HostEntity hostEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "scanResultEntity", cascade = CascadeType.ALL)
    private List<PortEntity> portEntity;
}
