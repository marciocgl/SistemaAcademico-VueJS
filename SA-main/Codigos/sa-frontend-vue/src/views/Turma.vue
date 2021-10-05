<template>
  <v-data-table
    :headers="headers"
    :items="lTurma"
    sort-by="id"
    class="elevation-1"
    :loading="carregando" 
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Cadastro de Turma</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="800px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on"
              >Novo Item</v-btn
            >
          </template>
          <v-card>
            <v-form ref="form" v-model="valid">
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="12">
                      <v-text-field
                        v-model="editedItem.nome"
                        label="Nome"
                        outlined
                        required
                        :counter="200"
                        :rules="turmaRulesNome"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="4" md="6">
                      <v-combobox
                        v-model="editedItem.professor"
                        label="Professor"
                        outlined
                        :items="lProfessor"
                        item-text="nome"
                        required
                        :rules="turmaRulesProfessor"
                      ></v-combobox>
                    </v-col>
                    <v-col cols="12" sm="4" md="6">
                      <v-combobox
                        v-model="editedItem.curso"
                        label="Curso"
                        item-text="nome"
                        outlined
                        :items="lCurso"
                        required
                        @change="filtrarDisciplinaPorCurso"
                        :rules="turmaRulesCurso"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="4" md="6">
                      <v-combobox
                        v-model="editedItem.disciplina"
                        label="Disciplina"
                        outlined
                        required
                        :items="lDisciplinaFiltrada"
                        item-text="nome"
                        :rules="turmaRulesDisciplina"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="4" md="3">
                      <v-combobox
                        v-model="editedItem.periodoLetivo"
                        label="Período Letivo"
                        outlined
                        :items="lPeriodoLetivo"
                        required
                        item-text="nome"
                        :rules="turmaRulesPeriodoLetivo"
                      ></v-combobox>
                    </v-col>
                    <v-col cols="12" sm="6" md="3">
                      <v-text-field
                        v-model="editedItem.qtdVaga"
                        label="Vagas"
                        outlined
                        required
                        :rules="turmaRulesVagas"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-menu
                        ref="menuEntrada"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="290px"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="editedItem.dataInicio"
                            label="Data de Inicio"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            outlined
                            required
                           
                          ></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="editedItem.dataInicio"
                          no-title
                          scrollable
                        >
                          <v-spacer></v-spacer>
                          <v-btn
                            text
                            color="primary"
                            @click="menuEntrada = false"
                            >Cancelar</v-btn
                          >
                          <v-btn
                            text
                            color="primary"
                            @click="$refs.menuEntrada.save(dataInicio)"
                            >OK</v-btn
                          >
                        </v-date-picker>
                      </v-menu>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-menu
                        ref="menuEntrada"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="290px"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="editedItem.dataFim"
                            label="Data de Fim"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            outlined
                            required
                       
                          ></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="editedItem.dataFim"
                          no-title
                          scrollable
                        >
                          <v-spacer></v-spacer>
                          <v-btn
                            text
                            color="primary"
                            @click="menuEntrada = false"
                            >Cancelar</v-btn
                          >
                          <v-btn
                            text
                            color="primary"
                            @click="$refs.menuEntrada.save(dataFim)"
                            >OK</v-btn
                          >
                        </v-date-picker>
                      </v-menu>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" v-text="false"
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
      <v-btn color="primary" @click="initialize">Alterar</v-btn>
    </template>
  </v-data-table>
</template>

<script>
import { mask } from "@titou10/v-mask";
import TurmaService from "../service/domain/TurmaService";
import ProfessorService from "../service/domain/ProfessorService";
import DisciplinaService from "../service/domain/DisciplinaService";
import CursoService from "../service/domain/CursoService";
import PeriodoLetivoService from "../service/domain/PeriodoLetivoService";
const serviceProfessor = ProfessorService.build();
const serviceDisciplina = DisciplinaService.build();
const serviceCurso = CursoService.build();
const servicePeriodoLetivo = PeriodoLetivoService.build();

const textos = {
  novo: "Nova Turma",
  edicao: "Edição de Turma",
  exclusao: "Deseja mesmo remover esta Turma?",
};

export default {
  directives: { mask },
  data: () => ({
    service: TurmaService.build(),
    dialog: false,
    dialogExcluir: false,
    valid: true,
    turmaRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 50 && v.length >= 3) ||
        "O campo deve ter pelo menos 3 e no maximo 50 letras",
    ],
    turmaRulesVagas: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 1) ||
        "O campo deve ter pelo menos 3 e no maximo 20 digitos",
    ],
    turmaRulesCurso: [(v) => !!v || "Seleção Necessária"],
    turmaRulesProfessor: [(v) => !!v || "Seleção Necessária"],
    turmaRulesDisciplina: [(v) => !!v || "Seleção Necessária"],
    turmaRulesPeriodoLetivo: [(v) => !!v || "Seleção Necessária"],
    headers: [
      { text: "ID", value: "id" },
      { text: "Nome", align: "start", value: "nome" },
      { text: "Professor", align: "start", value: "professor.nome" },
      { text: "Curso", align: "start", value: "curso.nome" },
      { text: "Disciplina", align: "start", value: "disciplina.nome" },
      { text: "Período Letivo", align: "start", value: "periodoLetivo.nome" },
      { text: "Vagas", align: "start", value: "qtdVaga" },
      { text: "Data Início", align: "start", value: "dataInicio" },
      { text: "Data Fim", align: "start", value: "dataFim" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lTurma: [],
    lProfessor: [],
    lCurso: [],
    lDisciplina: [],
    lDisciplinaFiltrada: [],
    lPeriodoLetivo: [],
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
      this.fetchRecordsProfessor();
      this.fetchRecordsCurso();
      this.fetchRecordsDisciplina();
      this.fetchRecordsPeriodoLetivo();
    },
    fetchRecords() {
      this.service.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsProfessor() {
      serviceProfessor.search({}).then(this.fetchRecodsSuccessProfessor);
    },
    fetchRecordsCurso() {
      serviceCurso.search({}).then(this.fetchRecodsSuccessCurso);
    },
    fetchRecordsDisciplina() {
      serviceDisciplina.search({}).then(this.fetchRecodsSuccessDisciplina);
    },
    fetchRecordsPeriodoLetivo() {
      servicePeriodoLetivo
        .search({})
        .then(this.fetchRecodsSuccessPeriodoLetivo);
    },
    fetchRecodsSuccess(response) {
      if (Array.isArray(response.rows)) {
        this.lTurma = response.rows;
        return;
      }
      this.lTurma = [];
    },
    fetchRecodsSuccessProfessor(response) {
      if (Array.isArray(response.rows)) {
        this.lProfessor = response.rows;
        return;
      }
      this.lProfessor = [];
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
    fetchRecodsSuccessPeriodoLetivo(response) {
      if (Array.isArray(response.rows)) {
        this.lPeriodoLetivo = response.rows;
        return;
      }
      this.lPeriodoLetivo = [];
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
      this.editedIndex = this.lTurma.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lTurma.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      this.service
        .destroy(this.editedItem)
        .then(this.lTurma.splice(this.editedIndex, 1));
      this.lTurma.splice(this.editedIndex, 1);
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
          .then(Object.assign(this.lTurma[this.editedIndex], this.editedItem));
      } else {
        this.service
          .create(this.editedItem)
          .then((response) => this.lTurma.push(response));
      }
      this.close();
    },
  },
};
</script>