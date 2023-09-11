package com.example.despesasfamiliares.domain;

import java.time.LocalDate;
import java.util.Objects;

import com.example.despesasfamiliares.controller.dto.despesa.CreateDespesaDTO;
import com.example.despesasfamiliares.controller.dto.despesa.UpdateDespesaDTO;
import com.example.despesasfamiliares.domain.enums.Categoria;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;

@Entity
@Table(name = "despesas")
public class Despesa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private Float valor;
	private LocalDate data;
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	public Despesa() {
	}

	public Despesa(Long id, String descricao, Float valor, LocalDate data, Categoria categoria) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.categoria = categoria;
	}

	public Despesa(CreateDespesaDTO newDespesa) {
		this.descricao = newDespesa.descricao();
		this.valor = newDespesa.valor();
		this.data = newDespesa.data();
		this.categoria = Categoria.valueOf(newDespesa.categoria());
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

	public Categoria getCategoria() {
		return categoria;
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
		Despesa other = (Despesa) obj;
		return Objects.equals(id, other.id);
	}

	public void update(@Valid UpdateDespesaDTO despesaDto) {
		if(despesaDto.descricao()!=null)
			this.descricao = despesaDto.descricao();
		if(despesaDto.valor()!=null)
			this.valor = despesaDto.valor();
		if(despesaDto.data()!=null)
			this.data = despesaDto.data();
		if(despesaDto.categoria()!=null)
			this.categoria = Categoria.valueOf(despesaDto.categoria());
	}

	
}
