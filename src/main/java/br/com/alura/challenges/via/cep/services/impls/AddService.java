package br.com.alura.challenges.via.cep.services.impls;

import br.com.alura.challenges.via.cep.controllers.AddressController;
import br.com.alura.challenges.via.cep.exceptions.IllegalInputException;
import br.com.alura.challenges.via.cep.exceptions.JsonConversionException;
import br.com.alura.challenges.via.cep.exceptions.NotFoundException;
import br.com.alura.challenges.via.cep.exceptions.RequestViaCepException;
import br.com.alura.challenges.via.cep.models.AddressHistory;
import br.com.alura.challenges.via.cep.models.enums.MainMenu;
import br.com.alura.challenges.via.cep.models.transfers.AddressDTO;
import br.com.alura.challenges.via.cep.services.IAddService;
import br.com.alura.challenges.via.cep.services.IAnimService;
import br.com.alura.challenges.via.cep.services.IQuestionService;
import br.com.alura.challenges.via.cep.services.IViaCepService;
import br.com.alura.challenges.via.cep.utils.ScannerUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddService implements IAddService {

	private final Scanner scanner;
	private final IViaCepService viaCepService;
	private final IQuestionService questionService;
	private final IAnimService animService;

	private static AddressDTO founded;

	public AddService(
		final Scanner scanner,
		final IViaCepService viaCepService,
		final IQuestionService questionService,
		final IAnimService animService
	) {
		this.scanner = scanner;
		this.viaCepService = viaCepService;
		this.questionService = questionService;
		this.animService = animService;
	}

	protected static synchronized AddressDTO getFounded() {
		return founded;
	}

	protected static synchronized void setFounded(final AddressDTO address) {
		founded = address;
	}

	@Override
	public void init() {
		System.out.println("\n> Opção selecionada: " + MainMenu.ADD.getLabel());

		try {
			System.out.println("Informe o endereço que será adicionado");
			final var cep = getCep();

			final var animThread = animService.animate("Procurando");
			final var taskThread = queryAddress(cep);

			animThread.start();
			taskThread.start();

			taskThread.join();

			animThread.interrupt();
			animThread.join();

			if (getFounded() != null) {
				setAddressToHistory();
			}

		} catch (InterruptedException e) {
			e.getStackTrace();
		} catch (IllegalInputException e) {
			System.out.println(e.getMessage());
		}
	}

	private Thread queryAddress(final String cep) {
		return new Thread(() -> {
			try {
				final var response = viaCepService.request(cep, AddressDTO.class);
				System.out.println("\r\rConsulta concluída");
				System.out.println("Endereço encontrado: " + response);
				setFounded(response);
			} catch (NotFoundException | RequestViaCepException | JsonConversionException | IllegalInputException e) {
				System.out.print("\r\r" + e.getMessage());
			}
		});
	}

	private String getProprietario() {
		System.out.print("Nome do proprietário: ");
		final var proprietario = scanner.nextLine();

		if (proprietario.length() < 3) {
			throw new IllegalInputException("Nome do proprietário deve conter 3 ou mais caracteres.");
		}
		if (proprietario.matches("[^a-zA-Z ]")) {
			throw new IllegalInputException("O nome do proprietário deve ser composto apenas por letras.");
		}

		return proprietario;
	}

	private Integer getNumero() {
		System.out.print("Número do endereço: ");
		try {
			final var numero = scanner.nextInt();
			if (numero < 0) {
				throw new IllegalInputException();
			}

			return numero;
		} catch (InputMismatchException | IllegalInputException e) {
			throw new IllegalInputException("Número do endereço é inválido.");
		}
	}

	private String getComplement() {
		System.out.print("Complemento para identificação do endereco: ");
		ScannerUtil.clear(scanner);
		final var complemento = scanner.nextLine();
		if (complemento.length() > 64) {
			throw new IllegalInputException(
				"Complemento para identificação do endereço deve conter menos que 64 caracteres."
			);
		}
		return complemento;
	}

	private String getCep() {
		System.out.println("Realize a consulta do endereço usando o CEP");
		System.out.print("CEP: ");
		ScannerUtil.clear(scanner);
		final var cep = scanner.nextLine();
		final var newCep = cep.replaceAll("[^0-9]", "");
		if (newCep.length() < 8) {
			throw new IllegalInputException("O Cep informado é inválido não contém 8 caracteres.");
		}

		return newCep;
	}

	private void setAddressToHistory() {
		System.out.print("Deseja adicionar esse endereço? (S/n) ");
		final var choice = questionService.init();

		if (choice) {
			System.out.println("\nSerá necessário algumas informações");
			final var proprietario = getProprietario();
			final var numero = getNumero();
			final var complemento = getComplement();
			final var history = AddressHistory.parse(getFounded(), proprietario, numero, complemento);
			System.out.println("Endereço adicionado");
			AddressController.addAddressToHistory(history);
			setFounded(null);
		} else {
			System.out.println("Operação finalizada.");
		}
	}

}
