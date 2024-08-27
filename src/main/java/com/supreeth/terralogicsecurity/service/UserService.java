package com.supreeth.terralogicsecurity.service;

import com.supreeth.terralogicsecurity.dao.AccountDao;
import com.supreeth.terralogicsecurity.dto.AccountDto;
import com.supreeth.terralogicsecurity.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IAccountRepository iAccountRepository;

    public List<AccountDto> getAllUsers() {
        return convertToDto(iAccountRepository.findAll());
    }

    public List<AccountDto> convertToDto(List<AccountDao> accountDaos) {
        return accountDaos.stream().map(accountDao -> {
            AccountDto accountDto = new AccountDto();
            accountDto.setUserName(accountDao.getUsername());
            accountDto.setFirstName(accountDao.getFirstName());
            accountDto.setMiddleName(accountDao.getMiddleName());
            accountDto.setLastName(accountDao.getLastName());
            accountDto.setEmail(accountDao.getEmail());
            return accountDto;
        }).toList();
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

    public AccountDto getUser(String username) {
        AccountDao accountDao = iAccountRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(accountDao);
    }

    public AccountDto updateUser(String username, AccountDto accountDto) {
        AccountDao accountDao = iAccountRepository.findByUserName(username).orElseThrow();
        accountDao.setFirstName(accountDto.getFirstName());
        accountDao.setMiddleName(accountDto.getMiddleName());
        accountDao.setLastName(accountDto.getLastName());
        accountDao.setEmail(accountDto.getEmail());
        iAccountRepository.save(accountDao);
        return convertToDto(accountDao);
    }
}
