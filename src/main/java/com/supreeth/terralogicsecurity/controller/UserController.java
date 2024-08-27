package com.supreeth.terralogicsecurity.controller;

import com.supreeth.terralogicsecurity.dao.AccountDao;
import com.supreeth.terralogicsecurity.dto.AccountDto;
import com.supreeth.terralogicsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<AccountDto>> allUsers() {
        List<AccountDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_WRITE','USER_READ')")
    public ResponseEntity<AccountDto> getUser(@PathVariable String username) {
        AccountDto user = userService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_WRITE')")
    public ResponseEntity<AccountDto> updateUser(@PathVariable String username, @RequestBody AccountDto accountDto) {
        AccountDto updatedUser = userService.updateUser(username, accountDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
