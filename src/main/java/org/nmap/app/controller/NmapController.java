package org.nmap.app.controller;

import org.nmap.app.exception.InvalidIpAddressException;
import org.nmap.app.exception.NoScanHistoryException;
import org.nmap.app.service.NmapService;
import org.nmap.app.service.dto.ScanResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scan")
public class NmapController {


    @Autowired
    private NmapService nmapService;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(final NoScanHistoryException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidIpAddressException.class)
    public ResponseEntity<String> handle(final InvalidIpAddressException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @PostMapping("/initiate")
    public ScanResultDto initiateScan(@RequestParam String ipAddressOrHostname) throws Exception {
        return nmapService.initiateScan(ipAddressOrHostname);
    }

    @GetMapping("/history")
    public List<ScanResultDto> getScanHistory(@RequestParam String ipAddressOrHostname) {
        return nmapService.getScanHistory(ipAddressOrHostname);
    }
}
