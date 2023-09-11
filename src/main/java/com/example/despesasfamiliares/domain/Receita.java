package com.example.despesasfamiliares.domain;

import java.time.LocalDate;
import java.util.Objects;

import com.example.despesasfamiliares.controller.dto.CreateReceitaDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "receitas")
public class Receita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private Float valor;
	private LocalDate data;

	public Receita() {
	}

	public Receita(Long id, String descricao, Float valor, LocalDate data) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
	}

	public Receita(CreateReceitaDTO newReceita) {
		this.descricao = newReceita.descricao();
		this.valor = newReceita.valor();
		this.data = newReceita.data();
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public Float getValor() {
		return valor;
	}

	public LocalDate getData() {
		return data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receita other = (Receita) obj;
		return Objects.equals(id, other.id);
	}

	
}
