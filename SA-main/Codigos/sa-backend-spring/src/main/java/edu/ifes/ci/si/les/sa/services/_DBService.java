package edu.ifes.ci.si.les.sa.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.sa.model.Administrador;
import edu.ifes.ci.si.les.sa.model.Aluno;
import edu.ifes.ci.si.les.sa.model.AlunoTurma;
import edu.ifes.ci.si.les.sa.model.AlunoTurmaPK;
import edu.ifes.ci.si.les.sa.model.AtividadeAvaliativa;
import edu.ifes.ci.si.les.sa.model.Aula;
import edu.ifes.ci.si.les.sa.model.AvaliacaoAluno;
import edu.ifes.ci.si.les.sa.model.AvaliacaoAlunoPK;
import edu.ifes.ci.si.les.sa.model.Bairro;
import edu.ifes.ci.si.les.sa.model.Cidade;
import edu.ifes.ci.si.les.sa.model.Curso;
import edu.ifes.ci.si.les.sa.model.Disciplina;
import edu.ifes.ci.si.les.sa.model.PeriodoLetivo;
import edu.ifes.ci.si.les.sa.model.PresencaAluno;
import edu.ifes.ci.si.les.sa.model.PresencaAlunoPK;
import edu.ifes.ci.si.les.sa.model.Professor;
import edu.ifes.ci.si.les.sa.model.RegistroDeNotas;
import edu.ifes.ci.si.les.sa.model.Turma;
import edu.ifes.ci.si.les.sa.model.Uf;
import edu.ifes.ci.si.les.sa.repositories.AdministradorRepository;
import edu.ifes.ci.si.les.sa.repositories.AlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.AlunoTurmaRepository;
import edu.ifes.ci.si.les.sa.repositories.AtividadeAvaliativaRepository;
import edu.ifes.ci.si.les.sa.repositories.AulaRepository;
import edu.ifes.ci.si.les.sa.repositories.AvaliacaoAlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.BairroRepository;
import edu.ifes.ci.si.les.sa.repositories.CidadeRepository;
import edu.ifes.ci.si.les.sa.repositories.CursoRepository;
import edu.ifes.ci.si.les.sa.repositories.DisciplinaRepository;
import edu.ifes.ci.si.les.sa.repositories.PeriodoLetivoRepository;
import edu.ifes.ci.si.les.sa.repositories.PresencaAlunoRepository;
import edu.ifes.ci.si.les.sa.repositories.ProfessorRepository;
import edu.ifes.ci.si.les.sa.repositories.RegistroDeNotasRepository;
import edu.ifes.ci.si.les.sa.repositories.TurmaRepository;
import edu.ifes.ci.si.les.sa.repositories.UfRepository;

@Service
public class _DBService {

	@Autowired
	private AdministradorRepository administradorRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private AlunoTurmaRepository alunoTurmaRepository;

	@Autowired
	private AtividadeAvaliativaRepository atividadeAvaliativaRepository;

	@Autowired
	private AulaRepository aulaRepository;

	@Autowired
	private AvaliacaoAlunoRepository avaliacaoAlunoRepository;

	@Autowired
	private BairroRepository bairroRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private PeriodoLetivoRepository periodoLetivoRepository;

	@Autowired
	private PresencaAlunoRepository presencaAlunoRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private UfRepository ufRepository;

	@Autowired
	private RegistroDeNotasRepository registroDeNotasRepository;

	public void instantiateTestDatabase() throws ParseException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// ********************************************CRUDS************************************************

		// CADASTRO UF
		Uf uf1 = new Uf(null, "ES", "Espirito Santo");
		Uf uf2 = new Uf(null, "RJ", "Rio de Janeiro");
		Uf uf3 = new Uf(null, "SP", "São Paulo");
		ufRepository.saveAll(Arrays.asList(uf1, uf2, uf3));

		// CADASTRO CIDADE
		Cidade cidade1 = new Cidade(null, "Vitória", uf1);
		Cidade cidade2 = new Cidade(null, "Osasco", uf2);
		Cidade cidade3 = new Cidade(null, "Marataízes", uf1);
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

