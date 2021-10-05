package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.AvaliacaoAluno;
import edu.ifes.ci.si.les.sa.model.AvaliacaoAlunoPK;
import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;
import edu.ifes.ci.si.les.sa.repositories.AvaliacaoAlunoRepository;



@Service
public class AvaliacaoAlunoService {

	@Autowired
	private AvaliacaoAlunoRepository repository;

	public AvaliacaoAluno findById(AvaliacaoAlunoPK id) {
		AvaliacaoAluno obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + AvaliacaoAluno.class.getName());
		}
		return obj;
	}

	public Collection<AvaliacaoAluno> findAll() {
		return repository.findAll();
	}

	public AvaliacaoAluno insert(AvaliacaoAluno obj) {
		//obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public AvaliacaoAluno update(AvaliacaoAluno obj) {
	//	findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
		}
	}

	public void delete(AvaliacaoAlunoPK id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item associado a outros!");
		}
	}

}
