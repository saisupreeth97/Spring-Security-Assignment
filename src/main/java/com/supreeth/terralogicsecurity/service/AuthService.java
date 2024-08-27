package com.supreeth.terralogicsecurity.service;

import com.supreeth.terralogicsecurity.dao.AccountDao;
import com.supreeth.terralogicsecurity.dto.AccountDto;
import com.supreeth.terralogicsecurity.dto.LoginDto;
import com.supreeth.terralogicsecurity.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String DEFAULT_ROLE = "ROLE_USER_WRITE";
    public static final List<String> ADMIN_ROLES_LIST = List.of("ROLE_USER_READ", "ROLE_USER_WRITE", "ROLE_ADMIN");
    public static final List<String> USER_ROLES_LIST = List.of("ROLE_USER_READ");

    public AccountDto register(AccountDto accountDto) {
        if (iAccountRepository.findByUserName(accountDto.getUserName()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        AccountDao accountDao = mapToDao(accountDto);
        accountDao.setRoles(DEFAULT_ROLE);
        return convertToDto(iAccountRepository.save(accountDao));
    }

    public AccountDao login(LoginDto loginDto) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getUserPassword())
        );
        return iAccountRepository.findByUserName(loginDto.getUserName()).orElseThrow(() -> new Exception("Invalid credentials"));
    }

    public AccountDao mapToDao(AccountDto accountDto) {
        AccountDao accountDao = new AccountDao();
        accountDao.setUserName(accountDto.getUserName());
        accountDao.setFirstName(accountDto.getFirstName());
        accountDao.setMiddleName(accountDto.getMiddleName());
        accountDao.setLastName(accountDto.getLastName());
        accountDao.setEmail(accountDto.getEmail());
        accountDao.setUserPassword(passwordEncoder.encode(accountDto.getUserPassword()));
        return accountDao;
    }

    public AccountDto convertToDto(AccountDao accountDao) {
        AccountDto accountDto = new AccountDto();
        accountDto.setUserName(accountDao.getUsername());
        accountDto.setFirstName(accountDao.getFirstName());
        accountDto.setMiddleName(accountDao.getMiddleName());
        accountDto.setLastName(accountDao.getLastName());
        accountDto.setEmail(accountDao.getEmail());
        return accountDto;
    }

    public String giveAccessToUser(String username, String role) {
        if(hasRole(SecurityContextHolder.getContext().getAuthentication(), "ROLE_ADMIN")
        && ADMIN_ROLES_LIST.contains(role))
        {
            updateRole(username, role);
        }
        else if(hasRole(SecurityContextHolder.getContext().getAuthentication(), "ROLE_USER_WRITE")&& USER_ROLES_LIST.contains(role))
        {
            updateRole(username, role);
        }
        else
        {
            return "You are not authorized to perform this action";
        }
        return "Role updated successfully";
    }

    public void updateRole(String username, String role) {
        AccountDao accountDao = iAccountRepository.findByUserName(username).orElseThrow();
        accountDao.setRoles(role);
        iAccountRepository.save(accountDao);
    }


    public boolean hasRole(Principal principal, String role) {
        Authentication authentication = (Authentication) principal;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }

}
