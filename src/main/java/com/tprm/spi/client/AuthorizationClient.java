package com.tprm.spi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tprm.spi.utils.AuthenticationResponse;

@FeignClient(name = "authorization-service", url = "${application.config.authorization-url}")
public interface AuthorizationClient {
    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestParam String userName,
            @RequestParam String password);
}
