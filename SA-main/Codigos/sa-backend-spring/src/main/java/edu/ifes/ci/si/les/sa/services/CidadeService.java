package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Cidade;
import edu.ifes.ci.si.les.sa.model.Uf;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;
import edu.ifes.ci.si.les.sa.repositories.CidadeRepository;


@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;

	public Cidade findById(Integer id) {
		Cidade obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName());
		}
		return obj;
	}

	public Collection<Cidade> findAll() {
		return repository.findAll();
	}
	
	public Collection<Cidade> findByUf(Uf uf) {
		return repository.findByUf(uf);
	}

	public Cidade insert(Cidade obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public Cidade update(Cidade obj) {
		findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
		}
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item associado a outros!");
		}
	}

}
