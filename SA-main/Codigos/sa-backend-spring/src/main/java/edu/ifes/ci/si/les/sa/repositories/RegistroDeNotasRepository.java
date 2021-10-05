package edu.ifes.ci.si.les.sa.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.sa.model.Aluno;
import edu.ifes.ci.si.les.sa.model.Disciplina;
import edu.ifes.ci.si.les.sa.model.RegistroDeNotas;

@Repository
public interface RegistroDeNotasRepository extends JpaRepository<RegistroDeNotas, Integer>{

	@Transactional(readOnly = true)
	@Query(value = "SELECT * FROM registro_de_notas WHERE aluno_id = ?1 AND disciplina_id = ?2", nativeQuery = true)
	public Collection<RegistroDeNotas> findByAlunoDisc(Integer alunoID, Integer disciplinaID);
}
