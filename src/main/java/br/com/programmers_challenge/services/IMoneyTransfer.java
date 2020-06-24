package br.com.programmers_challenge.services;

public interface IMoneyTransfer {
	
	boolean transfer(Integer origemAccountId, Integer destinyAccountId, Double amount);	
	
}
