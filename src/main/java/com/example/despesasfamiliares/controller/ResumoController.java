package com.example.despesasfamiliares.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.despesasfamiliares.controller.dto.resumo.ReadResumoDTO;
import com.example.despesasfamiliares.domain.Despesa;
import com.example.despesasfamiliares.domain.Receita;
import com.example.despesasfamiliares.repository.DespesaRepository;
import com.example.despesasfamiliares.repository.ReceitaRepository;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

	@Autowired
	ReceitaRepository receitaRepository;
	@Autowired
	DespesaRepository despesaRepository;
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<?> getResumoMes(@PathVariable Integer ano, @PathVariable Integer mes){
		LocalDate dataInicio = LocalDate.of(ano, mes, 1);
		LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
		List<Receita> receitas = receitaRepository.findByDataBetween(dataInicio, dataFim);
		List<Despesa> despesas = despesaRepository.findByDataBetween(dataInicio, dataFim);
		
		Map<String, Float> despesasPorCategoria = new HashMap<>(); 
		Float valorTotalDespesas = 0F;
		for (Despesa despesa : despesas) {
			valorTotalDespesas +=  despesa.getValor();		
			if(despesasPorCategoria.containsKey(despesa.getCategoria().toString())) {
				Float valor = despesasPorCategoria.get(despesa.getCategoria().toString());
				despesasPorCategoria.put(despesa.getCategoria().toString(), valor+despesa.getValor());
			}else {
				despesasPorCategoria.put(despesa.getCategoria().toString(), despesa.getValor());
			}
		}				
		Float valorTotalReceitas = 0F;
		for (Receita receita : receitas) {
			valorTotalReceitas += receita.getValor();
		}
		
		ReadResumoDTO resumoDto = new ReadResumoDTO(valorTotalReceitas, valorTotalDespesas, valorTotalReceitas-valorTotalDespesas, despesasPorCategoria);
		return ResponseEntity.ok(resumoDto);
	}
}
