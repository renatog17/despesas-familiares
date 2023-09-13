package com.example.despesasfamiliares.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.despesasfamiliares.domain.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{

	List<Receita> findByDescricao(String descricao);

	List<Receita> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
}
