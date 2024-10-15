package br.com.alura.challenges.via.cep.services.impls;

import br.com.alura.challenges.via.cep.controllers.AddressController;
import br.com.alura.challenges.via.cep.models.enums.MainMenu;
import br.com.alura.challenges.via.cep.services.IHistoryRemoveService;
import br.com.alura.challenges.via.cep.services.IQuestionService;
import br.com.alura.challenges.via.cep.utils.ScannerUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HistoryRemoveService implements IHistoryRemoveService {

	private final Scanner scanner;
	private final IQuestionService questionService;

	public HistoryRemoveService(final Scanner scanner, final IQuestionService questionService) {
		this.scanner = scanner;
		this.questionService = questionService;
	}

	@Override
	public void init() {
		System.out.println("\n> Opção selecionada: " + MainMenu.REMOVE.getLabel());
		System.out.println("Informe o indice do endereço no histórico a ser removido.");
		try {
			System.out.print("Indice: ");
			final var index = scanner.nextInt() - 1;

			try  {
				var found = AddressController.getHistory().get(index);

				System.out.print("Deseja realmente remover o endereço do indice? (S/n) ");
				ScannerUtil.clear(scanner);
				final var choice = questionService.init();
				if (choice) {
					AddressController.getHistory().remove(found);
					System.out.println("Endereço removido do histórico.");
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Não foi encontrado o endereço com o indice no histórico.");
			}
		} catch (InputMismatchException e) {
			System.out.println("O indice informado não é válido.");
		}
	}

}
