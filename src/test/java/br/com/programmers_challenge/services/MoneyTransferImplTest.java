package br.com.programmers_challenge.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.programmers_challenge.domain.entity.Account;
import br.com.programmers_challenge.domain.exceptions.BusinessException;
import br.com.programmers_challenge.domain.exceptions.ResourceNotFoundException;
import br.com.programmers_challenge.services.impl.MoneyTransferImpl;
import br.com.programmers_challenge.services.repository.IAccountRepository;

@ExtendWith(SpringExtension.class)
public class MoneyTransferImplTest {
	
	
	@MockBean
	IAccountRepository iAccountRepository;
	
	
	IMoneyTransfer iMoneyTransfer;
	
	@BeforeEach
	public void setUp() {
		this.iMoneyTransfer = new MoneyTransferImpl(iAccountRepository);
	}
	
	
	private final Account account1 = new Account(1, "Jose dos Santos", 3000d);
	private final Account account2 = new Account(2, "Manolo Rodrigues", 3000d);
	
	@Test
	@DisplayName("Deve realizar uma transferência")
	public void trasfer() {
		Integer origemAccountId = 1;
		Integer destinyAccountId = 1;
		Double amount = 2000d;
		Account origemAccount = account1;
		Account destinyAccount = account2;		
		when(iAccountRepository.findById(origemAccountId)).thenReturn(Optional.of(origemAccount));
		when(iAccountRepository.findById(destinyAccountId)).thenReturn(Optional.of(destinyAccount));		
		assertDoesNotThrow(() -> {			
			boolean status = iMoneyTransfer.transfer(origemAccountId, destinyAccountId, amount);
			assertThat(status).isTrue();
			verify(iAccountRepository, Mockito.times(2)).save(Mockito.any());
		});				
	}
	
	@Test
	@DisplayName("Deve lançar um erro de BusinessExeption quando saldo for insuficiente")
	public void trasferFailInsufficientFunds() {
		Integer origemAccountId = 1;
		Integer destinyAccountId = 1;
		Double amount = 4000d;
		Account origemAccount = account1;
		Account destinyAccount = account2;		
		when(iAccountRepository.findById(origemAccountId)).thenReturn(Optional.of(origemAccount));
		when(iAccountRepository.findById(destinyAccountId)).thenReturn(Optional.of(destinyAccount));		
		Throwable thr = catchThrowable(() -> {
			iMoneyTransfer.transfer(origemAccountId, destinyAccountId, amount);
		});
		assertThat(thr)
			.isInstanceOf(BusinessException.class);
		verify(iAccountRepository, Mockito.never()).save(Mockito.any());
	}
	
	@Test
	@DisplayName("Deverá lançar um erro de ResourceNotFoundException quando não encontrar uma conta pelo id")
	public void transferFailAccountNotFound() {
		Integer origemAccountId = 1;
		Integer destinyAccountId = 1;
		Double amount = 4000d;
		when(iAccountRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Throwable thr = catchThrowable(() -> {
			iMoneyTransfer.transfer(origemAccountId, destinyAccountId, amount);
		});
		assertThat(thr)
			.isInstanceOf(ResourceNotFoundException.class);
		verify(iAccountRepository, Mockito.never()).save(Mockito.any());
	}
}
