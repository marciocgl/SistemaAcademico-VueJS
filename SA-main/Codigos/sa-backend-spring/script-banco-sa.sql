-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 13-Jun-2021 às 17:46
-- Versão do servidor: 10.4.19-MariaDB
-- versão do PHP: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sa`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `administrador`
--

CREATE TABLE `administrador` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `dataNasc` date NOT NULL,
  `sexo` char(1) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `rua` varchar(100) NOT NULL,
  `id_bairro` int(11) NOT NULL,
  `numero` varchar(4) NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `administrador`
--

INSERT INTO `administrador` (`id`, `nome`, `dataNasc`, `sexo`, `cpf`, `rua`, `id_bairro`, `numero`, `email`, `senha`) VALUES
(1, 'Chris Rock', '1970-01-10', 'M', '77788899966', 'Rua Brooklin 1996', 1, '10', 'chris@email.com', '123456'),
(2, 'Rochele Rock', '1980-01-25', 'F', '20010030040', 'Rua Brooklin do outro lado ', 2, '30', 'rochelle@email.com', '123456');

-- --------------------------------------------------------

--
-- Estrutura da tabela `aluno`
--

CREATE TABLE `aluno` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `dataNasc` date NOT NULL,
  `sexo` char(1) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `rua` varchar(100) NOT NULL,
  `numero` varchar(4) NOT NULL,
  `id_bairro` int(11) NOT NULL,
  `tipoUsuario` int(11) NOT NULL,
  `id_curso` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `aluno`
--

INSERT INTO `aluno` (`id`, `nome`, `dataNasc`, `sexo`, `cpf`, `rua`, `numero`, `id_bairro`, `tipoUsuario`, `id_curso`) VALUES
(1, 'Joaozinho da sulva', '1996-02-10', 'M', '77744411123', 'Rua dos alunos criados', '36', 1, 2, 1),
(2, 'José Sebastiao', '1999-10-02', 'M', '99966632145', 'Ruas dos Pastor elegantes', '23', 2, 2, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `alunoturma`
--

CREATE TABLE `alunoturma` (
  `id` int(11) NOT NULL,
  `notaFinal` float NOT NULL,
  `presencaFinal` int(11) NOT NULL,
  `id_turma` int(11) NOT NULL,
  `id_aluno` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `alunoturma`
--

INSERT INTO `alunoturma` (`id`, `notaFinal`, `presencaFinal`, `id_turma`, `id_aluno`) VALUES
(1, 85, 10, 4, 1),
(2, 70, 20, 4, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `atividadeavaliativa`
--

CREATE TABLE `atividadeavaliativa` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `data` date NOT NULL,
  `valor` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `atividadeavaliativa`
--

INSERT INTO `atividadeavaliativa` (`id`, `nome`, `tipo`, `data`, `valor`) VALUES
(1, 'Prova de C', 'Avaliação', '2021-10-25', 10),
(2, 'Trabalho de ponteiros', 'Trabalho', '2021-03-15', 10);

-- --------------------------------------------------------

--
-- Estrutura da tabela `aula`
--

CREATE TABLE `aula` (
  `id` int(11) NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `data` date NOT NULL,
  `id_turma` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `aula`
--

INSERT INTO `aula` (`id`, `descricao`, `data`, `id_turma`) VALUES
(1, 'Trabalhando com ponteiros', '2021-03-12', 4),
(2, 'Manipulação de arquivos', '2021-02-10', 4);

-- --------------------------------------------------------

--
-- Estrutura da tabela `avaliacaoaluno`
--

CREATE TABLE `avaliacaoaluno` (
  `id` int(11) NOT NULL,
  `nota` float NOT NULL,
  `id_alunoTurma` int(11) NOT NULL,
  `id_atividadeAvaliativa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `avaliacaoaluno`
--

