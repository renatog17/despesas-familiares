package com.example.despesasfamiliares.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.despesasfamiliares.controller.dto.CreateReceitaDTO;
import com.example.despesasfamiliares.controller.exceptionhandler.RegistroDuplicadoException;
import com.example.despesasfamiliares.domain.Receita;
import com.example.despesasfamiliares.repositorie.ReceitaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

	@Autowired
	ReceitaRepository receitaRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> postReceita(@RequestBody @Valid CreateReceitaDTO newReceita, UriComponentsBuilder uriBuilder) throws RegistroDuplicadoException{
		Receita receita = new Receita(newReceita);
		List<Receita> receitasByDescricao = receitaRepository.findByDescricao(receita.getDescricao());
		for (Receita r: receitasByDescricao) {
			if(r.getDescricao().equals(receita.getDescricao()) &&
					r.getData().getMonth() == receita.getData().getMonth() &&
					r.getData().getYear() == receita.getData().getYear())
					throw new RegistroDuplicadoException("receita já foi criada para este mês");
		}
		receitaRepository.save(receita);
		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(receita);
	}
}
