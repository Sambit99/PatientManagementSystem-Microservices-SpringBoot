package com.pms.authservice.controller;

import com.pms.authservice.dto.LoginRequestDTO;
import com.pms.authservice.service.AuthService;
import com.pms.authservice.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletRequest request, @RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if (tokenOptional.isEmpty()) {
            return ApiResponse.Send(request,"fail", HttpStatus.UNAUTHORIZED, null);
        }

        String token = tokenOptional.get();
        return ApiResponse.Send(request,"success", HttpStatus.OK, new HashMap<>(Map.of("token", token)));
    }
}