INSERT INTO `avaliacaoaluno` (`id`, `nota`, `id_alunoTurma`, `id_atividadeAvaliativa`) VALUES
(1, 10, 1, 1),
(2, 8, 2, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `bairro`
--

CREATE TABLE `bairro` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `id_cidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `bairro`
--

INSERT INTO `bairro` (`id`, `nome`, `id_cidade`) VALUES
(1, 'Camburi', 1),
(2, 'Penha', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cidade`
--

CREATE TABLE `cidade` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `id_uf` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `cidade`
--

INSERT INTO `cidade` (`id`, `nome`, `id_uf`) VALUES
(1, 'Vitoria', 1),
(2, 'Osasco', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `curso`
--

CREATE TABLE `curso` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cargaHoraria` int(11) NOT NULL,
  `duracao` int(11) NOT NULL,
  `tipo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `curso`
--

INSERT INTO `curso` (`id`, `nome`, `cargaHoraria`, `duracao`, `tipo`) VALUES
(1, 'Sistemas de Informação', 2060, 4, 'Graduação'),
(2, 'Engenharia Mecanica', 3060, 5, 'Graduação');

-- --------------------------------------------------------

--
-- Estrutura da tabela `disciplina`
--

CREATE TABLE `disciplina` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `nomeReduzido` varchar(50) NOT NULL,
  `id_curso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `disciplina`
--

INSERT INTO `disciplina` (`id`, `nome`, `nomeReduzido`, `id_curso`) VALUES
(1, 'Programação I', 'PROGI', 1),
(2, 'Calculo I', 'CALCI', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `periodoletivo`
--

CREATE TABLE `periodoletivo` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `dataInicio` date NOT NULL,
  `dataFim` date DEFAULT NULL,
  `qtdDiaLetivo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `periodoletivo`
--

INSERT INTO `periodoletivo` (`id`, `nome`, `dataInicio`, `dataFim`, `qtdDiaLetivo`) VALUES
(1, '2021.1', '2021-06-01', '2021-12-31', 6),
(2, '2022.1', '2022-01-03', '2022-07-29', 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `presencaaluno`
--

CREATE TABLE `presencaaluno` (
  `id` int(11) NOT NULL,
  `qtdAula` int(11) NOT NULL,
  `presenca` tinyint(1) NOT NULL,
  `id_aluno_turma` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `presencaaluno`
--

INSERT INTO `presencaaluno` (`id`, `qtdAula`, `presenca`, `id_aluno_turma`) VALUES
(1, 2, 1, 1),
(2, 2, 1, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `professor`
--

CREATE TABLE `professor` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `dataNasc` date NOT NULL,
  `sexo` char(1) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `rua` varchar(100) NOT NULL,
  `numero` varchar(4) NOT NULL,
  `id_bairro` int(11) NOT NULL,
  `titulacao` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(50) NOT NULL,
  `tipoUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `professor`
--

INSERT INTO `professor` (`id`, `nome`, `dataNasc`, `sexo`, `cpf`, `rua`, `numero`, `id_bairro`, `titulacao`, `email`, `senha`, `tipoUsuario`) VALUES
(3, 'Atila Uno', '1980-02-20', 'M', '01472589631', 'Rua Selva 3', '33', 1, 'Mestrado', 'atila@email.com', '123456', 2),
(4, 'Leona Silva', '1995-05-13', 'F', '78965412365', 'Rua dos cabelos', '18', 2, 'Doutorado', 'leona@email.com', '123456', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `telefone`
--

CREATE TABLE `telefone` (
  `id` int(11) NOT NULL,
  `numero` varchar(11) NOT NULL,
  `id_professor` int(11) DEFAULT NULL,
  `id_aluno` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `telefone`
--

INSERT INTO `telefone` (`id`, `numero`, `id_professor`, `id_aluno`) VALUES
(3, '28999143115', 4, NULL),
(5, '28999112233', NULL, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `turma`
--

CREATE TABLE `turma` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `qtdVaga` int(11) NOT NULL,
  `dataInicioMatricula` date NOT NULL,
  `dataFimMatricula` date NOT NULL,
  `id_periodoLetivo` int(11) DEFAULT NULL,
  `id_disciplina` int(11) DEFAULT NULL,
  `id_professor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `turma`
--

INSERT INTO `turma` (`id`, `nome`, `qtdVaga`, `dataInicioMatricula`, `dataFimMatricula`, `id_periodoLetivo`, `id_disciplina`, `id_professor`) VALUES
(3, 'Calculo I - 2021.1', 40, '2021-06-01', '2021-06-30', 1, 2, 3),
(4, 'Programação I - 2021.1', 40, '2021-06-01', '2021-06-30', 1, 1, 4);

-- --------------------------------------------------------

--
-- Estrutura da tabela `uf`
--

CREATE TABLE `uf` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `sigla` char(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `uf`
--

INSERT INTO `uf` (`id`, `nome`, `sigla`) VALUES
(1, 'Espirito Santo', 'ES'),
(2, 'São Paulo', 'SP');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bairro_administrador` (`id_bairro`);

--
-- Índices para tabela `aluno`
--
ALTER TABLE `aluno`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bairro_aluno` (`id_bairro`),
  ADD KEY `fk_curso_aluno` (`id_curso`);

--
-- Índices para tabela `alunoturma`
--
ALTER TABLE `alunoturma`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_turma_alunoTurma` (`id_turma`),
  ADD KEY `fk_aluno_alunoTurma` (`id_aluno`);

--
-- Índices para tabela `atividadeavaliativa`
--
ALTER TABLE `atividadeavaliativa`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `aula`
--
ALTER TABLE `aula`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_turma_aula` (`id_turma`);

--
-- Índices para tabela `avaliacaoaluno`
--
ALTER TABLE `avaliacaoaluno`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_alunoTurma_avaliacaoAluno` (`id_alunoTurma`),
  ADD KEY `fk_atividadeAvaliativa_avaliacaoAluno` (`id_atividadeAvaliativa`);

--
-- Índices para tabela `bairro`
--
ALTER TABLE `bairro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cidade_bairro` (`id_cidade`);

--
-- Índices para tabela `cidade`
--
ALTER TABLE `cidade`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_uf_cidade` (`id_uf`);

--
-- Índices para tabela `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `disciplina`
--
ALTER TABLE `disciplina`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_curso_disciplina` (`id_curso`);

--
-- Índices para tabela `periodoletivo`
--
ALTER TABLE `periodoletivo`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `presencaaluno`
--
ALTER TABLE `presencaaluno`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_alunoTurma_presencaAluno` (`id_aluno_turma`);

--
-- Índices para tabela `professor`
--
ALTER TABLE `professor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bairro_professor` (`id_bairro`);

--
-- Índices para tabela `telefone`
--
ALTER TABLE `telefone`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_aluno_telefone` (`id_aluno`),
  ADD KEY `fk_professor_telefone` (`id_professor`);

--
-- Índices para tabela `turma`
--
ALTER TABLE `turma`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_periodoLetivo_turma` (`id_periodoLetivo`),
  ADD KEY `fk_disciplina_turma` (`id_disciplina`),
  ADD KEY `fk_professor_turma` (`id_professor`);

--
-- Índices para tabela `uf`
--
ALTER TABLE `uf`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `administrador`
--
ALTER TABLE `administrador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `aluno`
--
ALTER TABLE `aluno`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `alunoturma`
--
ALTER TABLE `alunoturma`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `atividadeavaliativa`
--
ALTER TABLE `atividadeavaliativa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `aula`
--
ALTER TABLE `aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `avaliacaoaluno`
--
ALTER TABLE `avaliacaoaluno`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `bairro`
--
ALTER TABLE `bairro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `cidade`
--
ALTER TABLE `cidade`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `curso`
--
ALTER TABLE `curso`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `disciplina`
--
ALTER TABLE `disciplina`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `periodoletivo`
--
ALTER TABLE `periodoletivo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `presencaaluno`
--
ALTER TABLE `presencaaluno`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `professor`
--
ALTER TABLE `professor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `telefone`
--
ALTER TABLE `telefone`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `turma`
--
ALTER TABLE `turma`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `uf`
--
ALTER TABLE `uf`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `administrador`
--
ALTER TABLE `administrador`
  ADD CONSTRAINT `fk_bairro_administrador` FOREIGN KEY (`id_bairro`) REFERENCES `bairro` (`id`);

--
-- Limitadores para a tabela `aluno`
--
ALTER TABLE `aluno`
  ADD CONSTRAINT `fk_bairro_aluno` FOREIGN KEY (`id_bairro`) REFERENCES `bairro` (`id`),
  ADD CONSTRAINT `fk_curso_aluno` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`);

--
-- Limitadores para a tabela `alunoturma`
--
ALTER TABLE `alunoturma`
  ADD CONSTRAINT `fk_aluno_alunoTurma` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id`),
  ADD CONSTRAINT `fk_turma_alunoTurma` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id`);

--
-- Limitadores para a tabela `aula`
--
ALTER TABLE `aula`
  ADD CONSTRAINT `fk_turma_aula` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id`);

--
-- Limitadores para a tabela `avaliacaoaluno`
--
ALTER TABLE `avaliacaoaluno`
  ADD CONSTRAINT `fk_alunoTurma_avaliacaoAluno` FOREIGN KEY (`id_alunoTurma`) REFERENCES `alunoturma` (`id`),
  ADD CONSTRAINT `fk_atividadeAvaliativa_avaliacaoAluno` FOREIGN KEY (`id_atividadeAvaliativa`) REFERENCES `atividadeavaliativa` (`id`);

--
-- Limitadores para a tabela `bairro`
--
ALTER TABLE `bairro`
  ADD CONSTRAINT `fk_cidade_bairro` FOREIGN KEY (`id_cidade`) REFERENCES `cidade` (`id`);

--
-- Limitadores para a tabela `cidade`
--
ALTER TABLE `cidade`
  ADD CONSTRAINT `fk_uf_cidade` FOREIGN KEY (`id_uf`) REFERENCES `uf` (`id`);

--
-- Limitadores para a tabela `disciplina`
--
ALTER TABLE `disciplina`
  ADD CONSTRAINT `fk_curso_disciplina` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`);

--
-- Limitadores para a tabela `presencaaluno`
--
ALTER TABLE `presencaaluno`
  ADD CONSTRAINT `fk_alunoTurma_presencaAluno` FOREIGN KEY (`id_aluno_turma`) REFERENCES `alunoturma` (`id`);

--
-- Limitadores para a tabela `professor`
--
ALTER TABLE `professor`
  ADD CONSTRAINT `fk_bairro_professor` FOREIGN KEY (`id_bairro`) REFERENCES `bairro` (`id`);

--
-- Limitadores para a tabela `telefone`
--
ALTER TABLE `telefone`
  ADD CONSTRAINT `fk_aluno_telefone` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id`),
  ADD CONSTRAINT `fk_professor_telefone` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id`);

--
-- Limitadores para a tabela `turma`
--
ALTER TABLE `turma`
  ADD CONSTRAINT `fk_disciplina_turma` FOREIGN KEY (`id_disciplina`) REFERENCES `disciplina` (`id`),
  ADD CONSTRAINT `fk_periodoLetivo_turma` FOREIGN KEY (`id_periodoLetivo`) REFERENCES `periodoletivo` (`id`),
  ADD CONSTRAINT `fk_professor_turma` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
