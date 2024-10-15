package br.com.alura.challenges.via.cep.services.impls;

import br.com.alura.challenges.via.cep.exceptions.JsonConversionException;
import br.com.alura.challenges.via.cep.exceptions.NotFoundException;
import br.com.alura.challenges.via.cep.exceptions.RequestViaCepException;
import br.com.alura.challenges.via.cep.models.transfers.ErrorDTO;
import br.com.alura.challenges.via.cep.services.IViaCepService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepService implements IViaCepService {

	private final String VIA_CEP_URI = "https://viacep.com.br/ws/";
	private final String STD_FORMAT = "json";

	private final Gson gson;

	public ViaCepService(final Gson gson) {
		this.gson = gson;
	}

	@Override
	public <T> T request(final String cep, final Class<T> clazz) {
		final String uri = VIA_CEP_URI + cep + '/' + STD_FORMAT;
		try {
			final var client = HttpClient.newHttpClient();
			final var request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
			final var bodyResponse = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

			try {
				if (gson.fromJson(bodyResponse, ErrorDTO.class).erro()) {
					throw new NotFoundException(
						"Não foi possivel encontrar o endereço com o CEP especificado."
					);
				}
			} catch (JsonSyntaxException | NullPointerException e) {
				return gson.fromJson(bodyResponse, clazz);
			}

		} catch (UncheckedIOException | InterruptedException | IOException e) {
			throw new RequestViaCepException(
				"Não foi possivel realizar a consulta do endereço."
			);
		} catch (JsonSyntaxException e) {
			throw new JsonConversionException(
				"Ocorreu um erro ao tentar converter as informações de consulta."
			);
		}
		return null;
	}
}
