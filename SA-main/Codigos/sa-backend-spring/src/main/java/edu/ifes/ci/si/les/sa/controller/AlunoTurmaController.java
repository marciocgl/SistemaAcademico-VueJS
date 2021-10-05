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

import edu.ifes.ci.si.les.sa.model.AlunoTurma;
import edu.ifes.ci.si.les.sa.model.AlunoTurmaPK;
import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.model.Turma;
import edu.ifes.ci.si.les.sa.services.AlunoTurmaService;
import edu.ifes.ci.si.les.sa.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/api/v1/alunoTurma")
public class AlunoTurmaController {

	@Autowired
	private AlunoTurmaService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<AlunoTurma>> findAll() {
		Collection<AlunoTurma> collection = service.findAll();
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AlunoTurma> find(@PathVariable AlunoTurmaPK id) {
		AlunoTurma obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<AlunoTurma> update(@Valid @RequestBody AlunoTurma obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/findByTurma/{id}", method = RequestMethod.GET)
	public ResponseEntity<Collection<AlunoTurma>> findByTurma(@PathVariable Integer id) {
		Collection<AlunoTurma> collection = service.findByTurma(id);
		return ResponseEntity.ok().body(collection);
	}
	
	@RequestMapping(value = "/findByCurso/{id}", method = RequestMethod.GET)
	public ResponseEntity<Collection<AlunoTurma>> findByCurso(@PathVariable Integer id) {
		Collection<AlunoTurma> collection = service.findByCurso(id);
		return ResponseEntity.ok().body(collection);
	}
	@RequestMapping(value = "/findQtdAlunoTotal", method = RequestMethod.GET)
	public ResponseEntity<Collection<AlunoTurma>> findQtdAlunoTotal() {
		Collection<AlunoTurma> collection = service.findQtdAlunoTotal();
		return ResponseEntity.ok().body(collection);
	}
	
	@RequestMapping(value = "/findQtdAlunoTotalPorCurso/{id}", method = RequestMethod.GET)
	public ResponseEntity<Collection<AlunoTurma>> findQtdAlunoTotalPorCurso(@PathVariable Integer id) {
		Collection<AlunoTurma> collection = service.findQtdAlunoTotalPorCurso(id);
		return ResponseEntity.ok().body(collection);
	}
	
	@RequestMapping(value = "/findQtdAlunoTotalPorTurma/{id}", method = RequestMethod.GET)
	public ResponseEntity<Collection<AlunoTurma>> findQtdAlunoTotalPorTurma(@PathVariable Integer id) {
		Collection<AlunoTurma> collection = service.findQtdAlunoTotalPorTurma(id);
		return ResponseEntity.ok().body(collection);
	}
}
