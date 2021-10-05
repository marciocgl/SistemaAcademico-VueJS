package edu.ifes.ci.si.les.sa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifes.ci.si.les.sa.model.AvaliacaoAluno;
import edu.ifes.ci.si.les.sa.model.AvaliacaoAlunoPK;

@Repository
public interface AvaliacaoAlunoRepository extends JpaRepository<AvaliacaoAluno, AvaliacaoAlunoPK>{

}
