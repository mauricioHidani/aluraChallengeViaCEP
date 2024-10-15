package br.com.alura.challenges.via.cep.controllers;

import br.com.alura.challenges.via.cep.configs.ApplicationConfig;
import br.com.alura.challenges.via.cep.models.AddressHistory;
import br.com.alura.challenges.via.cep.models.enums.MainMenu;
import br.com.alura.challenges.via.cep.models.enums.MenuState;
import br.com.alura.challenges.via.cep.services.IAddService;
import br.com.alura.challenges.via.cep.services.IHistoryRemoveService;
import br.com.alura.challenges.via.cep.services.IHistorySaveService;
import br.com.alura.challenges.via.cep.services.IHistoryService;
import br.com.alura.challenges.via.cep.utils.BannerUtil;
import br.com.alura.challenges.via.cep.utils.ScannerUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AddressController {

	private final Scanner scanner;

	private final IAddService addService;
	private final IHistoryService historyService;
	private final IHistoryRemoveService historyRemoveService;
	private final IHistorySaveService historySaveService;

	private final List<MainMenu> options = Arrays.stream(MainMenu.values()).toList();
	private final static List<AddressHistory> history = new ArrayList<>();

	public AddressController() {
		final ApplicationConfig config = new ApplicationConfig();
		this.scanner = config.scanner();
		this.addService = config.addService();
		this.historyService = config.historyService();
		this.historyRemoveService = config.historyRemoveService();
		this.historySaveService = config.historySaveService();
	}

	public static synchronized List<AddressHistory> getHistory() {
		return history;
	}

	public static synchronized void addAddressToHistory(final AddressHistory address) {
		history.add(address);
	}

	public void start() {
		var menuState = MenuState.RUNNING;

		BannerUtil.load();
		System.out.println("""
				Desafio Alura ONE G7 - Endereços Via CEP
				Aula -> Java: Consumindo APIs, gravando arquivos e lidando com errors 
				14 de outubro de 2024 - São Paulo, Brasil""");

		do {
			showMainMenu();
			menuState = onChoiceOption(getOption());

		} while (menuState != MenuState.LEAVING);
	}

	private MainMenu getOption() {
		try {
			System.out.print("Opção: ");
			var choice = scanner.nextInt();
			if (choice < 0 || choice >= options.size()) {
				return MainMenu.WRONG_OPTION;
			}
			return options.get(choice -1);

		} catch (InputMismatchException e) {
			ScannerUtil.clear(scanner);
			return MainMenu.WRONG_OPTION;
		}
	}

	private void showMainMenu() {
		System.out.println("\nMenu principal");
		final var optionsSize = options.size();
		var indexOption = new AtomicInteger(1);
		options.forEach((option) -> {
			if (indexOption.get() < optionsSize) {
				System.out.printf(
					"%d- %s\n", indexOption.getAndIncrement(), option.getLabel()
				);
			}
		});
	}

	private MenuState onChoiceOption(final MainMenu choice) {
		switch (choice) {
			case ADD -> addService.init();
			case HISTORY -> historyService.init();
			case REMOVE -> historyRemoveService.init();
			case SAVE -> historySaveService.init();
			case EXIT -> { return MenuState.LEAVING; }
			default -> System.out.println("Opção inválida selecionada");
		}
		return MenuState.RUNNING;
	}

}
