package edu.ifes.ci.si.les.sa.controller;


import java.time.LocalDate;
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
import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.services.AlunoService;
import edu.ifes.ci.si.les.sa.services.AulaService;
import edu.ifes.ci.si.les.sa.services.CursoService;
import edu.ifes.ci.si.les.sa.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/api/v1/aluno")
public class AlunoController {

	@Autowired
	private AlunoService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Aluno>> findAll() {
		Collection<Aluno> collection = service.findAll();
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Aluno> find(@PathVariable Integer id) {
		Aluno obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Aluno> insert(@Valid @RequestBody Aluno obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj.setMatricula(gerarMatricula());
		obj.setTipoUsuario(2);
		obj.setSenha("123456");
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Aluno> update(@Valid @RequestBody Aluno obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Aluno id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	private String gerarMatricula() {
		String ano = String.valueOf(LocalDate.now().getYear());
		Integer qtd  = service.findAll().size();
		return ano+qtd.toString();
	}

}
