package com.example.despesasfamiliares.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.example.despesasfamiliares.controller.dto.despesa.CreateDespesaDTO;
import com.example.despesasfamiliares.controller.dto.despesa.ReadDespesaDTO;
import com.example.despesasfamiliares.controller.dto.despesa.UpdateDespesaDTO;
import com.example.despesasfamiliares.controller.exceptionhandler.RegistroDuplicadoException;
import com.example.despesasfamiliares.domain.Despesa;
import com.example.despesasfamiliares.repository.DespesaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	DespesaRepository despesaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> postDespesa(@RequestBody @Valid CreateDespesaDTO newDespesa,
			UriComponentsBuilder uriBuilder) throws RegistroDuplicadoException {
		Despesa despesa = new Despesa(newDespesa);
		System.out.println(despesa.getCategoria());
		List<Despesa> despesasByDescricao = despesaRepository.findByDescricao(despesa.getDescricao());
		for (Despesa r : despesasByDescricao) {
			if (r.getDescricao().equals(despesa.getDescricao())
					&& r.getData().getMonth() == despesa.getData().getMonth()
					&& r.getData().getYear() == despesa.getData().getYear())
				throw new RegistroDuplicadoException("despesa já foi criada para este mês");
		}
		despesaRepository.save(despesa);
		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(despesa);
	}

	@GetMapping()
	public ResponseEntity<?> getdespesas() {
		List<ReadDespesaDTO> despesasDto = despesaRepository.findAll().stream().map(despesa -> {
			ReadDespesaDTO despesaDto = new ReadDespesaDTO(despesa);
			return despesaDto;
		}).toList();
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAa");
		return ResponseEntity.ok(despesasDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDespesa(@PathVariable Long id) {
		Despesa despesa = despesaRepository.getReferenceById(id);
		return ResponseEntity.ok(new ReadDespesaDTO(despesa));
	}

	@GetMapping("/lista")
	public ResponseEntity<?> getdespesas(@RequestParam String descricao) {
		List<ReadDespesaDTO> despesasDto = despesaRepository.findByDescricao(descricao).stream().map(despesa -> {
			ReadDespesaDTO despesaDto = new ReadDespesaDTO(despesa);
			return despesaDto;
		}).toList();
		return ResponseEntity.ok(despesasDto);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> putDespesa(@PathVariable Long id, @RequestBody UpdateDespesaDTO despesaDto) {
		Despesa despesa = despesaRepository.getReferenceById(id);
		despesa.update(despesaDto);
		return ResponseEntity.ok(new ReadDespesaDTO(despesa));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public void deleteDespesa(@PathVariable Long id) {
		despesaRepository.deleteById(id);
	}
}
