package br.com.programmers_challenge.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.programmers_challenge.domain.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
	
}
