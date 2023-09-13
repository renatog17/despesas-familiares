package com.example.despesasfamiliares.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.despesasfamiliares.domain.Despesa;
import com.example.despesasfamiliares.domain.Receita;
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	List<Despesa> findByDescricao(String descricao);
	
	List<Despesa> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
}
