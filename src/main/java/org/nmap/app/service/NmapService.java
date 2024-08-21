package org.nmap.app.service;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.nmap.app.exception.InvalidIpAddressException;
import org.nmap.app.exception.NoScanHistoryException;
import org.nmap.app.model.HostEntity;
import org.nmap.app.model.PortEntity;
import org.nmap.app.model.ScanResultEntity;
import org.nmap.app.repository.HostRepository;
import org.nmap.app.repository.PortRepository;
import org.nmap.app.repository.ScanResultRepository;
import org.nmap.app.service.dto.PortChangeDto;
import org.nmap.app.service.dto.ScanResultDto;
import org.nmap.app.service.external.dto.NmapResponseDto;
import org.nmap.app.service.external.NmapExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.nmap.app.util.PortComparisonUtil.comparePorts;

@Service
public class NmapService {

    private final HostRepository hostRepository;

    private final ScanResultRepository scanResultRepository;

    private final PortRepository portRepository;

    private final NmapExternalService nmapExternalService;

    @Autowired
    public NmapService(HostRepository hostRepository,
                       NmapExternalService nmapExternalService,
                       ScanResultRepository scanResultRepository,
                       PortRepository portRepository) {
        this.hostRepository = hostRepository;
        this.nmapExternalService = nmapExternalService;
        this.scanResultRepository = scanResultRepository;
        this.portRepository = portRepository;
    }

    public ScanResultDto initiateScan(String ipAddressOrHostname) throws Exception {
        if (!isValidIpOrHostname(ipAddressOrHostname)) {
            throw new InvalidIpAddressException(ipAddressOrHostname);
        }

        NmapResponseDto responseDto = this.nmapExternalService.callNmapAgainstHost(ipAddressOrHostname);

        HostEntity hostEntity = this.findOrCreate(responseDto);

        ScanResultEntity scanResultEntity = new ScanResultEntity();
        scanResultEntity.setTimestamp(LocalDateTime.now());
        scanResultEntity.setHostEntity(hostEntity);

        ScanResultEntity scanResultEntity1 = this.scanResultRepository.save(scanResultEntity);

        List<PortEntity> portEntities = responseDto.getPorts().stream()
                .map(portInfoDto -> {
                    PortEntity portEntity = new PortEntity();
                    portEntity.setPort(portInfoDto.getPort());
                    portEntity.setState(portInfoDto.getState());
                    portEntity.setService(portInfoDto.getService());
                    portEntity.setScanResultEntity(scanResultEntity1);
                    return this.portRepository.save(portEntity);
                })
                .toList();

        List<PortChangeDto> changes = this.findPortChanges(hostEntity, portEntities);

        return new ScanResultDto(scanResultEntity1, hostEntity, portEntities, changes);
    }

    public List<ScanResultDto> getScanHistory(String ipAddressOrHostname) {
        HostEntity hostEntity = this.hostRepository.findByIpAddress(ipAddressOrHostname)
                .orElseThrow(() -> new NoScanHistoryException(ipAddressOrHostname));

        ArrayList<ScanResultDto> result = new ArrayList<>();

        for (ScanResultEntity scanResultEntity : hostEntity.getScanResultEntities()) {
            result.add(new ScanResultDto(scanResultEntity, hostEntity, scanResultEntity.getPortEntity()));
        }
        return result;
    }

    private HostEntity findOrCreate(NmapResponseDto responseDto) {
        return this.hostRepository.findByIpAddress(responseDto.getIpAddress())
                .orElseGet(() -> this.hostRepository.save(
                        new HostEntity(
                                responseDto.getIpAddress(),
                                responseDto.getHost()
                        )
                ));
    }

    private List<PortChangeDto> findPortChanges(HostEntity hostEntity, List<PortEntity> portEntities) {
        List<ScanResultEntity> previousPortEntities = this.scanResultRepository.findTopByHostIdOrderByTimestampDesc(hostEntity.getId());
        if(previousPortEntities.size()<=1){
            return List.of();
        }
        return comparePorts(previousPortEntities.get(1).getPortEntity(), portEntities);
    }

    private boolean isValidIpOrHostname(String ipAddressOrHostname) {
        InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
        DomainValidator domainValidator = DomainValidator.getInstance();

        return inetAddressValidator.isValid(ipAddressOrHostname) || domainValidator.isValid(ipAddressOrHostname);
    }
}
