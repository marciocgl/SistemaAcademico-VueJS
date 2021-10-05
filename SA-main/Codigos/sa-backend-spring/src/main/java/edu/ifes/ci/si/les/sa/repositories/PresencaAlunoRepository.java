package edu.ifes.ci.si.les.sa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifes.ci.si.les.sa.model.PresencaAluno;
import edu.ifes.ci.si.les.sa.model.PresencaAlunoPK;

@Repository
public interface PresencaAlunoRepository extends JpaRepository<PresencaAluno, PresencaAlunoPK>{

}
