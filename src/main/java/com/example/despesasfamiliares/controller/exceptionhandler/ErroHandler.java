package com.example.despesasfamiliares.controller.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroHandler {

	@ExceptionHandler(RegistroDuplicadoException.class)
	public ResponseEntity catchRegistroDuplicadoErro(RegistroDuplicadoException erro){
		return ResponseEntity.badRequest().body(erro.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
		var erros = exception.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity tratarErroFormatoData(HttpMessageNotReadableException exception) {
		String txt = exception.getCause().getMessage();
		System.out.println(txt);
		 if (txt.contains("valor")) {
	            return ResponseEntity.badRequest().body("Campo 'valor' está em formato incorreto. Formato esperado: 12345.67");
	        } else if (txt.contains("data")) {
	            return ResponseEntity.badRequest().body("Campo 'data' está em formato incorreto. Formato esperado: dd/MM/aaaa");
	        } else {
	            return ResponseEntity.badRequest().body("Erro de formato desconhecido.");
	        }
	}
	
}
