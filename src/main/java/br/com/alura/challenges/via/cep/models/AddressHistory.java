package br.com.alura.challenges.via.cep.models;

import br.com.alura.challenges.via.cep.models.transfers.AddressDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record AddressHistory(
	String proprietario,
	String cep,
	String logradouro,
	Integer numero,
	String complemento,
	String bairro,
	String cidade,
	String uf,
	String regiao,
	LocalDateTime registro
) {

	public static AddressHistory parse(
		final AddressDTO transferencia,
		final String proprietario,
		final Integer numero,
		final String complemento
	) {
		return new AddressHistory(
			proprietario,
			transferencia.cep(),
			transferencia.logradouro(),
			numero,
			complemento,
			transferencia.bairro(),
			transferencia.localidade(),
			transferencia.uf(),
			transferencia.regiao(),
			LocalDateTime.now()
		);
	}

	@Override
	public String toString() {
		final var newCep = cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
		final var newEndereco = "%s, %d(%s), %s, %s-%s, %s".formatted(logradouro, numero, complemento, bairro, cidade, uf, newCep);
		final var newReistro = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(registro);
		return "Endereço: %s\n Proprietário: %s\n Registro: %s".formatted(newEndereco, proprietario, newReistro);
	}
}
