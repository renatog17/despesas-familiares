package com.example.despesasfamiliares.controller.dto.receita;

import java.time.LocalDate;

import com.example.despesasfamiliares.domain.Receita;

public record ReadReceitaDTO(Long id,
		String descricao,
		Float valor,
		LocalDate data) {
	public ReadReceitaDTO(Receita receita) {
		this(receita.getId(), receita.getDescricao(), receita.getValor(), receita.getData());
	}
}
