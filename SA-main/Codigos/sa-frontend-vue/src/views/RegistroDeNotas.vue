<template>
  <v-data-table
    :headers="headers"
    :items="lRegistroDeNotas"
    sort-by="id"
    class="elevation-1"
    :loading="carregando"
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-spacer
          ><center>
            <v-toolbar-title>Registro De Notas</v-toolbar-title>
          </center></v-spacer
        >
        <v-dialog v-model="dialog" max-width="550">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on"
              >Inserir Notas</v-btn
            >
          </template>
          <v-card>
            <v-form ref="form" v-model="valid">
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row align="center" justify="center">
                    <v-col cols="12" sm="4" md="12">
                      <v-combobox
                        v-model="editedItem.disciplina"
                        label="Disciplina"
                        outlined
                        :items="lDisciplina"
                        item-text="nome"
                        @change="filtrarAtividadeAvaliativasPorDisciplina"
                        required
                        :rules="registroDeNotasRulesDisciplina"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="8" md="12">
                      <v-combobox
                        v-model="editedItem.atividadeAvaliativa"
                        :items="lAtividadeAvaliativaFiltrada"
                        label="Atividade Avaliativa"
                        item-text="nome"
                        outlined
                        required
                        :rules="registroDeNotasRulesAtividadeAvaliativa"
                      ></v-combobox>
                    </v-col>
                    <!-- PRECISA CORRIGIR - LISTAR APENAS OS ALUNOS QUE PERTENCEREM A DISCIPLINA SELECIONADA -->
                    <v-col cols="12" sm="12" md="12">
                      <v-combobox
                        v-model="editedItem.aluno"
                        :items="lAluno"
                        label="Aluno"
                        item-text="nome"
                        outlined
                        required
                        :rules="registroDeNotasRulesAluno"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="6" sm="6" md="5">
                      <v-text-field
                        v-mask="{ mask: '##.##' }"
                        v-model="editedItem.nota"
                        label="Nota"
                        outlined
                        required
                        :counter="5"
                        :rules="registroDeNotasRulesNota"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>

                <!-- PRECISA CORRIGIR - FALTA DAR UMM REFRESH NA HORA DE CANCELAR PARA VOLTAR AS SELEÇOES ANTERIORES A NULL -->
                <v-btn color="blue darken-1" text @click="close"
                  >Cancelar</v-btn
                >
                <v-btn
                  :disabled="!valid"
                  color="blue darken-1"
                  text
                  @click="save"
                  >Salvar</v-btn
                >
              </v-card-actions>
            </v-form>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogExcluir" max-width="430px">
          <v-card>
            <v-card-title class="headline"
              >Deseja mesmo remover este Item?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeExcluir"
                >Cancelar</v-btn
              >
              <v-btn color="blue darken-1" text @click="deleteItemComfirm"
                >Sim</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
      <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
    </template>
    <template v-slot:no-data>
      <!-- PRECISA CORRIGIR - ALTERAR APENAS AS NOTAS, E DESABILITAR OS OUTROS CAMPOS. -->
      <v-btn color="primary" @click="initialize">Alterar</v-btn>
    </template>
  </v-data-table>
</template>

 
<script>
import { mask } from "@titou10/v-mask";
import RegistroDeNotasService from "../service/domain/RegistroDeNotasService";
import AlunoService from "../service/domain/AlunoService";
import AtividadeAvaliativaService from "../service/domain/AtividadeAvaliativaService";
import DisciplinaService from "../service/domain/DisciplinaService";

import TurmaService from "../service/domain/TurmaService";
const serviceTurma = TurmaService.build();

const serviceDisciplina = DisciplinaService.build();
const serviceAluno = AlunoService.build();
const serviceAtividadeAvaliativa = AtividadeAvaliativaService.build();

const textos = {
  novo: "Inserir Notas",
  edicao: "Edição de Registro De Notas",
  exclusao: "Deseja mesmo remover este Registro De Notas?",
};

