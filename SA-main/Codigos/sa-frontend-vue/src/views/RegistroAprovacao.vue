<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <v-toolbar-title
            ><center>Registro de Aprovação</center></v-toolbar-title
          >
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
    <v-form ref="form">
      <v-data-table
        :headers="headers"
        :items="lAlunoTurmaRegApr"
        item-key="alunoTurma"
        :show-select="false"
        class="elevation-1">

        <template v-slot:item.status="{ item }">
        <v-select
          v-model="item.status"
          :items="cSel"
          
      ></v-select>
      </template> 
      </v-data-table>
    </v-form >
    </v-container>

    <v-container>
      <div class="d-flex justify-center mb-6">
        <div class="mr-2">
          <v-btn block 
          color="blue darken-1"
          :disabled="disableBotao"
           @click="save"
          >Salvar</v-btn>
        </div>
        <div class="mr-2">
          <v-btn block 
          color="red"
          :disabled="disableBotao"
          @click="fetchRecordsAlunoTurma"
          >Resetar</v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import RegistroAprovacaoService from "../service/domain/RegistroAprovacaoService";

import CursoService from "../service/domain/CursoService";
const serviceCurso = CursoService.build();

import TurmaService from "../service/domain/TurmaService";
const serviceTurma = TurmaService.build();

import AlunoTurmaService from "../service/domain/AlunoTurmaService";
const serviceAlunoTurma = AlunoTurmaService.build();

//const textos = {
  //novo: "Registrar Aprovação",
  //edicao: "Edição de Registro De Aprovação",
  //exclusao: "Deseja mesmo remover estes Registrso De Aprovação?",
//};

export default {
   data: () => ({
    service: RegistroAprovacaoService.build(),
    components: {},
    registroAprovacaoRulesCurso: [(v) => !!v || "Seleção Necessária"],
    registroAprovacaoRulesTurma: [(v) => !!v || "Seleção Necessária"],
    cSel: ['APROVADO', 'APROV. CONSELHO', 'APROV. ABONO FALTAS', 'CURSANDO', 'REPROVADO', 'REPROV. FALTAS'], // setando manualmente porque esse atributo deveria ser um enum
      headers: [
        { text: "Matrícula", align: "start", value: "aluno.matricula", width: "25%" },
        { text: "Aluno", value: "aluno.nome", width: "25%"
        },
        { text: "Faltas", align: "center", value: "presencaFinal", width: "15%" },
        { text: "Nota Final", align: "center", value: "notaFinal", width: "15%" },
        { text: "Status", align: "center", value: "status", width: "20%" },
      ],
      lCursoRegApr: [],
      lTurmaRegApr: [],
      lAlunoTurmaRegApr:[],
      lAlunoRegApr:[],

      disableBotao: true,
      disableComboboxStatus: true,
      usuario: 1, //por enquanto setando manualmente o usuário
      i: 0,
      teste: null,
  }),
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      this.fetchRecordsCurso();
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
    fetchRecordsTurma() {
      serviceTurma.searchFiltro("findByCurso", this.curso).then(this.fetchRecordsSuccessTurma);
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
    },
    fetchRecordsSuccessAlunoTurma(response) {
      if (Array.isArray(response.rows)) {
        this.lAlunoTurmaRegApr = response.rows;
        this.disableBotao = false;
        if(this.usuario == 1){
          this.disableComboboxStatus = false;
        }
        for(this.i = 0; this.i < this.lAlunoTurmaRegApr.length; this.i++){
          if(this.lAlunoTurmaRegApr[this.i].status == "CURSANDO"){
            if(this.lAlunoTurmaRegApr[this.i].notaFinal >= 60){
              this.lAlunoTurmaRegApr[this.i].status = "APROVADO";
            }else{
              this.lAlunoTurmaRegApr[this.i].status = "REPROVADO";
            }
          }
        }
        return;
      }
      this.lAlunoTurmaRegApr = [];
    },
    save(){
        this.service.create(this.lAlunoTurmaRegApr).then((response) => this.lAlunoTurmaRegApr.push(response));
    },
  },
};
</script>