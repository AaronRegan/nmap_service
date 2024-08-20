package org.nmap.app.util;

import org.nmap.app.model.PortEntity;
import org.nmap.app.service.dto.PortChangeDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PortComparisonUtil {

    public static List<PortChangeDto> comparePorts(List<PortEntity> previousPorts, List<PortEntity> currentPorts) {
        Map<String, PortEntity> previousPortsMap = previousPorts.stream()
                .collect(Collectors.toMap(PortEntity::getPort, port -> port));

        Map<String, PortEntity> currentPortsMap = currentPorts.stream()
                .collect(Collectors.toMap(PortEntity::getPort, port -> port));

        List<PortChangeDto> changes = new ArrayList<>();

        // Check for changes or additions
        for (Map.Entry<String, PortEntity> entry : currentPortsMap.entrySet()) {
            String portKey = entry.getKey();
            PortEntity currentPort = entry.getValue();
            PortEntity previousPort = previousPortsMap.get(portKey);

            if (previousPort == null) {
                changes.add(new PortChangeDto(portKey, null, currentPort.getState(), PortChangeDto.ChangeType.ADDED));
            } else if (!previousPort.getState().equals(currentPort.getState())) {
                changes.add(new PortChangeDto(portKey, previousPort.getState(), currentPort.getState(), PortChangeDto.ChangeType.CHANGED));
            }
        }

        // Check for removed ports
        for (Map.Entry<String, PortEntity> entry : previousPortsMap.entrySet()) {
            String portKey = entry.getKey();
            if (!currentPortsMap.containsKey(portKey)) {
                changes.add(new PortChangeDto(portKey, entry.getValue().getState(), null, PortChangeDto.ChangeType.REMOVED));
            }
        }

        return changes;
    }

}