		// CADASTRO BAIRRO
		Bairro bairro1 = new Bairro(null, "Jardim Camburi", uf1, cidade1);
		Bairro bairro2 = new Bairro(null, "Piratininga", uf3, cidade2);
		Bairro bairro3 = new Bairro(null, "Barra do Itapemirim", uf1, cidade3);
		bairroRepository.saveAll(Arrays.asList(bairro1, bairro2, bairro3));

		// CADASTRO AULA
		Aula aula1 = new Aula(null, "Introdução a Algoritmos", sdf.parse("2021-07-12"));
		Aula aula2 = new Aula(null, "Portugol para iniciantes", sdf.parse("2021-07-16"));
		Aula aula3 = new Aula(null, "Importancia do teste de mesa", sdf.parse("2021-07-18"));
		aulaRepository.saveAll(Arrays.asList(aula1, aula2, aula3));

		// CADASTRO CURSO
		Curso curso1 = new Curso(null, "Sistemas de Informação", 2370, 4, "Graduação");
		Curso curso2 = new Curso(null, "Matemática", 2370, 4, "Graduação");
		Curso curso3 = new Curso(null, "Engenharia Mecânica", 2370, 4, "Graduação");
		Curso curso4 = new Curso(null, "Engenharia de Minas", 2370, 4, "Graduação");
		Curso curso5 = new Curso(null, "Ténico em Informática", 1200, 2, "Técnico");
		cursoRepository.saveAll(Arrays.asList(curso1, curso2, curso3, curso4, curso5));

		// CADASTRO DISCIPLINA
		Disciplina disciplina1 = new Disciplina(null, "Desenvolvimento WEB", 60, "DW", curso1);
		Disciplina disciplina2 = new Disciplina(null, "Cálculo 3", 90, "CALC3", curso2);
		Disciplina disciplina3 = new Disciplina(null, "Sistema Operacional", 60, "SO", curso1);
		Disciplina disciplina4 = new Disciplina(null, "Programação Orientada a Objetos 1", 60, "POO1", curso1);
		Disciplina disciplina5 = new Disciplina(null, "Fundamentos de Hardware", 30, "FH", curso5);
		Disciplina disciplina6 = new Disciplina(null, "Laboratório Engenharia de Software", 60, "LES", curso1);
		Disciplina disciplina7 = new Disciplina(null, "Cálculo 2", 90, "CALC2", curso2);
		disciplinaRepository
				.saveAll(Arrays.asList(disciplina1, disciplina2, disciplina3, disciplina4, disciplina5, disciplina6, disciplina7));

		// CADASTRO ATIVIDADE AVALIATIVA (AVALIAÇÃO)
		AtividadeAvaliativa atividadeAvaliativa1 = new AtividadeAvaliativa(null,
				"Avaliacao Listas Duplamente Encadeadas", "Avaliacao", sdf.parse("2021-02-16"), 25.0, disciplina4);
		AtividadeAvaliativa atividadeAvaliativa2 = new AtividadeAvaliativa(null, "Trabalho de HTML5 - Aspnet.core",
				"Trabalho", sdf.parse("2021-04-16"), 25.0, disciplina1);
		AtividadeAvaliativa atividadeAvaliativa3 = new AtividadeAvaliativa(null, "Avaliacao de Calculo III",
				"Avaliacao", sdf.parse("2021-05-16"), 25.0, disciplina2);
		AtividadeAvaliativa atividadeAvaliativa4 = new AtividadeAvaliativa(null, "Compilação de KERNEL LINUX",
				"Trabalho", sdf.parse("2021-06-16"), 25.0, disciplina3);
		AtividadeAvaliativa atividadeAvaliativa5 = new AtividadeAvaliativa(null,
				"PDS - Implementar Levantamento de Requisitos, Diagramas de casos de Uso e Classe", "Trabalho",
				sdf.parse("2021-03-16"), 15.0, disciplina6);
		AtividadeAvaliativa atividadeAvaliativa6 = new AtividadeAvaliativa(null, "PDS - Implementar Interface Gráfica",
				"Trabalho", sdf.parse("2021-04-16"), 15.0, disciplina6);
		AtividadeAvaliativa atividadeAvaliativa7 = new AtividadeAvaliativa(null, "PDS - Implementar (CRUDS)",
				"Trabalho", sdf.parse("2021-05-16"), 15.0, disciplina6);
		AtividadeAvaliativa atividadeAvaliativa8 = new AtividadeAvaliativa(null, "PDS - Implementar Casos de Uso",
				"Trabalho", sdf.parse("2021-06-16"), 15.0, disciplina6);
		AtividadeAvaliativa atividadeAvaliativa9 = new AtividadeAvaliativa(null,
				"PDS - Relatórios, Implantar, Apresentação", "Trabalho", sdf.parse("2021-07-16"), 20.0, disciplina6);
		atividadeAvaliativaRepository.saveAll(Arrays.asList(atividadeAvaliativa1, atividadeAvaliativa2,
				atividadeAvaliativa3, atividadeAvaliativa4, atividadeAvaliativa5, atividadeAvaliativa6,
				atividadeAvaliativa7, atividadeAvaliativa8, atividadeAvaliativa9));

