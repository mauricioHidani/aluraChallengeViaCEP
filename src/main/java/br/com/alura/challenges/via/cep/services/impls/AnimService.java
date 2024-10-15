package br.com.alura.challenges.via.cep.services.impls;

import br.com.alura.challenges.via.cep.services.IAnimService;

public class AnimService implements IAnimService {

	@Override
	public Thread animate(final String message) {
		return new Thread(() -> {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					System.out.print("\r" + message + ".");
					Thread.sleep(500);
					System.out.print("\r" + message + "..");
					Thread.sleep(500);
					System.out.print("\r" + message + "...");
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				System.out.println("\r");
			}
		});
	}

}
