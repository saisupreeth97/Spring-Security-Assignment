package com.supreeth.terralogicsecurity;

import com.supreeth.terralogicsecurity.dao.AccountDao;
import com.supreeth.terralogicsecurity.repository.IAccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class TerralogicSecurityApplication {

	@Autowired
	private IAccountRepository iAccountRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void initUsers()
	{
		List<AccountDao> users = Stream.of(
				new AccountDao("terralogic","terra",null,"logic",bCryptPasswordEncoder.encode("password"),"test@gmail.com","ROLE_ADMIN"),
				new AccountDao("terralogic1","terra",null,"logic",bCryptPasswordEncoder.encode("password"),"test1@gmail.com","ROLE_USER_READ")
		).toList();
		iAccountRepository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(TerralogicSecurityApplication.class, args);
	}

}
