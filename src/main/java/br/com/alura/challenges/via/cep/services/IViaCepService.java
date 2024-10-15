package br.com.alura.challenges.via.cep.services;

public interface IViaCepService {
	<T> T request(String cep, Class<T> clazz);
}
