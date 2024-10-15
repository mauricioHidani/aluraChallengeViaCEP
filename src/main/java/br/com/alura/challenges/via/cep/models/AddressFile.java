package br.com.alura.challenges.via.cep.models;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record AddressFile(
	String proprietario,
	String cep,
	String logradouro,
	Integer numero,
	String complemento,
	String bairro,
	String cidade,
	String uf,
	String regiao,
	String registro
) {

	public static AddressFile parse(final AddressHistory history) {
		return new AddressFile(
				history.proprietario(),
				history.cep(),
				history.logradouro(),
				history.numero(),
				history.complemento(),
				history.bairro(),
				history.cidade(),
				history.uf(),
				history.regiao(),
				DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(history.registro())
		);
	}

	public static List<AddressFile> parse(final List<AddressHistory> history) {
		return history.stream().map(AddressFile::parse).toList();
	}

}
