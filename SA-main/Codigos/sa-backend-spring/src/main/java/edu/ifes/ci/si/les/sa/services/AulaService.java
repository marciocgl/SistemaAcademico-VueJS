package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;
import edu.ifes.ci.si.les.sa.repositories.AulaRepository;
import edu.ifes.ci.si.les.sa.repositories.CursoRepository;



@Service
public class AulaService {

	@Autowired
	private AulaRepository repository;

	public Aula findById(Integer id) {
		Aula obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Curso.class.getName());
		}
		return obj;
	}

	public Collection<Aula> findAll() {
		return repository.findAll();
	}

	public Aula insert(Aula obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public Aula update(Aula obj) {
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
