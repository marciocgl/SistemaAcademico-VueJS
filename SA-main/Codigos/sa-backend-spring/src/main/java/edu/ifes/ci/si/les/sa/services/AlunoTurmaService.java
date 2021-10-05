package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Aluno;
import edu.ifes.ci.si.les.sa.model.AlunoTurma;
import edu.ifes.ci.si.les.sa.model.AlunoTurmaPK;
import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.PresencaAluno;
import edu.ifes.ci.si.les.sa.model.PresencaAlunoPK;
import edu.ifes.ci.si.les.sa.model.Turma;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;
import edu.ifes.ci.si.les.sa.repositories.AlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.AlunoTurmaRepository;
import edu.ifes.ci.si.les.sa.repositories.AulaRepository;
import edu.ifes.ci.si.les.sa.repositories.PresencaAlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.TurmaRepository;



@Service
public class AlunoTurmaService {

	@Autowired
	private AlunoTurmaRepository repository;
	
	public AlunoTurma findById(AlunoTurmaPK id) {
		AlunoTurma obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Aluno.class.getName());
		}
		return obj;
	}

	public Collection<AlunoTurma> findAll() {
		return repository.findAll();
	}

	public Collection<AlunoTurma> findByTurma(Integer turmaID){
		return repository.findByTurma(turmaID);
	}
	
	public Collection<AlunoTurma> findByCurso(Integer cursoID){
		return repository.findByCurso(cursoID);
	}
	
	public Collection<AlunoTurma> findQtdAlunoTotal(){
		return repository.findQtdAlunoTotal();
	}
	
	public Collection<AlunoTurma> findQtdAlunoTotalPorCurso(Integer cursoID){
		return repository.findQtdAlunoTotalPorCurso(cursoID);
	}
	
	public Collection<AlunoTurma> findQtdAlunoTotalPorTurma(Integer turmaID){
		return repository.findQtdAlunoTotalPorTurma(turmaID);
	}
	
	public AlunoTurma insert(AlunoTurma obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public AlunoTurma update(AlunoTurma obj) {
		findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
		}
	}

	public void delete(AlunoTurma obj) {
		findById(obj.getId());
		try {
			repository.deleteById(obj.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item associado a outros!");
		}
	}
	

}
