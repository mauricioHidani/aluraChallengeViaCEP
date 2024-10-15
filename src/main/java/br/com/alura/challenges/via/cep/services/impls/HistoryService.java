package br.com.alura.challenges.via.cep.services.impls;

import br.com.alura.challenges.via.cep.controllers.AddressController;
import br.com.alura.challenges.via.cep.models.enums.MainMenu;
import br.com.alura.challenges.via.cep.services.IHistoryService;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class HistoryService implements IHistoryService {

	@Override
	public void init() {
		System.out.println("\n> Opção selecionada: " + MainMenu.HISTORY.getLabel());
		var index = new AtomicInteger(0);
		AddressController.getHistory().forEach((address) -> {
			System.out.printf("""
				%d - Endereço adicionado em %s
				Endereço: %s
				Proprietário: %s
				""",
				index.get() + 1,
				DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(address.registro()),
				"%s, %d %s, %s, %s-%s, %s".formatted(
					address.logradouro(),
					address.numero(),
					address.complemento(),
					address.bairro(),
					address.cidade(),
					address.uf(),
					address.cep().replaceAll("(\\d{5})(\\d{3})", "$1-$2")
				),
				address.proprietario()
			);

			if (index.getAndIncrement() < (AddressController.getHistory().size() - 1)) {
				System.out.println();
			}
		});
	}

}