		// CADASTRO PROFESSOR -- tipo usuário = 2
		Professor professor1 = new Professor(null, "Mario da Silva", sdf.parse("1979-08-10"), "M", "147.2356.987-50",
				"Ruas dos Braos Loics", 21, 2, bairro1, "Mestre", "mm@gmail.com", "123456", "(28) 99900-0000");
		Professor professor2 = new Professor(null, "Lorena Marcos", sdf.parse("1990-09-15"), "F", "000.748.962-71",
				"Ruas Marcos Fonseca", 52, 2, bairro2, "Mestre", "lorena@gmail.com", "123456", "(28) 99900-0000");
		Professor professor3 = new Professor(null, "Sebastiao Santos", sdf.parse("1965-08-09"), "M", "951.232.741-32",
				"Avenida Sambaio Dutra", 70, 2, bairro3, "Doutor", "sebastiao@gmail.com", "123456", "(28) 99900-0000");
		// professor1.getTelefones().addAll(Arrays.asList("273323-6333","273321-8600"));
		professorRepository.saveAll(Arrays.asList(professor1, professor2, professor3));

		// CADASTRO PERIODO LETIVO
		PeriodoLetivo periodoLetivo1 = new PeriodoLetivo(null, "2021/1", sdf.parse("2021-02-08"),
				sdf.parse("2021-06-01"), 280);
		PeriodoLetivo periodoLetivo2 = new PeriodoLetivo(null, "2021/2", sdf.parse("2021-07-31"),
				sdf.parse("2020-12-21"), 283);
		PeriodoLetivo periodoLetivo3 = new PeriodoLetivo(null, "2022/1", sdf.parse("2022-02-10"),
				sdf.parse("2022-06-03"), 275);
		periodoLetivoRepository.saveAll(Arrays.asList(periodoLetivo1, periodoLetivo2, periodoLetivo3));

		// CADASTRO TURMA
		Turma turma1 = new Turma(null, "Programacao I - 2021.1", 20, sdf.parse("2021-10-12"), sdf.parse("2021-10-15"),
				periodoLetivo1, aula1, atividadeAvaliativa1, professor1, disciplina4, curso1);
		Turma turma2 = new Turma(null, "Logica I - 2021.1", 20, sdf.parse("2021-10-12"), sdf.parse("2021-10-15"),
				periodoLetivo1, aula1, atividadeAvaliativa1, professor1, disciplina1, curso1);
		Turma turma3 = new Turma(null, "Calculo I - 2021.1", 20, sdf.parse("2021-10-12"), sdf.parse("2021-10-15"),
				periodoLetivo1, aula1, atividadeAvaliativa1, professor1, disciplina2, curso2);
		Turma turma4 = new Turma(null, "Redes I - 2021.1", 20, sdf.parse("2021-10-12"), sdf.parse("2021-10-15"),
				periodoLetivo1, aula1, atividadeAvaliativa1, professor1, disciplina1, curso1);
		Turma turma5 = new Turma(null, "Banco de Dados I - 2021.1", 20, sdf.parse("2021-10-12"),
				sdf.parse("2021-10-15"), periodoLetivo1, aula1, atividadeAvaliativa1, professor1, disciplina1, curso1);
		Turma turma6 = new Turma(null, "Calculo II - 2021.1", 20, sdf.parse("2021-10-12"), sdf.parse("2021-10-15"),
				periodoLetivo1, aula1, atividadeAvaliativa1, professor1, disciplina7, curso2);
		turmaRepository.saveAll(Arrays.asList(turma1, turma2, turma3, turma4, turma5, turma6));

