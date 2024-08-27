package com.supreeth.terralogicsecurity.controller;


import com.supreeth.terralogicsecurity.dao.AccountDao;
import com.supreeth.terralogicsecurity.dto.AccountDto;
import com.supreeth.terralogicsecurity.dto.LoginDto;
import com.supreeth.terralogicsecurity.service.AuthService;
import com.supreeth.terralogicsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AccountDto> register(@RequestBody AccountDto accountDto)
    {
        AccountDto register = authService.register(accountDto);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws Exception {
        AccountDao accountDao = authService.login(loginDto);
        return new ResponseEntity<>(jwtUtil.generateToken(accountDao), HttpStatus.OK);
    }

    @PostMapping("/access/{username}/{role}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_WRITE')")
    public String giveAccessToUser(@PathVariable String username, @PathVariable String role)
    {
        return authService.giveAccessToUser(username, role);
    }
}
