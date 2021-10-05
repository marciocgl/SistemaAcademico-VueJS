package edu.ifes.ci.si.les.sa.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.AlunoTurma;
import edu.ifes.ci.si.les.sa.model.AtividadeAvaliativa;
import edu.ifes.ci.si.les.sa.model.RegistroDeNotas;
import edu.ifes.ci.si.les.sa.model.Turma;
import edu.ifes.ci.si.les.sa.services.exceptions.BusinessRuleException;


@Service
public class RegistroAprovacaoService {
	
	@Autowired
	private AtividadeAvaliativaService ativAvalService;
	
	@Autowired
	private RegistroDeNotasService regisNotaService;
	
	@Autowired
	private AlunoTurmaService alunoTurmaService;

	public String salvaRegistroAprovacao(List<AlunoTurma> listAlunoTurma) {
		String msg = "";
		if(listAlunoTurma != null && !listAlunoTurma.isEmpty()) {
			Turma turma = listAlunoTurma.get(0).getTurma();
			Collection<RegistroDeNotas> listRegistroNotasAluno = new ArrayList<RegistroDeNotas>();
			Collection<AtividadeAvaliativa> listAtivAval = ativAvalService.findByDisciplina(turma.getDisciplina());
			if(listAtivAval != null && !listAtivAval.isEmpty()) {
				Double somaAtivAval = 0.0;
				for(AtividadeAvaliativa ativAval : listAtivAval) {
					somaAtivAval += ativAval.getValor();
				}
				if(somaAtivAval < 100.0) {
					throw new BusinessRuleException("As avaliações dessa turma não somam 100.0 pontos!");
				}

				for(AlunoTurma alunTurm : listAlunoTurma) {
					listRegistroNotasAluno = regisNotaService.findByAlunoDisc(alunTurm.getAluno().getId(), turma.getDisciplina().getId());
					if(listRegistroNotasAluno != null && !listRegistroNotasAluno.isEmpty()) {
						if(listRegistroNotasAluno.size() < listAtivAval.size()) {
							msg += "Aluno: " + alunTurm.getAluno().getNome() + " não tem todas as notas lançadas! \n";
						}
					}else {
						msg += "Aluno: " + alunTurm.getAluno().getNome() + " não tem registros de nota! \n";
					}
				}
				if(msg != null && !msg.isEmpty()) {
					throw new BusinessRuleException(msg);
				}else {
					for(AlunoTurma alunTurm : listAlunoTurma) {
						alunoTurmaService.update(alunTurm);
					}
					msg = "Registro de Aprovação Salvo com Sucesso!";
				}
				
			}else {
				throw new BusinessRuleException("Essa turma não tem nenhuma avaliação cadastrada!");
			}
		}
		return msg;
	}
}
