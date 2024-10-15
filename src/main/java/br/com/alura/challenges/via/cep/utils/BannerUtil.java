package br.com.alura.challenges.via.cep.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BannerUtil {

	private static final String BANNER_ARCHIVE = "BANNER.txt";
	private static final String STD_BANNER = "Via CEP";

	public static void load() {
		var builder = new StringBuilder();
		try {
			var inputStream = BannerUtil.class.getClassLoader().getResourceAsStream(BANNER_ARCHIVE);
			if (inputStream != null) {
				try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
					var line = "";
					while ((line = reader.readLine()) != null) {
						builder.append(line);
						builder.append('\n');
					}
				} catch (IOException e) {
					builder.append(STD_BANNER);
				}
			} else {
				builder.append(STD_BANNER);
			}
		} catch (NullPointerException e) {
			builder.append(STD_BANNER);
		}
		System.out.println(builder);
	}

}
