package br.com.infuse.crudsb.util;

import java.util.Optional;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.infuse.crudsb.dto.RetornoDTO;

public final class ResponseEntityUtil {

	private ResponseEntityUtil() {}

	public static ResponseEntity<RetornoDTO> defaultResponse(Object objeto) {

		RetornoDTO retorno = null;

		if (objeto instanceof Optional) {

			if (!((Optional<?>) objeto).isPresent()) {
				retorno = new RetornoDTO(HttpStatus.NOT_FOUND, "Nenhum registro localizado.");
				return new ResponseEntity<>(retorno, retorno.getStatus());
			}

		}else if (objeto instanceof PageImpl) {

			if (((PageImpl<?>) objeto).isEmpty()) {
				retorno = new RetornoDTO(HttpStatus.NOT_FOUND, "Nenhum registro localizado para o item pesquisado.");
				return new ResponseEntity<>(retorno, retorno.getStatus());
			} 
		}
		if (objeto == null || objeto.toString().equals("[]")) {
			retorno = new RetornoDTO(HttpStatus.NOT_FOUND, "Nenhum registro localizado.");
		} else {
			retorno = new RetornoDTO(HttpStatus.OK, objeto);
		}
		return new ResponseEntity<>(retorno, retorno.getStatus());
	}

	public static ResponseEntity<RetornoDTO> retornaSucesso() {

		RetornoDTO retorno = new RetornoDTO(HttpStatus.OK, true);

		return new ResponseEntity<>(retorno, retorno.getStatus());

	}

}
