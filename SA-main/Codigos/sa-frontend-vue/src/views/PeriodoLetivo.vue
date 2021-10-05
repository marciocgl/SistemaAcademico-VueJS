<template>
  <v-data-table
    :headers="headers"
    :items="lPeriodoLetivo"
    sort-by="id"
    class="elevation-1"
    :loading="carregando" 
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Cadastro de PeriodoLetivo</v-toolbar-title>
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
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.nome"
                        label="Nome"
                        outlined
                        required
                        :counter="6"
                        v-mask="{ mask: '####/#' }"
                        :rules="periodoLetivoRulesNome"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
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
                            label="Data de Início"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            outlined
                            required
                            :rules="modeloRulesDataIncio"
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

                    <v-col cols="12" sm="6" md="4">
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
                            :rules="modeloRulesDataFim"
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

                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.qtddiaLetivo"
                        label="Nº de Dias Letivos"
                        outlined
                        required
                        :rules="periodoletivoRulesNumeroDiasLetivos"
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
import PeriodoLetivoService from "../service/domain/PeriodoLetivoService";
const servicePeriodoLetivo = PeriodoLetivoService.build();
import CursoService from "../service/domain/CursoService";
const serviceCurso = CursoService.build();
const textos = {
  novo: "Novo PeriodoLetivo",
  edicao: "Edição de PeriodoLetivo",
  exclusao: "Deseja mesmo remover este PeriodoLetivo?",
};
export default {
  name: "lPeriodoLetivo",
  components: {},
  data: () => ({
    carregando: true,
    dialog: false,
    dialogExcluir: false,
    valid: true,
    periodoLetivoRulesCurso: [(v) => !!v || "Seleção Necessária"],
    periodoLetivoRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 3) ||
        "O campo deve ter pelo menos 3 e no maximo 20 letras",
    ],
   periodoletivoRulesNumeroDiasLetivos: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 1) ||
        "O campo deve ter pelo menos 1 e no maximo 20 digitos",
    ],
    headers: [
      { text: "ID", value: "id" },
      { text: "Período Letivo", align: "start", value: "nome" },
      { text: "Data Inicio", value: "dataInicio" },
      { text: "Data Fim", value: "dataFim" },
      { text: "Nº de dias Letivos", value: "qtddiaLetivo" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lPeriodoLetivo: [],
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
      servicePeriodoLetivo.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsCurso() {
      serviceCurso.search({}).then(this.fetchRecodsSuccessCurso);
    },
    fetchRecodsSuccess(response) {
      setTimeout(() => {
        if (Array.isArray(response.rows)) {
        this.lPeriodoLetivo = response.rows;
        this.carregando = false
        return;
      }
      this.lPeriodoLetivo = [];
      this.carregando = false
      }, 1500);
      
      
    },
    fetchRecodsSuccessCurso(response) {
      if (Array.isArray(response.rows)) {
        this.lCurso = response.rows;
        return;
      }
      this.lCurso = [];
    },

    editItem(item) {
      this.editedIndex = this.lPeriodoLetivo.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lPeriodoLetivo.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      //const index = this.lPeriodoLetivo.indexOf(this.editedItem);
      servicePeriodoLetivo
        .destroy(this.editedItem)
        .then(this.lPeriodoLetivo.splice(this.editedIndex, 1));
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
        servicePeriodoLetivo
          .update(this.editedItem)
          .then(
            Object.assign(
              this.lPeriodoLetivo[this.editedIndex],
              this.editedItem
            )
          );
      } else {
        servicePeriodoLetivo
          .create(this.editedItem)
          .then((response) => this.lPeriodoLetivo.push(response));
      }
      this.close();
    },
  },
};
</script>