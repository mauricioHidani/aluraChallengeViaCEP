package br.com.alura.challenges.via.cep;

import br.com.alura.challenges.via.cep.controllers.AddressController;

public class Main {
	public static void main(String[] args) {
		final AddressController controller = new AddressController();
		controller.start();
	}
}