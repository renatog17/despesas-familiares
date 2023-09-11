package com.example.despesasfamiliares.controller.dto.receita;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateReceitaDTO(
		String descricao,
		Float valor,
		@JsonFormat(pattern = "dd/MM/yyyy")
		LocalDate data) {
}
