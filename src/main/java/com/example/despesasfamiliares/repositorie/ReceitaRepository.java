package com.example.despesasfamiliares.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.despesasfamiliares.domain.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{

	List<Receita> findByDescricao(String descricao);
}
