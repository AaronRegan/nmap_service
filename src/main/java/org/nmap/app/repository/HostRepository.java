package org.nmap.app.repository;

import org.nmap.app.model.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
    Optional<HostEntity> findByIpAddress(String ipAddress);
}
