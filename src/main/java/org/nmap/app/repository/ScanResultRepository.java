package org.nmap.app.repository;

import org.nmap.app.model.ScanResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScanResultRepository extends JpaRepository<ScanResultEntity, Long> {

    @Query("SELECT sr FROM ScanResultEntity sr WHERE sr.hostEntity.id = :hostId ORDER BY sr.timestamp DESC")
    List<ScanResultEntity> findTopByHostIdOrderByTimestampDesc(@Param("hostId") Long hostId);
}
