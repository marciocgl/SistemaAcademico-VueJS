package edu.ifes.ci.si.les.sa.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ifes.ci.si.les.sa.model.AtividadeAvaliativa;
import edu.ifes.ci.si.les.sa.model.Disciplina;
import edu.ifes.ci.si.les.sa.services.AtividadeAvaliativaService;
import edu.ifes.ci.si.les.sa.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/api/v1/atividadeAvaliativa")
public class AtividadeAvaliativaController {

	@Autowired
	private AtividadeAvaliativaService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<AtividadeAvaliativa>> findAll() {
		Collection<AtividadeAvaliativa> collection = service.findAll();
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AtividadeAvaliativa> find(@PathVariable Integer id) {
		AtividadeAvaliativa obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/findByDisciplina/{id}", method = RequestMethod.GET)
	public ResponseEntity<Collection<AtividadeAvaliativa>> findByDisciplina(@PathVariable Integer id) {
		Disciplina disciplina = new Disciplina();
		disciplina.setId(id);
		Collection<AtividadeAvaliativa> collection = service.findByDisciplina(disciplina);
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<AtividadeAvaliativa> insert(@Valid @RequestBody AtividadeAvaliativa obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<AtividadeAvaliativa> update(@Valid @RequestBody AtividadeAvaliativa obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
