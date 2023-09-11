package com.example.despesasfamiliares.controller.exceptionhandler;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErroHandler {

	@ExceptionHandler(RegistroDuplicadoException.class)
	public ResponseEntity<?> catchRegistroDuplicadoErro(RegistroDuplicadoException erro){
		ErrorResponse error = new ErrorResponse(erro.getMessage(), HttpStatusCode.valueOf(409), "409");
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratarErroValidation(MethodArgumentNotValidException exception) {
		var erros = exception.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
//		var erros = exception.getFieldErrors();
//		List<ErrorResponse> errorResponseList = erros.stream().map(erro -> {
//		    return new ErrorResponse(erro.getDefaultMessage(), HttpStatusCode.valueOf(400), "400");
//		}).collect(Collectors.toList());
//		return ResponseEntity.badRequest().body(errorResponseList);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> tratarErroFormatoData(HttpMessageNotReadableException exception) {
		String txt = exception.getCause().getMessage();
		System.out.println(txt);
		 if (txt.contains("valor")) {
			 	ErrorResponse error = new ErrorResponse("Campo 'valor' está em formato incorreto. Formato esperado: 12345.67", HttpStatusCode.valueOf(400), "400");
	            return ResponseEntity.badRequest().body(error);
	        } else if (txt.contains("data")) {
	        	ErrorResponse error = new ErrorResponse("Campo 'data' está em formato incorreto. Formato esperado: dd/MM/aaaa", HttpStatusCode.valueOf(400), "400");
	            return ResponseEntity.badRequest().body(error);
	        } else {
	        	ErrorResponse error = new ErrorResponse("Erro de formato desconhecido.", HttpStatusCode.valueOf(400), "400");
	            return ResponseEntity.badRequest().body(error);
	        }
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratarErroEntidadeNaoEncontrada(EntityNotFoundException exception) {
		ErrorResponse error = new ErrorResponse("Entidade não encontrada.", HttpStatusCode.valueOf(400), "400");
		return ResponseEntity.badRequest().body(error);
	}
	
//	@ExceptionHandler(IllegalArgumentException.class)
//	public ResponseEntity<?> tratarErroCategoriaInvalida(IllegalArgumentException exception) {
//		ErrorResponse error = new ErrorResponse("Categoria não encontrada. Parâmetros aceitos para categoria: 'ALIMENTACAO, SAUDE, TRANSPORTE, EDUCAOCAO, LAZER, IMPREVISTOS, OUTROS'", HttpStatusCode.valueOf(400), "400");
//		return ResponseEntity.badRequest().body(error);
//	}
}
