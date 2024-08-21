package org.nmap.app.util;

import org.nmap.app.service.external.dto.NmapResponseDto;
import org.nmap.app.service.external.dto.PortInfoDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NmapParserUtil {

    public static NmapResponseDto parseNmapOutput(String output) {
        NmapResponseDto result = new NmapResponseDto();
        String[] lines = output.split("\n");

        Pattern hostPattern = Pattern.compile("Nmap scan report for (.+) \\((.+)\\)");
        Pattern ipPattern = Pattern.compile("Nmap scan report for (.+)");
        Pattern latencyPattern = Pattern.compile("Host is up \\((.+) latency\\)");
        Pattern portPattern = Pattern.compile("(\\d+/tcp)\\s+(\\w+)\\s+(\\w+)");

        for (String line : lines) {
            Matcher hostMatcher = hostPattern.matcher(line);
            Matcher ipMatcher = ipPattern.matcher(line);
            Matcher latencyMatcher = latencyPattern.matcher(line);
            Matcher portMatcher = portPattern.matcher(line);

            if (hostMatcher.find()) {
                result.setHost(hostMatcher.group(1));
                result.setIpAddress(hostMatcher.group(2));
            } else if (ipMatcher.find()) {
                result.setIpAddress(ipMatcher.group(1));
            }

            if (latencyMatcher.find()) {
                result.setLatency(latencyMatcher.group(1));
            }

            if (portMatcher.find()) {
                String port = portMatcher.group(1);
                String state = portMatcher.group(2);
                String service = portMatcher.group(3);
                result.getPorts().add(new PortInfoDto(port, state, service));
            }
        }

        return result;
    }
}
