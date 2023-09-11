package com.example.despesasfamiliares.controller.dto.receita;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReceitaDTO(
		@NotBlank(message = "Campo descricao não pode estar em branco")
		String descricao,
		@NotNull(message = "Campo valor não pode estar em branco")
		Float valor,
		@NotNull(message = "Campo data não pode estar em branco")
		@JsonFormat(pattern = "dd/MM/yyyy")
		LocalDate data) {
}
