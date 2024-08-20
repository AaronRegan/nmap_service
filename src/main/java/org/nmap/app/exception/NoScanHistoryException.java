package org.nmap.app.exception;

public class NoScanHistoryException extends RuntimeException {

    public NoScanHistoryException(String ipAddress) {
        super("No Scan History found for " + ipAddress);
    }
}