		// CADASTRO ALUNO -- tipo usuário = 3
		Aluno aluno1 = new Aluno(null, "Abrao Lincao", sdf.parse("1980-10-12"), "M", "154.145.025-96",
				"Rua Canoi Carpado", 10, 3, bairro1, "20161SI0030", "3987412ES", "abraol@email.cpm", "123456", curso1,
				"(28) 99900-0000");
		Aluno aluno2 = new Aluno(null, "Jovelha Nova", sdf.parse("1982-04-13"), "F", "003.134.543-96",
				"Rua Tabata battat", 11, 3, bairro2, "20161MA0031", "8889542ES", "joana@email.cpm", "123456", curso2,
				"(28) 99900-0000");
		Aluno aluno3 = new Aluno(null, "Linara Barg", sdf.parse("1989-12-14"), "F", "111.532.004-96",
				"Rua Macarena Barao", 12, 3, bairro3, "20161ME0032", "0005412ES", "magno@email.cpm", "123456", curso2,
				"(28) 99900-0000");
		Aluno aluno4 = new Aluno(null, "Márcio Jr", sdf.parse("1995-03-1"), "M", "166.532.004-76",
				"Rua Oliveira Sobrinho", 215, 3, bairro3, "20181SI027", "1946001ES", "marciojr028@email.cpm", "123456",
				curso1, "(28) 99900-0000");
		Aluno aluno5 = new Aluno(null, "Irmão do Jorel", sdf.parse("1995-03-1"), "M", "166.532.004-76",
				"Rua Oliveira Sobrinho", 215, 3, bairro3, "20181SI027", "1946001ES", "irmaodojorel028@email.cpm", "123456",
				curso2, "(28) 99900-0000");
		// aluno1.getTelefones().addAll(Arrays.asList("2899910-1112","283027-1112"));
		alunoRepository.saveAll(Arrays.asList(aluno1, aluno2, aluno3, aluno4, aluno5));

		// CADASTRO ALUNOTURMA
		AlunoTurmaPK alunoTurmaPK1 = new AlunoTurmaPK(aluno1, turma1);
		AlunoTurmaPK alunoTurmaPK2 = new AlunoTurmaPK(aluno2, turma3);
		AlunoTurmaPK alunoTurmaPK3 = new AlunoTurmaPK(aluno3, turma6);
		AlunoTurmaPK alunoTurmaPK4 = new AlunoTurmaPK(aluno4, turma1);
		AlunoTurmaPK alunoTurmaPK5 = new AlunoTurmaPK(aluno5, turma3);

		AlunoTurma alunoTurma1 = new AlunoTurma(alunoTurmaPK1, "APROVADO", 25.00, 18);
		AlunoTurma alunoTurma2 = new AlunoTurma(alunoTurmaPK2, "APROV. CONSELHO", 16.00, 14);
		AlunoTurma alunoTurma3 = new AlunoTurma(alunoTurmaPK3, "REPROV. FALTAS", 25.00, 16);
		AlunoTurma alunoTurma4 = new AlunoTurma(alunoTurmaPK4, "CURSANDO", 60.00, 12);
		AlunoTurma alunoTurma5 = new AlunoTurma(alunoTurmaPK5, "APROV. ABONO FALTAS", 60.00, 12);
		alunoTurmaRepository.saveAll(Arrays.asList(alunoTurma1, alunoTurma2, alunoTurma3, alunoTurma4, alunoTurma5));

		// CADASTRO ALUNODISCIPLINA

		// CADASTRO AVALIACAO ALUNO
		AvaliacaoAlunoPK avaliacaoAlunoPK1 = new AvaliacaoAlunoPK(alunoTurma1, atividadeAvaliativa1);
		AvaliacaoAlunoPK avaliacaoAlunoPK2 = new AvaliacaoAlunoPK(alunoTurma2, atividadeAvaliativa1);
		AvaliacaoAlunoPK avaliacaoAlunoPK3 = new AvaliacaoAlunoPK(alunoTurma3, atividadeAvaliativa2);

