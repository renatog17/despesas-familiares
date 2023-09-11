package com.example.despesasfamiliares.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.despesasfamiliares.domain.Despesa;
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	List<Despesa> findByDescricao(String descricao);
}
