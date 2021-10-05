<template>

  <v-data-table
    :headers="headers"
    :items="lAdministrador"
    sort-by="id"
    class="elevation-1"
    :loading="carregando" 
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Cadastro de Administrador</v-toolbar-title>
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
                    <v-col cols="12" sm="6" md="10">
                      <v-text-field
                        v-model="editedItem.nome"
                        label="Nome"
                        prepend-icon="mdi-account"
                        outlined
                        required
                        :counter="200"
                        :rules="administradorRulesNome"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.senha"
                        label="Senha"
                        prepend-icon="mdi-lock"
                        outlined
                        required
                        :counter="200"
                        :rules="administradorRulesSenha"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.email"
                        label="Email"
                        prepend-icon="mdi-email"
                        outlined
                        required
                        :counter="200"
                        :rules="administradorRulesEmail"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.cpf"
                        label="CPF"
                        outlined
                        required
                        :counter="200"
                        :rules="administradorRulesCpf"
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
                            v-model="editedItem.dataNascimento"
                            label="Data de Nascimento"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            outlined
                            required
                            :rules="atividadeAvaliativaRulesdatadataNascimento"
                          ></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="editedItem.dataNascimento"
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
                            @click="$refs.menuEntrada.save(dataNascimento)"
                            >OK</v-btn
                          >
                        </v-date-picker>
                      </v-menu>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.sexo"
                        label="Sexo"
                        outlined
                        required
                        :counter="200"
                        :rules="administradorRulesSexo"
                      ></v-text-field>
                    </v-col>

                    <!--Endereço-->

                    <v-col cols="12" sm="4" md="6">
                      <v-combobox
                        v-model="editedItem.uf"
                        label="UF"
                        outlined
                        :items="lUf"
                        item-text="nome"
                        @change="filtrarCidadesPorEstado"
                        required
                        :rules="administradorRulesUf"

                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="8" md="6">
                      <v-combobox
                        v-model="editedItem.cidade"
                        :items="lCidadeFiltrada"
                        label="Cidade"
                        item-text="nome"
                        @change="filtrarBairroPorCidade"
                        outlined
                        :rules="administradorRulesCidade"
                        required
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="12" md="4">
                      <v-combobox
                        v-model="editedItem.bairro"
                        :items="lBairroFiltrada"
                        label="Bairro"
                        item-text="nome"
                        outlined
                        required
                        :rules="administradorRulesBairro"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="12" md="6">
                      <v-text-field
                        v-model="editedItem.rua"
                        label="Rua"
                        outlined
                        required
                        :counter="100"
                        :rules="administradorRulesRua"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="2">
                      <v-text-field
                        v-model="editedItem.numero"
                        label="Número"
                        outlined
                        required
                        :rules="administradorRulesNumero"
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
      <v-btn color="primary" @click="initialize">Alterar</v-btn>
    </template>
  </v-data-table>
</template>

<script>
import AdministradorService from "../service/domain/AdministradorService";
import { mask } from "@titou10/v-mask";
import BairroService from "../service/domain/BairroService";
import CidadeService from "../service/domain/CidadeService";
import UfService from "../service/domain/UfService";

const serviceUf = UfService.build();
const serviceBairro = BairroService.build();
const serviceCidade = CidadeService.build();

const textos = {
  novo: "Novo Administrador",
  edicao: "Edição de Administrador",
  exclusao: "Deseja mesmo remover este Administrador?",
};

export default {
  directives: { mask },
  data: () => ({
    service: AdministradorService.build(),
    dialog: false,
    dialogExcluir: false,
    valid: true,

    administradorRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 200 && v.length >= 10) ||
        "O campo deve ter pelo menos 10 e no maximo 200 letras",
    ],
    administradorRulesCpf: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 14 && v.length >= 14) ||
        "O campo deve ter 11 digitos",
    ],
    administradorRulesTelefone: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 15 && v.length >= 15) ||
        "O campo deve ter 10 digitos",
    ],
    administradorRulesSenha: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length >= 6) ||
        "O campo deve ter ao menos 6 digitos",
    ],
    administradorRulesEmail: [ 
        (v) => !!v || "Preenchimento Necessário",
        v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail deve ser válido',
      ],

    administradorRulesSexo: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 1 && v.length >= 1) ||
        "O campo deve ter pelo menos 1 e no maximo 1 letras",
    ],
    administradorRulesRua: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 200 && v.length >= 10) ||
        "O campo deve ter pelo menos 8 e no maximo 200 letras",
    ],
    administradorRulesNumero: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 5 && v.length >= 1) ||
        "O campo deve ter pelo menos 1 e no maximo 5 letras",
    ],

    
    administradorRulesUf: [(v) => !!v || "Seleção Necessária"],
    administradorRulesCidade: [(v) => !!v || "Seleção Necessária"],
    administradorRulesBairro: [(v) => !!v || "Seleção Necessária"],
    headers: [
      { text: "ID", value: "id" },
      { text: "Nome", align: "start", value: "nome" },
      { text: "Email", value: "email" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lAdministrador: [],
    lBairro: [],
    lBairroFiltrada: [],
    lUf: [],
    lCidade: [],
    lCidadeFiltrada: [],
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
      this.fetchRecordsCidade();
      this.fetchRecordsBairro();
    },
    fetchRecords() {
      this.service.search({}).then(this.fetchRecodsSuccess);
    },
    fetchRecordsUf() {
      serviceUf.search({}).then(this.fetchRecodsSuccessUf);
    },
    fetchRecordsCidade() {
      serviceCidade.search({}).then(this.fetchRecodsSuccessCidade);
    },
    fetchRecordsBairro() {
      serviceBairro.search({}).then(this.fetchRecodsSuccessBairro);
    },
    fetchRecodsSuccess(response) {
      if (Array.isArray(response.rows)) {
        this.lAdministrador = response.rows;
        return;
      }
      this.lAdministrador = [];
    },
    fetchRecodsSuccessUf(response) {
      if (Array.isArray(response.rows)) {
        this.lUf = response.rows;
        return;
      }
      this.lUf = [];
    },

    fetchRecodsSuccessCidade(response) {
      if (Array.isArray(response.rows)) {
        this.lCidade = response.rows;
        return;
      }
      this.lCidade = [];
    },
    fetchRecodsSuccessBairro(response) {
      if (Array.isArray(response.rows)) {
        this.lBairro = response.rows;
        return;
      }
      this.lBairro = [];
    },
    filtrarCidadesPorEstado() {
      this.resetSelecaoCidade();
      this.lCidadeFiltrada = this.lCidade.filter(
        (cidade) => cidade.uf.id == this.editedItem.uf.id
      );
    },
    filtrarBairroPorCidade() {
      this.resetSelecaoBairro();
      this.lBairroFiltrada = this.lBairro.filter(
        (bairro) => bairro.cidade.id == this.editedItem.cidade.id
      );
    },
    resetSelecaoCidade() {
      this.lCidadeFiltrada = [];
      this.editedItem.cidade = null;
    },
    resetSelecaoBairro() {
      this.lBairroFiltrada = [];
      this.editedItem.bairro = null;
    },
    editItem(item) {
      this.editedIndex = this.lAdministrador.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lAdministrador.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      this.service
        .destroy(this.editedItem)
        .then(this.lAdministrador.splice(this.editedIndex, 1));
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
              this.lAdministrador[this.editedIndex],
              this.editedItem
            )
          );
      } else {
        this.service
          .create(this.editedItem)
          .then((response) => this.lAdministrador.push(response));
      }
      this.close();
    },
  },
};
</script>