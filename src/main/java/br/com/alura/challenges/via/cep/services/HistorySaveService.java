package br.com.alura.challenges.via.cep.services;

import br.com.alura.challenges.via.cep.controllers.AddressController;
import br.com.alura.challenges.via.cep.models.AddressFile;
import br.com.alura.challenges.via.cep.models.enums.MainMenu;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class HistorySaveService implements IHistorySaveService {

	private final String historyFileNamePath;
	private final Gson gson;

	public HistorySaveService(final String historyFileNamePath, final Gson gson) {
		this.historyFileNamePath = historyFileNamePath;
		this.gson = gson;
	}

	@Override
	public void init() {
		System.out.println("\n> Opção selecionada: " + MainMenu.SAVE.getLabel());

		try {
			FileWriter writer = new FileWriter(historyFileNamePath);
			final var addressesFile = AddressFile.parse(AddressController.getHistory());
			writer.write(gson.toJson(addressesFile));
			writer.close();

			System.out.println("Histórico de endereços salvo.");

		} catch (IOException e) {
			System.out.println("Não foi possivel realizar o salvamento do histórico de endereços.");
		}
	}

}
