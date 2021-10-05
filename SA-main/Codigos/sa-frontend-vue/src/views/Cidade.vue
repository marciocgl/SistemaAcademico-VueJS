<template>
  <v-data-table
    :headers="headers"
    :items="lCidade"
    sort-by="id"
    class="elevation-1"
    :loading="carregando" 
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Cadastro de Cidade</v-toolbar-title>
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
                        :items="lUf"
                        item-text="nome"
                        item-value="id"
                        label="UF"
                        v-model="editedItem.uf"
                        outlined
                        required
                        :rules="cidadeRulesUf"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.nome"
                        label="Nome"
                        outlined
                        required
                        :counter="200"
                        :rules="cidadeRulesNome"
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
import CidadeService from "../service/domain/CidadeService";
const serviceCidade = CidadeService.build();
import UfService from "../service/domain/UfService";
const serviceUf = UfService.build();
const textos = {
  novo: "Novo Cidade",
  edicao: "Edição de Cidade",
  exclusao: "Deseja mesmo remover este Cidade?",
};
export default {
  name: "lCidade",
  components: {},
  data: () => ({
    dialog: false,
    dialogExcluir: false,
    valid: true,
    cidadeRulesUf: [(v) => !!v || "Seleção Necessária"],
    cidadeRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 20 && v.length >= 3) ||
        "O campo deve ter pelo menos 3 e no maximo 20 letras",
    ],
    headers: [
      { text: "ID", value: "id" },
      { text: "Nome", align: "start", value: "nome" },
      { text: "UF", value: "uf.nome" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lCidade: [],
    lUf: [],
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
      this.fetchRecordsUf();
    },
    fetchRecords() {
      serviceCidade.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsUf() {
      serviceUf.search({}).then(this.fetchRecodsSuccessUf);
    },
    fetchRecodsSuccess(response) {
      if (Array.isArray(response.rows)) {
        this.lCidade = response.rows;
        return;
      }
      this.lCidade = [];
    },
    fetchRecodsSuccessUf(response) {
      if (Array.isArray(response.rows)) {
        this.lUf = response.rows;
        return;
      }
      this.lUf = [];
    },

    editItem(item) {
      this.editedIndex = this.lCidade.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lCidade.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      //const index = this.lCidade.indexOf(this.editedItem);
      serviceCidade
        .destroy(this.editedItem)
        .then(this.lCidade.splice(this.editedIndex, 1));
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
        serviceCidade
          .update(this.editedItem)
          .then(Object.assign(this.lCidade[this.editedIndex], this.editedItem));
      } else {
        serviceCidade
          .create(this.editedItem)
          .then((response) => this.lCidade.push(response));
      }
      this.close();
    },
  },
};
</script>