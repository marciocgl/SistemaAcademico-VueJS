package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Aluno;
import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.PresencaAluno;
import edu.ifes.ci.si.les.sa.model.PresencaAlunoPK;
import edu.ifes.ci.si.les.sa.model.Professor;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;
import edu.ifes.ci.si.les.sa.repositories.AlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.AulaRepository;
import edu.ifes.ci.si.les.sa.repositories.PresencaAlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.ProfessorRepository;
import edu.ifes.ci.si.les.sa.repositories.TurmaRepository;



@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository repository;
	
	public Professor findById(Integer id) {
		Professor obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + PresencaAluno.class.getName());
		}
		return obj;
	}

	public Collection<Professor> findAll() {
		return repository.findAll();
	}

	public Professor insert(Professor obj) {
		obj.setId(null);
		obj.setTipoUsuario(2); //professor é usuário 2
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public Professor update(Professor obj) {
		findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
		}
	}

	public void delete(Professor obj) {
		findById(obj.getId());
		try {
			repository.deleteById(obj.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item associado a outros!");
		}
	}

}
