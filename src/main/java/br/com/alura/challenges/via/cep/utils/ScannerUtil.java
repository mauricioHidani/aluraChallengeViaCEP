package br.com.alura.challenges.via.cep.utils;

import java.util.Scanner;

public class ScannerUtil {
	public static void clear(final Scanner scanner) {
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
	}
}
