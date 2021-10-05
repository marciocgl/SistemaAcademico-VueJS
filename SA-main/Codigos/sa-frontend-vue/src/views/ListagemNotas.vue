

<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <v-toolbar-title><center>Listagem de Notas</center></v-toolbar-title>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-toolbar>
        <v-spacer>
          <br />
          <v-row>
            <v-col cols="12" sm="4" md="6">
              <v-combobox
                v-model="editedItem.turma"
                label="Turma"
                outlined
                :items="lTurma"
                required
                item-text="nome"
                :rules="registroDeNotasRulesTurma"
              ></v-combobox>
            </v-col>
          </v-row>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-data-table
        :headers="headers"
        :items="codigos"
        class="elevation-1"
        :hide-default-footer="true"
      >
      </v-data-table>
    </v-container>
  </div>
</template>

<script>
import { mask } from "@titou10/v-mask";
import RegistroDeNotasService from "../service/domain/RegistroDeNotasService";
import TurmaService from "../service/domain/TurmaService";
import DisciplinaService from "../service/domain/DisciplinaService";
import CursoService from "../service/domain/CursoService";
import AtividadeAvaliativaService from "../service/domain/AtividadeAvaliativaService";
const serviceTurma = TurmaService.build();
const serviceDisciplina = DisciplinaService.build();
const serviceCurso = CursoService.build();
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
    registroDeNotasRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 50 && v.length >= 3) ||
        "O campo deve ter pelo menos 3 e no maximo 50 letras",
    ],
    registroDeNotasRulesVagas: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 1) ||
        "O campo deve ter pelo menos 3 e no maximo 20 digitos",
    ],
    registroDeNotasRulesCurso: [(v) => !!v || "Seleção Necessária"],
    registroDeNotasRulesTurma: [(v) => !!v || "Seleção Necessária"],
    registroDeNotasRulesDisciplina: [(v) => !!v || "Seleção Necessária"],
    registroDeNotasRulesAtividadeAvaliativa: [
      (v) => !!v || "Seleção Necessária",
    ],
    headers: [
      { text: "ID", value: "id" },
      { text: "Aluno", align: "start", value: "aluno.nome" },
      { text: "Matricula", align: "start", value: "aluno.matricula" },
      { text: "Disciplina", align: "start", value: "disciplina.nome" },
      { text: "Avaliação", align: "start", value: "atividadeAvaliativa.nome" },
      {
        text: "Tipo de Avaliação",
        align: "start",
        value: "atividadeAvaliativa.tipo",
      },
      { text: "Nota", align: "start", value: "nota" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lRegistroDeNotas: [],
    lCurso: [],
    lDisciplina: [],
    lDisciplinaFiltrada: [],
    lAtividadeAvaliativa: [],
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
      this.fetchRecordsTurma();
      this.fetchRecordsCurso();
      this.fetchRecordsDisciplina();
      this.fetchRecordsAtividadeAvaliativa();
    },
    fetchRecords() {
      this.service.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsTurma() {
      serviceTurma.search({}).then(this.fetchRecodsSuccessTurma);
    },
    fetchRecordsCurso() {
      serviceCurso.search({}).then(this.fetchRecodsSuccessCurso);
    },
    fetchRecordsDisciplina() {
      serviceDisciplina.search({}).then(this.fetchRecodsSuccessDisciplina);
    },
    fetchRecordsAtividadeAvaliativa() {
      serviceAtividadeAvaliativa
        .search({})
        .then(this.fetchRecodsSuccessAtividadeAvaliativa);
    },
    fetchRecodsSuccess(response) {
      if (Array.isArray(response.rows)) {
        this.lRegistroDeNotas = response.rows;
        return;
      }
      this.lRegistroDeNotas = [];
    },
    fetchRecodsSuccessTurma(response) {
      if (Array.isArray(response.rows)) {
        this.lTurma = response.rows;
        return;
      }
      this.lTurma = [];
    },
    fetchRecodsSuccessCurso(response) {
      if (Array.isArray(response.rows)) {
        this.lCurso = response.rows;
        return;
      }
      this.lCurso = [];
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

    filtrarDisciplinaPorCurso() {
      this.resetSelecaoDisciplina();
      this.lDisciplinaFiltrada = this.lDisciplina.filter(
        (disciplina) => disciplina.curso.id == this.editedItem.curso.id
      );
    },
    resetSelecaoDisciplina() {
      this.lDisciplinaFiltrada = [];
      this.editedItem.disciplina = null;
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
      this.lRegistroDeNotas.splice(this.editedIndex, 1);
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