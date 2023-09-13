package com.example.despesasfamiliares.controller.dto.resumo.util;

import java.util.Objects;

public class CategoriaResumo {

	private String categoria;
	private Float valor = 0F;

	public CategoriaResumo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoriaResumo(String categoria) {
		super();
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public Float getValor() {
		return valor;
	}
	
	public void incrementarValor(Float valor) {
		this.valor += valor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaResumo other = (CategoriaResumo) obj;
		return Objects.equals(categoria, other.categoria);
	}

	
	
}