export default {
  directives: { mask },
  data: () => ({
    service: RegistroDeNotasService.build(),
    dialog: false,
    dialogExcluir: false,
    valid: true,

    registroDeNotasRulesDisciplina: [(v) => !!v || "Seleção Necessária"],
    registroDeNotasRulesAtividadeAvaliativa: [
      (v) => !!v || "Seleção Necessária",
    ],
    registroDeNotasRulesAluno: [(v) => !!v || "Seleção Necessária"],

    headers: [
      { text: "ID", value: "id" },
      { text: "Aluno", align: "start", value: "aluno.nome" },
      { text: "Matricula", align: "start", value: "aluno.matricula" },
      { text: "Disciplina", align: "start", value: "disciplina.nome" },
      { text: "Avaliação", align: "start", value: "atividadeAvaliativa.nome" },
      { text: "Valor", align: "start", value: "atividadeAvaliativa.valor" },
      { text: "Nota", align: "start", value: "nota" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lRegistroDeNotas: [],
    lAluno: [],
    lAlunoFiltrada: [],
    lDisciplina: [],
    lAtividadeAvaliativa: [],
    lAtividadeAvaliativaFiltrada: [],
    lTurma: [],
    lTurmaFiltrada: [],
    editedIndex: -1,
    editedItem: {},
    defaultItem: {},
  }),
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? textos.novo : textos.edicao;
    },
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogExcluir(val) {
      val || this.closeExcluir();
    },
  },
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      this.fetchRecords();
      this.fetchRecordsDisciplina();
      this.fetchRecordsAtividadeAvaliativa();
      this.fetchRecordsAluno();
      this.fetchRecordsTurma();
    },
    fetchRecords() {
      this.service.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsDisciplina() {
      serviceDisciplina.search({}).then(this.fetchRecodsSuccessDisciplina);
    },
    fetchRecordsAtividadeAvaliativa() {
      serviceAtividadeAvaliativa
        .search({})
        .then(this.fetchRecodsSuccessAtividadeAvaliativa);
    },
    fetchRecordsAluno() {
      serviceAluno.search({}).then(this.fetchRecodsSuccessAluno);
    },
    fetchRecordsTurma() {
      serviceTurma.search({}).then(this.fetchRecodsSuccessTurma);
    },
    fetchRecodsSuccess(response) {
      if (Array.isArray(response.rows)) {
        this.lRegistroDeNotas = response.rows;
        return;
      }
      this.lRegistroDeNotas = [];
    },
    fetchRecodsSuccessDisciplina(response) {
      if (Array.isArray(response.rows)) {
        this.lDisciplina = response.rows;
        return;
      }
      this.lDisciplina = [];
    },

    fetchRecodsSuccessAtividadeAvaliativa(response) {
      if (Array.isArray(response.rows)) {
        this.lAtividadeAvaliativa = response.rows;
        return;
      }
      this.lAtividadeAvaliativa = [];
    },
    fetchRecodsSuccessAluno(response) {
      if (Array.isArray(response.rows)) {
        this.lAluno = response.rows;
        return;
      }
      this.lAluno = [];
    },
    fetchRecodsSuccessTurma(response) {
      if (Array.isArray(response.rows)) {
        this.lTurma = response.rows;
        return;
      }
      this.lTurma = [];
    },

    filtrarAtividadeAvaliativasPorDisciplina() {
      this.resetSelecaoAtividadeAvaliativa();
      /*  this.resetSelecaoTurma(); */
      this.lAtividadeAvaliativaFiltrada = this.lAtividadeAvaliativa.filter(
        (atividadeAvaliativa) =>
          atividadeAvaliativa.disciplina.id == this.editedItem.disciplina.id
      );

      /* this.lTurmaFiltrada = this.lTurma.filter(
        (turma) => turma.disciplina.id == this.editedItem.disciplina.id,
        (aluno) => aluno.turma.id == this.editedItem.disciplina.id
      ); */
    },

    /*     filtrarDisciplinasPorTurma() {
      this.resetSelecaoDisciplina();

      this.lDisciplinaFiltrada = this.lDisciplina.filter(
        (Disciplina) => Disciplina.Turma.id == this.editedItem.Turma.id
      );
    },

    filtrarAlunosPorTurma() {
      this.resetSelecaoAluno();

      this.lAlunoFiltrada = this.lAluno.filter(
        (Aluno) => Aluno.Turma.id == this.editedItem.Turma.id
      );
    },

    resetSelecaoAluno() {
      this.lAlunoFiltrada = [];
      this.editedItem.Aluno = null;
    },

    resetSelecaoDisciplina() {
      this.lDisciplinaFiltrada = [];
      this.editedItem.Disciplina = null;
    },
        resetSelecaoTurma() {
      this.lTurma = [];
      this.editedItem.turma = null;
    },
    */

    resetSelecaoAtividadeAvaliativa() {
      this.lAtividadeAvaliativaFiltrada = [];
      this.editedItem.atividadeAvaliativa = null;
    },

    editItem(item) {
      this.editedIndex = this.lRegistroDeNotas.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lRegistroDeNotas.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      this.service
        .destroy(this.editedItem)
        .then(this.lRegistroDeNotas.splice(this.editedIndex, 1));
      this.closeExcluir();
    },
    closeExcluir() {
      this.dialogExcluir = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {
      if (this.editedIndex > -1) {
        this.service
          .update(this.editedItem)
          .then(
            Object.assign(
              this.lRegistroDeNotas[this.editedIndex],
              this.editedItem
            )
          );
      } else {
        this.service
          .create(this.editedItem)
          .then((response) => this.lRegistroDeNotas.push(response));
      }
      this.close();
    },
  },
};
</script>