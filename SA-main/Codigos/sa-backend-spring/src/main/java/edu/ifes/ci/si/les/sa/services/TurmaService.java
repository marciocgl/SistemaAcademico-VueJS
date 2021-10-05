package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.model.Turma;
import edu.ifes.ci.si.les.sa.repositories.TurmaRepository;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;



@Service
public class TurmaService {

	@Autowired
	private TurmaRepository repository;
	
	
	public Turma findById(Integer id) {
		Turma obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Turma.class.getName());
		}
		return obj;
	}

	public Collection<Turma> findAll() {
		return repository.findAll();
	}

	public Collection<Turma> findByCurso(Curso curso) {
		return repository.findByCurso(curso);
	}
	
	public Turma insert(Turma obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public Turma update(Turma obj) {
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
