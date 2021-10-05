<template>
  <v-data-table
    :headers="headers"
    :items="lAtividadeAvaliativa"
    sort-by="id"
    class="elevation-1"
    :loading="carregando" 
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title
          >Cadastro de Atividade Avaliativa (Avaliação)</v-toolbar-title
        >
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="800px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on"
              >Inserir Atividade Avaliativa</v-btn
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
                        :items="lDisciplina"
                        item-text="nome"
                        label="Disciplina"
                        v-model="editedItem.disciplina"
                        outlined
                        required
                        :rules="atividadeAvaliativaRulesDisciplina"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.nome"
                        label="Nome"
                        outlined
                        required
                        :rules="atividadeAvaliativaRulesNome"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.tipo"
                        label="Tipo"
                        outlined
                        required
                        :rules="atividadeAvaliativaRulesTipo"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="5">
                      <v-menu
                        ref="menuEntrada"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="290px"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="editedItem.dataAvaliacao"
                            label="Data da Avaliação"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            outlined
                            required
                            :rules="atividadeAvaliativaRulesdataAvaliacao"
                          ></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="editedItem.dataAvaliacao"
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
                            @click="$refs.menuEntrada.save(dataAvaliacao)"
                            >OK</v-btn
                          >
                        </v-date-picker>
                      </v-menu>
                    </v-col>
                    <v-col cols="12" sm="6" md="3">
                      <v-text-field
                        v-model="editedItem.valor"
                        label="Valor"
                        outlined
                        required
                        :rules="atividadeAvaliativaRulesValor"
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
import AtividadeAvaliativaService from "../service/domain/AtividadeAvaliativaService";
const serviceAtividadeAvaliativa = AtividadeAvaliativaService.build();
import DisciplinaService from "../service/domain/DisciplinaService";
const serviceDisciplina = DisciplinaService.build();
const textos = {
  novo: "Nova Atividade Avaliativa(Avaliação)",
  edicao: "Edição de Atividade Avaliativa(Avaliação)",
  exclusao: "Deseja mesmo remover este Atividade Avaliativa(Avaliação)?",
};
export default {
  name: "lAtividadeAvaliativa",
  components: {},
  data: () => ({
    dialog: false,
    dialogExcluir: false,
    valid: true,
    atividadeAvaliativaRulesDisciplina: [(v) => !!v || "Seleção Necessária"],
    atividadeAvaliativaRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 100 && v.length >= 3) ||
        "O campo deve ter pelo menos 3 e no maximo 100 letras",
    ],
    atividadeAvaliativaRulesTipo: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 3) ||
        "O campo deve ter pelo menos 3 e no maximo 20 letras",
    ],
    atividadeAvaliativaRulesValor: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 1) ||
        "O campo deve ter pelo menos 1 e no maximo 20 digitos",
    ],
    headers: [
      { text: "ID", value: "id" },
      { text: "Nome", align: "start", value: "nome" },
      { text: "Tipo",  value: "tipo" },
      { text: "Data da Avaliação", value: "dataAvaliacao" },
      { text: "Valor", value: "valor" },
      { text: "Disciplina", value: "disciplina.nome" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lAtividadeAvaliativa: [],
    lDisciplina: [],
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
    },
    fetchRecords() {
      serviceAtividadeAvaliativa.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsDisciplina() {
      serviceDisciplina.search({}).then(this.fetchRecodsSuccessDisciplina);
    },
    fetchRecodsSuccess(response) {
      if (Array.isArray(response.rows)) {
        this.lAtividadeAvaliativa = response.rows;
        return;
      }
      this.lAtividadeAvaliativa = [];
    },
    fetchRecodsSuccessDisciplina(response) {
      if (Array.isArray(response.rows)) {
        this.lDisciplina = response.rows;
        return;
      }
      this.lDisciplina = [];
    },

    editItem(item) {
      this.editedIndex = this.lAtividadeAvaliativa.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lAtividadeAvaliativa.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      //const index = this.lAtividadeAvaliativa.indexOf(this.editedItem);
      serviceAtividadeAvaliativa
        .destroy(this.editedItem)
        .then(this.lAtividadeAvaliativa.splice(this.editedIndex, 1));
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
        serviceAtividadeAvaliativa
          .update(this.editedItem)
          .then(
            Object.assign(
              this.lAtividadeAvaliativa[this.editedIndex],
              this.editedItem
            )
          );
      } else {
        serviceAtividadeAvaliativa
          .create(this.editedItem)
          .then((response) => this.lAtividadeAvaliativa.push(response));
      }
      this.close();
    },
  },
};
</script>