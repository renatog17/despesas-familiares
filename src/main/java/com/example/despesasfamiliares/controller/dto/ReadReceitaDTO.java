package com.example.despesasfamiliares.controller.dto;

import java.time.LocalDate;

import com.example.despesasfamiliares.domain.Receita;

public record ReadReceitaDTO(String descricao,
		Float valor,
		LocalDate data) {
	public ReadReceitaDTO(Receita receita) {
		this(receita.getDescricao(), receita.getValor(), receita.getData());
	}
}
