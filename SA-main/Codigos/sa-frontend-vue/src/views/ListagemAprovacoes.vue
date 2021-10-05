<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <v-toolbar-title><center>Listagem de Aprovação</center></v-toolbar-title>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-toolbar>
        <v-spacer>
          <br />
          <v-row>
            <v-select
              :items="lCursoRegApr"
              item-text="nome"
              label="Curso"
              v-model="curso"
              item-value="id"
              outlined
              required
              @change="fetchRecordsTurma"
              :rules="registroAprovacaoRulesCurso">
            </v-select>

            <v-select
              :items="lTurmaRegApr"
              item-text="nome"
              label="Turma"
              v-model="turma"
              item-value="id"
              outlined
              required
              @change="fetchRecordsAlunoTurma"
              :rules="registroAprovacaoRulesTurma">
            </v-select>
          </v-row>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <div > 
          <div style="width: 65%; position:relative; float: left; left:0px;">
              <v-container>
                  <v-data-table
                      :headers="headers"
                      :items="lAlunoTurmaRegApr"
                      item-key="alunoTurma"
                      :show-select="false"
                      class="elevation-1">
                  </v-data-table>
              </v-container>
          </div>
          <div style="width: 35%; position:relative; float: left; left:0px;">
              <v-container>
                  <v-data-table
                      :headers="headers2"
                      :items="dados"
                      class="elevation-1"
                      :hide-default-footer="true">
                  </v-data-table>
              </v-container>
          </div>
      </div>
    </v-container>
  </div>
</template>

<script>

//import RegistroAprovacaoService from "../service/domain/RegistroAprovacaoService";
//const serviceRegistroAprovacao = RegistroAprovacaoService.build();

import CursoService from "../service/domain/CursoService";
const serviceCurso = CursoService.build();

import TurmaService from "../service/domain/TurmaService";
const serviceTurma = TurmaService.build();

import AlunoTurmaService from "../service/domain/AlunoTurmaService";
const serviceAlunoTurma = AlunoTurmaService.build();

