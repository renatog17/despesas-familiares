package com.example.despesasfamiliares.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.despesasfamiliares.controller.dto.receita.CreateReceitaDTO;
import com.example.despesasfamiliares.controller.dto.receita.ReadReceitaDTO;
import com.example.despesasfamiliares.controller.dto.receita.UpdateReceitaDTO;
import com.example.despesasfamiliares.controller.exceptionhandler.RegistroDuplicadoException;
import com.example.despesasfamiliares.domain.Receita;
import com.example.despesasfamiliares.repository.ReceitaRepository;

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
	
	@GetMapping
	public ResponseEntity<?> getReceitas(){
		List<ReadReceitaDTO> receitasDto = receitaRepository.findAll().stream().map(
				receita -> {
					ReadReceitaDTO receitaDto = new ReadReceitaDTO(receita);
					return receitaDto;
				}).toList();
		return ResponseEntity.ok(receitasDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getReceita(@PathVariable Long id){
		Receita receita = receitaRepository.getReferenceById(id);
		return ResponseEntity.ok(new ReadReceitaDTO(receita));
	}
	@GetMapping("/lista")
	public ResponseEntity<?> getReceitas(@RequestParam String descricao) {
		List<ReadReceitaDTO> receitasDto = receitaRepository.findByDescricao(descricao).stream().map(receita -> {
			ReadReceitaDTO receitaDto = new ReadReceitaDTO(receita);
			return receitaDto;
		}).toList();
		return ResponseEntity.ok(receitasDto);
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<?> getReceitasPorData(@PathVariable Integer ano, @PathVariable Integer mes){
		LocalDate dataInicio = LocalDate.of(ano, mes, 1);
		LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
		List<ReadReceitaDTO> receitasDto = receitaRepository.findByDataBetween(dataInicio, dataFim).stream().map(receita -> {
			ReadReceitaDTO receitaDto = new ReadReceitaDTO(receita);
			return receitaDto;
		}).toList();
		return ResponseEntity.ok(receitasDto);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> putReceita(@PathVariable Long id, @RequestBody UpdateReceitaDTO receitaDto){
		Receita receita = receitaRepository.getReferenceById(id);
		receita.update(receitaDto);
		return ResponseEntity.ok(new ReadReceitaDTO(receita));
	}
	@DeleteMapping("/{id}")
	@Transactional
	public void deleteReceita(@PathVariable Long id) {
		receitaRepository.deleteById(id);
	}
}
