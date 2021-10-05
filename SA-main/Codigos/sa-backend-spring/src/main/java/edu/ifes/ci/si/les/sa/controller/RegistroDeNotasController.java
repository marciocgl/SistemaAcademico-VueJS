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

import edu.ifes.ci.si.les.sa.model.Aluno;
import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.model.Disciplina;
import edu.ifes.ci.si.les.sa.model.RegistroDeNotas;
import edu.ifes.ci.si.les.sa.model.Turma;
import edu.ifes.ci.si.les.sa.services.CursoService;
import edu.ifes.ci.si.les.sa.services.RegistroDeNotasService;
import edu.ifes.ci.si.les.sa.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/api/v1/registroDeNotas")
public class RegistroDeNotasController {

	@Autowired
	private RegistroDeNotasService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<RegistroDeNotas>> findAll() {
		Collection<RegistroDeNotas> collection = service.findAll();
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<RegistroDeNotas> find(@PathVariable Integer id) {
		RegistroDeNotas obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/findByAlunoDisc/{alunoID}/{disciplinaID}", method = RequestMethod.GET)
	public ResponseEntity<Collection<RegistroDeNotas>> findByAlunoDisc(@PathVariable Integer alunoID, @PathVariable Integer disciplinaID) {
		Collection<RegistroDeNotas> collection = service.findByAlunoDisc(alunoID, disciplinaID);
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<RegistroDeNotas> insert(@Valid @RequestBody RegistroDeNotas obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<RegistroDeNotas> update(@Valid @RequestBody RegistroDeNotas obj, BindingResult br) {
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
