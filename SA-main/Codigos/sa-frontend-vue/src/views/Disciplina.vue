<template>
  <v-data-table
    :headers="headers"
    :items="lDisciplina"
    sort-by="id"
    class="elevation-1"
    :loading="carregando" 
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Cadastro de Disciplina</v-toolbar-title>
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
                    <v-col cols="12" sm="6" md="6">
                      <v-combobox
                        :items="lCurso"
                        item-text="nome"
                        label="Curso"
                        v-model="editedItem.curso"
                        outlined
                        required
                        :rules="disciplinaRulesCurso"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.nome"
                        label="Nome"
                        outlined
                        required
                        :counter="200"
                        :rules="disciplinaRulesNome"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.nomeReduzido"
                        label="Nome Reduzido"
                        outlined
                        required
                        :counter="200"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.cargaHoraria"
                        label="Carga Horaria"
                        outlined
                        required
                        :rules="disciplinaRulesCargaHoraria"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
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
      <v-btn color="primary" @click="initialize">Resetar</v-btn>
    </template>
  </v-data-table>
</template>

<script>
import DisciplinaService from "../service/domain/DisciplinaService";
const serviceDisciplina = DisciplinaService.build();
import CursoService from "../service/domain/CursoService";
const serviceCurso = CursoService.build();
const textos = {
  novo: "Novo Disciplina",
  edicao: "Edição de Disciplina",
  exclusao: "Deseja mesmo remover este Disciplina?",
};
export default {
  name: "lDisciplina",
  components: {},
  data: () => ({
    dialog: false,
    dialogExcluir: false,
    valid: true,
    disciplinaRulesCurso: [(v) => !!v || "Seleção Necessária"],
    disciplinaRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 3) ||
        "O campo deve ter pelo menos 3 e no maximo 20 letras",
    ],
    disciplinaRulesCargaHoraria: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 1) ||
        "O campo deve ter pelo menos 1 e no maximo 20 digitos",
    ],
    headers: [
      { text: "ID", value: "id" },
      { text: "Curso", align: "start", value: "curso.nome" },
      { text: "Nome", align: "start", value: "nome" },
      { text: "Nome Reduzido", value: "nomeReduzido" },
      { text: "Carga Horária (Horas)", value: "cargaHoraria" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lDisciplina: [],
    lCurso: [],
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
    initialize(){
      this.fetchRecords();
    this.fetchRecordsCurso();
    },
    fetchRecords() {
      serviceDisciplina.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsCurso() {
      serviceCurso.search({}).then(this.fetchRecodsSuccessCurso);
    },
    fetchRecodsSuccess(response) {
      if (Array.isArray(response.rows)) {
        this.lDisciplina = response.rows;
        return;
      }
      this.lDisciplina = [];
    },
    fetchRecodsSuccessCurso(response) {
      if (Array.isArray(response.rows)) {
        this.lCurso = response.rows;
        return;
      }
      this.lCurso = [];
    },

    editItem(item) {
      this.editedIndex = this.lDisciplina.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lDisciplina.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      //const index = this.lDisciplina.indexOf(this.editedItem);
      serviceDisciplina
        .destroy(this.editedItem)
        .then(this.lDisciplina.splice(this.editedIndex, 1));
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
        console.log(this.editedItem);
        serviceDisciplina
          .update(this.editedItem)
          .then(
            Object.assign(this.lDisciplina[this.editedIndex], this.editedItem)
          );
      } else {
        serviceDisciplina
          .create(this.editedItem)
          .then((response) => this.lDisciplina.push(response));
      }
      this.close();
    },
  },
};
</script>