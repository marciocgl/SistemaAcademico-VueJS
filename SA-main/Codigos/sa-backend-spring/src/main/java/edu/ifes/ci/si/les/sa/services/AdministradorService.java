package edu.ifes.ci.si.les.sa.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Administrador;
import edu.ifes.ci.si.les.sa.model.Aluno;
import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.PresencaAluno;
import edu.ifes.ci.si.les.sa.model.PresencaAlunoPK;
import edu.ifes.ci.si.les.sa.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.sa.services.exceptions.ObjectNotFoundException;
import edu.ifes.ci.si.les.sa.repositories.AdministradorRepository;
import edu.ifes.ci.si.les.sa.repositories.AlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.AulaRepository;
import edu.ifes.ci.si.les.sa.repositories.PresencaAlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.TurmaRepository;



@Service
public class AdministradorService {

	@Autowired
	private AdministradorRepository repository;
	
	public Administrador findById(Integer id) {
		Administrador obj = repository.findById(id).get();
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Aluno.class.getName());
		}
		return obj;
	}

	public Collection<Administrador> findAll() {
		return repository.findAll();
	}

	public Administrador insert(Administrador obj) {
		obj.setId(null);
		obj.setTipoUsuario(1); //administrador é usuário 1
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do não foi(foram) preenchido(s)");
		}
	}

	public Administrador update(Administrador obj) {
		findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) não foi(foram) preenchido(s)");
		}
	}

	public void delete(Administrador obj) {
		findById(obj.getId());
		try {
			repository.deleteById(obj.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um item associado a outros!");
		}
	}

}
