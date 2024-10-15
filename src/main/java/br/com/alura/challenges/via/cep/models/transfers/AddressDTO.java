package br.com.alura.challenges.via.cep.models.transfers;

public record AddressDTO(
	String cep,
  	String logradouro,
    String bairro,
  	String localidade,
  	String uf,
	String regiao
) {

	@Override
	public String toString() {
		return "%s, %s, %s-%s".formatted(logradouro, bairro, localidade, uf);
	}

}
