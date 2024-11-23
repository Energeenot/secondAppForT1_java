package Energeenot.secondAppForT1_java.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientStatusController {

    @GetMapping
    public ResponseEntity<Map<String, String>> getClientStatus(@RequestParam String clientId, @RequestParam String accountId) {
        boolean isBlackList = Math.random() < 0.1;
        String status = isBlackList ? "BLOCKED" : "ACTIVE";
        log.info("Client status is {}", status);
        Map<String, String> response = Map.of(
                "clientId", clientId,
                "status", status
        );
        return ResponseEntity.ok(response);
    }

}
