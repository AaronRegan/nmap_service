package org.nmap.app.repository;

import org.nmap.app.model.PortEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortRepository  extends JpaRepository<PortEntity, Long> {
}
