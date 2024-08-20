package org.nmap.app.service.external;

import org.nmap.app.service.external.dto.NmapResponseDto;
import org.nmap.app.util.NmapParserUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class NmapExternalService {

    public NmapExternalService() {

    }

    public NmapResponseDto callNmapAgainstHost(String ipAddressOrHostname) throws IOException, InterruptedException {

        Process process = Runtime.getRuntime().exec("nmap " + ipAddressOrHostname);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        process.waitFor();

        // Parse and store result
        String result = output.toString();
        NmapResponseDto response = NmapParserUtil.parseNmapOutput(result);

        return response;
    }
}
