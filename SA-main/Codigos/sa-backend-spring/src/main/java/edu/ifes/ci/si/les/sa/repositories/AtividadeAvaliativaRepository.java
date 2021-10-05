package edu.ifes.ci.si.les.sa.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.sa.model.AtividadeAvaliativa;
import edu.ifes.ci.si.les.sa.model.Disciplina;

@Repository
public interface AtividadeAvaliativaRepository extends JpaRepository<AtividadeAvaliativa, Integer>{

	@Transactional(readOnly = true)
    public Collection<AtividadeAvaliativa> findByDisciplina(Disciplina disciplina);
}
