package com.supreeth.terralogicsecurity.repository;

import com.supreeth.terralogicsecurity.dao.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IAccountRepository extends JpaRepository<AccountDao,String> {
    Optional<AccountDao> findByUserName(String username);
}
