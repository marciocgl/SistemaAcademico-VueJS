<template>
  <v-data-table
    :headers="headers"
    :items="lAluno"
    sort-by="id"
    class="elevation-1"
    :loading="carregando" 
    loading-text="Aguarde... Carregando"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Cadastro de Aluno</v-toolbar-title>
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
                        :rules="alunoRulesNome"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.rg"
                        label="RG"
                        outlined
                        required
                        :counter="100"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-mask="{ mask: '###.###.###-##' }"
                        v-model="editedItem.cpf"
                        label="CPF"
                        outlined
                        required
                        :rules="alunoRulesCpf"
                      ></v-text-field>
                    </v-col>

                    <!--INICIO CAMPO DE DATA-->
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
                            :rules="modeloRulesData"
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

                    <!--FIM CAMPO DE DATA-->

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.sexo"
                        label="Sexo"
                        outlined
                        required
                        :rules="alunoRulesSexo"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-mask="{ mask: '(##) #####-####' }"
                        v-model="editedItem.telefone"
                        label="Telefone"
                        outlined
                        required
                        :rules="alunoRulesTelefone"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.email"
                        label="Email"
                        outlined
                        required
                        :counter="35"
                        :rules="alunoRulesEmail"
                      ></v-text-field>
                    </v-col>

                    <!--Endereço-->

                    <v-col cols="12" sm="4" md="4">
                      <v-combobox
                        v-model="editedItem.uf"
                        label="UF"
                        outlined
                        :items="lUf"
                        item-text="nome"
                        @change="filtrarCidadesPorEstado"
                        required
                        :rules="alunoRulesUf"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="8" md="4">
                      <v-combobox
                        v-model="editedItem.cidade"
                        :items="lCidadeFiltrada"
                        label="Cidade"
                        item-text="nome"
                        @change="filtrarBairroPorCidade"
                        outlined
                        required
                        :rules="alunoRulesCidade"
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
                        :rules="alunoRulesBairro"
                      ></v-combobox>
                    </v-col>

                    <v-col cols="12" sm="12" md="8">
                      <v-text-field
                        v-model="editedItem.rua"
                        label="Rua"
                        outlined
                        required
                        :counter="100"
                        :rules="alunoRulesRua"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedItem.numero"
                        label="Número"
                        outlined
                        required
                        :rules="alunoRulesNumero"
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
import { mask } from "@titou10/v-mask";
import AlunoService from "../service/domain/AlunoService";
import BairroService from "../service/domain/BairroService";
import CidadeService from "../service/domain/CidadeService";
import UfService from "../service/domain/UfService";

const serviceUf = UfService.build();
const serviceBairro = BairroService.build();
const serviceCidade = CidadeService.build();

const textos = {
  novo: "Novo Aluno",
  edicao: "Edição de Aluno",
  exclusao: "Deseja mesmo remover este Aluno?",
};

export default {
  directives: { mask },
  data: () => ({
    service: AlunoService.build(),
    dialog: false,
    dialogExcluir: false,
    valid: true,

    alunoRulesNome: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 200 && v.length >= 10) ||
        "O campo deve ter pelo menos 10 e no maximo 200 letras",
    ],
    alunoRulesCpf: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 14 && v.length >= 14) ||
        "O campo deve ter 11 digitos",
    ],
    alunoRulesTelefone: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 15 && v.length >= 15) ||
        "O campo deve ter 15 digitos",
    ],
    alunoRulesEmail: [ 
        (v) => !!v || "Preenchimento Necessário",
        v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail deve ser válido',
    ],

    alunoRulesSexo: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 1 && v.length >= 1) ||
        "O campo deve ter pelo menos 1 e no maximo 1 letras",
    ],
    alunoRulesRua: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 200 && v.length >= 10) ||
        "O campo deve ter pelo menos 8 e no maximo 200 letras",
    ],
    alunoRulesNumero: [
      (v) => !!v || "Preenchimento Necessário",
      (v) =>
        (v && v.length <= 5 && v.length >= 1) ||
        "O campo deve ter pelo menos 1 e no maximo 5 letras",
    ],
    alunoRulesUf: [(v) => !!v || "Seleção Necessária"],
    alunoRulesCidade: [(v) => !!v || "Seleção Necessária"],
    alunoRulesBairro: [(v) => !!v || "Seleção Necessária"],
    headers: [
      { text: "ID", value: "id" },
      { text: "Nome", align: "start", value: "nome" },
      { text: "Matrícula", value: "matricula" },
      { text: "CPF", value: "cpf" },
      { text: "Email", value: "email" },
      { text: "Telefone", value: "telefone" },
      { text: "Ações", align: "end", value: "actions", sortable: false },
    ],
    lAluno: [],
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
        this.lAluno = response.rows;
        return;
      }
      this.lAluno = [];
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
      this.editedIndex = this.lAluno.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.lAluno.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogExcluir = true;
    },
    deleteItemComfirm() {
      this.service
        .destroy(this.editedItem)
        .then(this.lAluno.splice(this.editedIndex, 1));
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
          .then(Object.assign(this.lAluno[this.editedIndex], this.editedItem));
      } else {
        this.service
          .create(this.editedItem)
          .then((response) => this.lAluno.push(response));
      }
      this.close();
    },
  },
};
</script>