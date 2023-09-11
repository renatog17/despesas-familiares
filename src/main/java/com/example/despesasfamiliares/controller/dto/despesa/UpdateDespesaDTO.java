package com.example.despesasfamiliares.controller.dto.despesa;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record UpdateDespesaDTO(
		String descricao,
		Float valor,
		@JsonFormat(pattern = "dd/MM/yyyy")
		LocalDate data,
		String categoria) {
}
