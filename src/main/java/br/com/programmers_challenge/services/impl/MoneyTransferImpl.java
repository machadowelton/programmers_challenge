package br.com.programmers_challenge.services.impl;

import org.springframework.stereotype.Service;

import br.com.programmers_challenge.domain.entity.Account;
import br.com.programmers_challenge.domain.exceptions.BusinessException;
import br.com.programmers_challenge.domain.exceptions.ResourceNotFoundException;
import br.com.programmers_challenge.services.IMoneyTransfer;
import br.com.programmers_challenge.services.repository.IAccountRepository;

@Service
public class MoneyTransferImpl implements IMoneyTransfer {
	
	private final IAccountRepository iAccountRepository;
	
	public MoneyTransferImpl(final IAccountRepository repository) {
		this.iAccountRepository = repository; 
	}

	@Override
	public boolean transfer(Integer origemAccountId, Integer destinyAccountId, Double amount) {
		try {
			Account origemAccount = 
					iAccountRepository.findById(origemAccountId)
						.map((m) -> m)
						.orElseThrow(() -> new ResourceNotFoundException("Nenhuma conta encontrada pelo id " + origemAccountId));
			Account destinyAccount =
					iAccountRepository.findById(destinyAccountId)
						.map((m) -> m)
						.orElseThrow(() -> new ResourceNotFoundException("Nenhuma conta encontrada pelo id " + destinyAccountId));
			if(origemAccount.getBalance() < amount)
				throw new BusinessException("Saldo insuficiente para a transferência");
			Account origemAccountUpdated = new Account(origemAccount.getId(), origemAccount.getClietName(), origemAccount.getBalance() - amount);
			Account destinyAccountUpdated = new Account(destinyAccount.getId(), destinyAccount.getClietName(), destinyAccount.getBalance() + amount);
			iAccountRepository.save(origemAccountUpdated);
			iAccountRepository.save(destinyAccountUpdated);
			return true;
		} catch (Exception e) {
			if(e instanceof BusinessException || 
					e instanceof ResourceNotFoundException) throw e;
			throw new RuntimeException("Ocorreu ao realizar a transferência");
		}
	}

}