export default {
  data: () => ({
    registroAprovacaoRulesCurso: [(v) => !!v || "Seleção Necessária"],
    registroAprovacaoRulesTurma: [(v) => !!v || "Seleção Necessária"],
    lCursoRegApr: [],
    lTurmaRegApr: [],
    lAlunoTurmaRegApr:[],
    lQtdAlunoTurmaRegApr:[],
    i: 0,
    headers: [
      { text: "Curso", align: "start", value: "aluno.curso.nome", width: "18%", },
      { text: "Matricula", align: "start", value: "aluno.matricula", width: "18", },
      { text: "Alunos", value: "aluno.nome", width: "24%" },
      { text: "Status", value: "status", width: "30%" },
    ],
    dados: [
        {
        status: 'Aprovado',
        qtdAlunos: 0
      },

      {
        status: 'Aprovado Conselho',
        qtdAlunos: 0
      },

      {
        status: 'Aprovado Abono de Faltas',
        qtdAlunos: 0
      },

      {
        status: 'Cursando',
        qtdAlunos: 0
      },

      {
        status: 'Reprovado',
        qtdAlunos: 0
      },

      {
        status: 'Reprovado Por Falta',
        qtdAlunos: 0
      },
    ],
    headers2: [
      { text: "Status", align: "start", value: "status", width: "30%"},
      { text: "Qtd Alunos", value: "qtdAlunos", width: "10%" },
    ],
  }),
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      this.fetchRecordsCurso();
      this.fetchRecordsTodosAlunos();
      this.fetchRecordsQtdTodosAlunos();
    },
    fetchRecordsCurso() {
      serviceCurso.search({}).then(this.fetchRecordsSuccessCurso);
    },
    fetchRecordsSuccessCurso(response) {
      if (Array.isArray(response.rows)) {
        this.lCursoRegApr = response.rows;
        return;
      }
      this.lCursoRegApr = [];
    },
    fetchRecordsTodosAlunos() {
      serviceAlunoTurma.search({}).then(this.fetchRecordsSuccessTodosAlunos);
    },
    fetchRecordsSuccessTodosAlunos(response) {
      if (Array.isArray(response.rows)) {
        this.lAlunoTurmaRegApr = response.rows;
        return;
      }
      this.lAlunoTurmaRegApr = [];
    },
    fetchRecordsAlunosPorCurso() {
      serviceAlunoTurma.searchFiltro("findByCurso", this.curso).then(this.fetchRecordsSuccessAlunosPorCurso);
    },
    fetchRecordsSuccessAlunosPorCurso(response) {
      if (Array.isArray(response.rows)) {
        this.lAlunoTurmaRegApr = response.rows;
        return;
      }
      this.lAlunoTurmaRegApr = [];
    },
    fetchRecordsTurma() {
      serviceTurma.searchFiltro("findByCurso", this.curso).then(this.fetchRecordsSuccessTurma);
      this.fetchRecordsAlunosPorCurso();
      this.fetchRecordsQtdAlunosPorCurso();
    },
    fetchRecordsSuccessTurma(response) {
      if (Array.isArray(response.rows)) {
        this.lTurmaRegApr = response.rows;
        return;
      }
      this.lTurmaRegApr = [];
    },
    fetchRecordsAlunoTurma() {
      serviceAlunoTurma.searchFiltro("findByTurma", this.turma).then(this.fetchRecordsSuccessAlunoTurma);
      this.fetchRecordsQtdAlunosPorTurma();
    },
    fetchRecordsSuccessAlunoTurma(response) {
      if (Array.isArray(response.rows)) {
        this.lAlunoTurmaRegApr = response.rows;
        return;
      }
      this.lAlunoTurmaRegApr = [];
    },
    fetchRecordsQtdTodosAlunos() {
      serviceAlunoTurma.search("findQtdAlunoTotal").then(this.fetchRecordsSuccessQtdTodosAlunos);
    },
    fetchRecordsSuccessQtdTodosAlunos(response) {
      if (Array.isArray(response.rows)) {
        this.lQtdAlunoTurmaRegApr = response.rows;
        this.calculaQtdAlunos(this.lQtdAlunoTurmaRegApr);
        return;
      }
      this.lQtdAlunoTurmaRegApr = [];
    },
    fetchRecordsQtdAlunosPorCurso() {
      serviceAlunoTurma.searchFiltro("findQtdAlunoTotalPorCurso", this.curso).then(this.fetchRecordsSuccessQtdAlunosPorCurso);
    },
    fetchRecordsSuccessQtdAlunosPorCurso(response) {
      if (Array.isArray(response.rows)) {
        this.lQtdAlunoTurmaRegApr = response.rows;
        this.calculaQtdAlunos(this.lQtdAlunoTurmaRegApr);
        return;
      }
      this.lQtdAlunoTurmaRegApr = [];
    },
    fetchRecordsQtdAlunosPorTurma() {
      serviceAlunoTurma.searchFiltro("findQtdAlunoTotalPorTurma", this.turma).then(this.fetchRecordsSuccessQtdAlunosPorTurma);
    },
    fetchRecordsSuccessQtdAlunosPorTurma(response) {
      if (Array.isArray(response.rows)) {
        this.lQtdAlunoTurmaRegApr = response.rows;
        this.calculaQtdAlunos(this.lQtdAlunoTurmaRegApr);
        return;
      }
      this.lQtdAlunoTurmaRegApr = [];
    },
    calculaQtdAlunos(vetorDados){
      this.dados[0].qtdAlunos = 0;
      this.dados[1].qtdAlunos = 0;
      this.dados[2].qtdAlunos = 0;
      this.dados[3].qtdAlunos = 0;
      this.dados[4].qtdAlunos = 0;
      this.dados[5].qtdAlunos = 0;
      for(this.i = 0; this.i < vetorDados.length; this.i++){
          if(vetorDados[this.i].[0] == "APROVADO"){
            this.dados[0].qtdAlunos = vetorDados[this.i].[1];
            //break;
          }
          if(vetorDados[this.i].[0] == "APROV. CONSELHO"){
            this.dados[1].qtdAlunos = vetorDados[this.i].[1];
            //break;
          }
          if(vetorDados[this.i].[0] == "APROV. ABONO FALTAS"){
            this.dados[2].qtdAlunos = vetorDados[this.i].[1];
            //break;
          }
          if(vetorDados[this.i].[0] == "CURSANDO"){
            this.dados[3].qtdAlunos = vetorDados[this.i].[1];
            //break;
          }
          if(vetorDados[this.i].[0] == "REPROVADO"){
            this.dados[4].qtdAlunos = vetorDados[this.i].[1];
            //break;
          }
          if(vetorDados[this.i].[0] == "REPROV. FALTAS"){
            this.dados[5].qtdAlunos = vetorDados[this.i].[1];
            //break;
          }
        }
    },
    printOut(){

    }
  },
};
</script>