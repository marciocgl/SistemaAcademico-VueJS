package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.AtividadeAvaliativa;
import edu.ifes.ci.si.les.sa.model.Disciplina;
import edu.ifes.ci.si.les.sa.repositories.AtividadeAvaliativaRepository;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;



@Service
public class AtividadeAvaliativaService {

	@Autowired
	private AtividadeAvaliativaRepository repository;

	public AtividadeAvaliativa findById(Integer id) {
		AtividadeAvaliativa obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + AtividadeAvaliativa.class.getName());
		}
		return obj;
	}

	public Collection<AtividadeAvaliativa> findAll() {
		return repository.findAll();
	}
	
	public Collection<AtividadeAvaliativa> findByDisciplina(Disciplina disciplina) {
		return repository.findByDisciplina(disciplina);
	}

	public AtividadeAvaliativa insert(AtividadeAvaliativa obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public AtividadeAvaliativa update(AtividadeAvaliativa obj) {
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