		AvaliacaoAluno avaliacaoAluno1 = new AvaliacaoAluno(avaliacaoAlunoPK1, 25.00);
		AvaliacaoAluno avaliacaoAluno2 = new AvaliacaoAluno(avaliacaoAlunoPK2, 16.00);
		AvaliacaoAluno avaliacaoAluno3 = new AvaliacaoAluno(avaliacaoAlunoPK3, 25.00);
		avaliacaoAlunoRepository.saveAll(Arrays.asList(avaliacaoAluno1, avaliacaoAluno2, avaliacaoAluno3));

		// CADASTRO ADMINISTRADOR -- tipo usuário = 1
		Administrador administrador1 = new Administrador(null, "João Paulo", sdf.parse("1980-02-05"), "M",
				"865.132.132-14", "Rua das mangas amarelas", 20, 1, bairro1, "adm1@email.com", "123456");
		Administrador administrador2 = new Administrador(null, "Paulo Mendonca", sdf.parse("1972-01-15"), "F",
				"097.364.741-25", "Rua Paralela Ivis", 52, 1, bairro2, "adm2@email.com", "123456");
		Administrador administrador3 = new Administrador(null, "Marco Aurelio", sdf.parse("1969-03-11"), "M",
				"235.987.150-00", "Rua Jorge Amarelo", 71, 1, bairro3, "adm3@email.com", "123456");
		administradorRepository.saveAll(Arrays.asList(administrador1, administrador2, administrador3));

		// ***************************PROCESSOS_DE_NEGÓCIO******************************

		// REGISTRO DE PRESENÇA (Processo 01)
		/*
		* PresencaAlunoPK presencaAlunoPK1 = new PresencaAlunoPK(aula1, aluno1,
		* turma5); PresencaAlunoPK presencaAlunoPK2 = new PresencaAlunoPK(aula2,
		* aluno2, turma5); PresencaAlunoPK presencaAlunoPK3 = new
		* PresencaAlunoPK(aula3, aluno3, turma5);
		*/

//		PresencaAluno presencaAluno1 = new PresencaAluno(turma5,aluno1,aula1, 2, false);
//		PresencaAluno presencaAluno2 = new PresencaAluno(turma5,aluno2,aula1, 2, true);
//		PresencaAluno presencaAluno3 = new PresencaAluno(turma5,aluno3,aula1, 2, true);
//		PresencaAluno presencaAluno4 = new PresencaAluno(turma5,aluno4,aula1, 2, true);
//
//		PresencaAluno presencaAluno5 = new PresencaAluno(turma5,aluno1,aula2, 2, false);
//		PresencaAluno presencaAluno6 = new PresencaAluno(turma5,aluno2,aula2, 2, true);
//		PresencaAluno presencaAluno7 = new PresencaAluno(turma5,aluno3,aula2, 2, true);
//		PresencaAluno presencaAluno8 = new PresencaAluno(turma5,aluno4,aula2, 2, true);
//
//		PresencaAluno presencaAluno9 = new PresencaAluno(turma5,aluno1,aula3, 2, false);
//		PresencaAluno presencaAluno10 = new PresencaAluno(turma5,aluno2,aula3, 2, true);
//		PresencaAluno presencaAluno11 = new PresencaAluno(turma5,aluno3,aula3, 2, true);
//		PresencaAluno presencaAluno12 = new PresencaAluno(turma5,aluno4,aula3, 2, true);
		
		PresencaAluno presencaAluno1 = new PresencaAluno(new PresencaAlunoPK(aula1, aluno1, turma5), 2, true);
		PresencaAluno presencaAluno2 = new PresencaAluno(new PresencaAlunoPK(aula1, aluno2, turma5), 2, true);
		PresencaAluno presencaAluno3 = new PresencaAluno(new PresencaAlunoPK(aula1, aluno3, turma5), 2, true);
		PresencaAluno presencaAluno4 = new PresencaAluno(new PresencaAlunoPK(aula1, aluno4, turma5), 2, true);
		
