package edu.ifes.ci.si.les.sa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifes.ci.si.les.sa.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

}
