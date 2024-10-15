package br.com.alura.challenges.via.cep.configs;

import br.com.alura.challenges.via.cep.services.*;
import br.com.alura.challenges.via.cep.services.impls.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Scanner;

public class ApplicationConfig {

	public Scanner scanner() {
		return new Scanner(System.in);
	}

	public Gson gson() {
		return new GsonBuilder()
				.setPrettyPrinting()
				.create();
	}

	public IViaCepService viaCepService() {
		return new ViaCepService(gson());
	}

	public IQuestionService questionService() {
		return new QuestionService(scanner());
	}

	public IAnimService animService() {
		return new AnimService();
	}

	public IAddService addService() {
		return new AddService(scanner(), viaCepService(), questionService(), animService());
	}

	public IHistoryService historyService() {
		return new HistoryService();
	}

	public IHistoryRemoveService historyRemoveService() {
		return new HistoryRemoveService(scanner(), questionService());
	}

	public IHistorySaveService historySaveService() {
		return new HistorySaveService("address-history.json", gson());
	}

}
