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

import edu.ifes.ci.si.les.sa.model.Cidade;
import edu.ifes.ci.si.les.sa.model.Uf;
import edu.ifes.ci.si.les.sa.services.CidadeService;
import edu.ifes.ci.si.les.sa.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/api/v1/cidade")
public class CidadeController {

	@Autowired
	private CidadeService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Cidade>> findAll() {
		Collection<Cidade> collection = service.findAll();
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(value = "/findByUf/{id}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Cidade>> findByUf(@PathVariable Integer id) {
		Uf uf = new Uf();
		uf.setId(id);
		Collection<Cidade> collection = service.findByUf(uf);
		return ResponseEntity.ok().body(collection);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cidade> find(@PathVariable Integer id) {
		Cidade obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Cidade> insert(@Valid @RequestBody Cidade obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Cidade> update(@Valid @RequestBody Cidade obj, BindingResult br) {
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
