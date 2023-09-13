package com.example.despesasfamiliares.controller.dto.resumo;

import java.util.Map;

public record ReadResumoDTO(Float valorTotalReceitas,
		Float valorTotalDespesas,
		Float saldoFinal,
		Map<String, Float> despesasPorCategoria) {

}
