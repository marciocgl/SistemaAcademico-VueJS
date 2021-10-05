package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.PresencaAluno;
import edu.ifes.ci.si.les.sa.model.PresencaAlunoPK;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;
import edu.ifes.ci.si.les.sa.repositories.AlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.AlunoTurmaRepository;
import edu.ifes.ci.si.les.sa.repositories.AulaRepository;
import edu.ifes.ci.si.les.sa.repositories.PresencaAlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.TurmaRepository;



@Service
public class PresencaAlunoService {

	@Autowired
	private PresencaAlunoRepository repository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private AlunoTurmaService alunoTurmaService;
	
	@Autowired
	private AlunoTurmaRepository alunoTurmaRepository;
	
	public PresencaAluno findById(PresencaAlunoPK id) {
		PresencaAluno obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + PresencaAluno.class.getName());
		}
		return obj;
	}

	public Collection<PresencaAluno> findAll() {
		return repository.findAll();
	}

	public PresencaAluno insert(PresencaAluno obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public PresencaAluno update(PresencaAluno obj) {
		findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
		}
	}

	public void delete(PresencaAlunoPK id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item associado a outros!");
		}
	}
	
	
	public Double presencaAluno(Integer turmaId, Integer alunoId) {
		Integer aulasPorTurma = turmaRepository.countAulaByTurma(turmaId);
		Integer alunoPresenteTurma = alunoRepository.countPresencaAulaByTurma(turmaId, alunoId);
		
		double percentualPresenca = ( (double)alunoPresenteTurma / (double)aulasPorTurma);
		
		if(percentualPresenca < 0.4) {
			alunoTurmaRepository.reprovaAluno(turmaId, alunoId);
		}else if(percentualPresenca > 0.4) {
			alunoTurmaRepository.alteraStatusAprovacaoPendente(turmaId, alunoId);
		}
		
		
		System.out.println("Aluno ID: "+alunoId);
		System.out.println("Turma ID: "+turmaId);
		
		System.out.println("Valor BANCO AULA TURMA: " + (double) aulasPorTurma);
		System.out.println("Valor BANCO AlunoPresente: " + (double) alunoPresenteTurma);
		
		System.out.println ((double) alunoPresenteTurma / (double) aulasPorTurma);
		return (double) ( (double)alunoPresenteTurma / (double)aulasPorTurma);
		
	}

}
