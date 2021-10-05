package edu.ifes.ci.si.les.sa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.sa.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

	
	@Transactional(readOnly = true)
	@Query(value = "SELECT COUNT(qtd_aula) AS qtdAulaTurma FROM presenca_aluno WHERE turma_id = ?1 AND aluno_id = ?2 AND presenca = true", nativeQuery = true)//Pensar em uma regra de select diferente 
	public Integer countPresencaAulaByTurma(Integer turmaId, Integer alunoId);
}
