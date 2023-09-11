package com.example.despesasfamiliares.controller.dto.despesa;

import java.time.LocalDate;

import com.example.despesasfamiliares.domain.Despesa;
import com.example.despesasfamiliares.domain.enums.Categoria;

public record ReadDespesaDTO(Long id,
		String descricao,
		Float valor,
		LocalDate data,
		String categoria) {
	public ReadDespesaDTO(Despesa despesa) {
		this(despesa.getId(), despesa.getDescricao(), despesa.getValor(), despesa.getData(), despesa.getCategoria().name());
	}
}
