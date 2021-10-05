package edu.ifes.ci.si.les.sa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ifes.ci.si.les.sa.model.AlunoTurma;
import edu.ifes.ci.si.les.sa.model.Turma;
import edu.ifes.ci.si.les.sa.services.RegistroAprovacaoService;
import edu.ifes.ci.si.les.sa.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/api/v1/registroAprovacao")
public class RegistroAprovacaoController {

	@Autowired
	private RegistroAprovacaoService service;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> insert(@Valid @RequestBody List<AlunoTurma> listObj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		String ret = service.salvaRegistroAprovacao(listObj);
		return ResponseEntity.ok().body(ret);
	}
}
