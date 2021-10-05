<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <v-toolbar-title><center>Registro de Notas</center></v-toolbar-title>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-toolbar>
        <v-spacer>
          <br />
          <v-row>
            <v-combobox
              item-text="disciplina"
              label="Disciplina"
              clearable
              outlined
              required
            >
            </v-combobox>

            <v-combobox
              item-text="avaliacao"
              label="Avaliação"
              clearable
              outlined
              required
            >
            </v-combobox>
          </v-row>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <br />
          <div class="d-flex justify-center mb-6">
            <div>
              <v-btn block color="primary">Listar Alunos</v-btn>
            </div>
          </div>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-data-table
        :headers="headers"
        :items="alunos"
        class="elevation-1"
        :hide-default-footer="true"
      >
      </v-data-table>
    </v-container>

    <v-container>
      <div class="d-flex justify-center mb-6">
        <div class="mr-2">
          <v-btn block color="success">Salvar</v-btn>
        </div>
        <div class="mr-2">
          <v-btn block color="primary">Limpar</v-btn>
        </div>
        <div class="mr-2">
          <v-btn block color="gray">Cancelar</v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>


<script>
export default {
  data() {
    return {
      alunos: [
        {
          aluno: "Marcio",
          matricula: "20181SI",
          nota: 10,
        },
        {
          aluno: "Joãozinho",
          matricula: "2020SI60",
          nota: 60,
        },
        {
          aluno: "Juquinha",
          matricula: "2019SI30",
          nota: 90,
        },
      ],

      headers: [
        {
          text: "Aluno",
          align: "start",
          value: "aluno",
          width: "45%",
        },
        { text: "Matrícula", value: "matricula" },
        { text: "Nota", value: "nota" },
      ],
    };
  },
};
</script>