		PresencaAluno presencaAluno5 = new PresencaAluno(new PresencaAlunoPK(aula2, aluno1, turma5), 2, false);
		PresencaAluno presencaAluno6 = new PresencaAluno(new PresencaAlunoPK(aula2, aluno2, turma5), 2, true);
		PresencaAluno presencaAluno7 = new PresencaAluno(new PresencaAlunoPK(aula2, aluno3, turma5), 2, true);
		PresencaAluno presencaAluno8 = new PresencaAluno(new PresencaAlunoPK(aula2, aluno4, turma5), 2, true);
		
		PresencaAluno presencaAluno9 = new PresencaAluno(new PresencaAlunoPK(aula3, aluno1, turma5), 2, false);
		PresencaAluno presencaAluno10 = new PresencaAluno(new PresencaAlunoPK(aula3, aluno2, turma5), 2, true);
		PresencaAluno presencaAluno11 = new PresencaAluno(new PresencaAlunoPK(aula3, aluno3, turma5), 2, true);
		PresencaAluno presencaAluno12 = new PresencaAluno(new PresencaAlunoPK(aula3, aluno4, turma5), 2, true);


		presencaAlunoRepository.saveAll(Arrays.asList(presencaAluno1,presencaAluno2,presencaAluno3,presencaAluno4,
				presencaAluno5,presencaAluno6,presencaAluno7,presencaAluno8,presencaAluno9,presencaAluno10,presencaAluno11,
					presencaAluno12));
		// REGISTRO DE NOTAS (Processo 02) - MárcioJr

		RegistroDeNotas registroDeNotas1 = new RegistroDeNotas(null, (float) 14.55, disciplina6, atividadeAvaliativa5,
				aluno4);
		RegistroDeNotas registroDeNotas2 = new RegistroDeNotas(null, 15, disciplina6, atividadeAvaliativa5, aluno4);
		RegistroDeNotas registroDeNotas3 = new RegistroDeNotas(null, 15, disciplina6, atividadeAvaliativa6, aluno4);
		RegistroDeNotas registroDeNotas4 = new RegistroDeNotas(null, 15, disciplina6, atividadeAvaliativa7, aluno4);
		RegistroDeNotas registroDeNotas5 = new RegistroDeNotas(null, 15, disciplina6, atividadeAvaliativa8, aluno4);
		RegistroDeNotas registroDeNotas6 = new RegistroDeNotas(null, 15, disciplina2, atividadeAvaliativa3, aluno2);
		registroDeNotasRepository.saveAll(Arrays.asList(registroDeNotas1, registroDeNotas2, registroDeNotas3,
				registroDeNotas4, registroDeNotas5, registroDeNotas6));

		/*
		 * RegistroDeNotas registroDeNotas1 = new RegistroDeNotas(null, (float) 14.5,
		 * periodoLetivo1, aluno4, atividadeAvaliativa5, curso1, disciplina6);
		 * RegistroDeNotas registroDeNotas2 = new RegistroDeNotas(null, 15,
		 * periodoLetivo1, aluno4, atividadeAvaliativa6, curso1, disciplina6);
		 * RegistroDeNotas registroDeNotas3 = new RegistroDeNotas(null, 15,
		 * periodoLetivo1, aluno4, atividadeAvaliativa7, curso1, disciplina6);
		 * RegistroDeNotas registroDeNotas4 = new RegistroDeNotas(null, 15,
		 * periodoLetivo1, aluno4, atividadeAvaliativa8, curso1, disciplina6);
		 * RegistroDeNotas registroDeNotas5 = new RegistroDeNotas(null, 20,
		 * periodoLetivo1, aluno4, atividadeAvaliativa9, curso1, disciplina6);
		 * RegistroDeNotas registroDeNotas6 = new RegistroDeNotas(null, 15,
		 * periodoLetivo2, aluno2, atividadeAvaliativa3, curso2, disciplina2);
		 * registroDeNotasRepository.saveAll(Arrays.asList(registroDeNotas1,
		 * registroDeNotas2, registroDeNotas3, registroDeNotas4, registroDeNotas5,
		 * registroDeNotas6));
		 */

		// REGISTRO DE APROVAÇÃO (Processo 03)

		// REGISTRO DE MATRÍCULA EM DISCIPLINAS (Processo 04)
	}

}
