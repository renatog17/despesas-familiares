package com.example.despesasfamiliares.controller.exceptionhandler;

import org.springframework.http.HttpStatusCode;

public record ErrorResponse(String mensagem, HttpStatusCode statusCode, String code) {

     public String getMensagem() {
         return mensagem;
     }
}
