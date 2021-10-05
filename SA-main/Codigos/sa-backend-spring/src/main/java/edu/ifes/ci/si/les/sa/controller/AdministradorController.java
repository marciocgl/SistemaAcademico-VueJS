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

import edu.ifes.ci.si.les.sa.model.Administrador;
import edu.ifes.ci.si.les.sa.model.Aluno;
import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.services.AdministradorService;
import edu.ifes.ci.si.les.sa.services.AlunoService;
import edu.ifes.ci.si.les.sa.services.AulaService;
import edu.ifes.ci.si.les.sa.services.CursoService;
import edu.ifes.ci.si.les.sa.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/api/v1/administrador")
public class AdministradorController {

	@Autowired
	private AdministradorService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Administrador>> findAll() {
		Collection<Administrador> collection = service.findAll();
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Administrador> find(@PathVariable Integer id) {
		Administrador obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Administrador> insert(@Valid @RequestBody Administrador obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj.setTipoUsuario(2);
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Administrador> update(@Valid @RequestBody Administrador obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Administrador id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
