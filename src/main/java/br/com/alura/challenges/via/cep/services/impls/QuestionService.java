package br.com.alura.challenges.via.cep.services.impls;

import br.com.alura.challenges.via.cep.exceptions.IllegalInputException;
import br.com.alura.challenges.via.cep.services.IQuestionService;

import java.util.Scanner;

public class QuestionService implements IQuestionService {

	private final Scanner scanner;

	public QuestionService(final Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public boolean init() {
		final var choice = scanner.nextLine();

		switch (choice) {
			case "S", "s", "Sim", "sim" -> { return true; }
			case "N", "n", "Não", "não", "Nao", "nao" -> { return false; }
			default -> throw new IllegalInputException("Opção inválida");
		}
	}

}
