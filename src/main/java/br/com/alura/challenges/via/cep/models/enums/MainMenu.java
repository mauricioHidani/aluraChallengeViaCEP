package br.com.alura.challenges.via.cep.models.enums;

public enum MainMenu {
	ADD("Adicionar endereço"),
	HISTORY("Histórico de endereço"),
	REMOVE("Remover endereço"),
	SAVE("Salvar histórico"),
	EXIT("Sair"),
	WRONG_OPTION(""),
	;

	private final String label;

	MainMenu(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
