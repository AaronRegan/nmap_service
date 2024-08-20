package org.nmap.app.exception;

public class InvalidIpAddressException extends RuntimeException {

    public InvalidIpAddressException(String ipAddress) {
        super("Invalid IP Address " + ipAddress);
    }
}
