<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <v-toolbar-title><center>Listagem de Faltas</center></v-toolbar-title>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-toolbar>
        <v-spacer>
          <br />
          <v-row>
            <v-combobox
              item-text="turma"
              label="Turma"
              clearable
              outlined
              required
            >
            </v-combobox>

            <v-combobox
              item-text="status"
              clearable
              outlined
              label="Status"
              required
            >
            </v-combobox>
          </v-row>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-toolbar>
        <v-spacer>
          <br />
          <v-row>
            <div class="mr-2 mb-6">
              <v-text-field
                v-model="dataInicioAula"
                hide-details
                outlined
                label="Data inicio"
                type="date"
              />
            </div>
            <div class="mr-2 mb-6">
              <v-text-field
                v-model="dataFimAula"
                hide-details
                outlined
                label="Data fim"
                type="date"
              />
            </div>
          </v-row>
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      alunos: [
        {
          matricula: "20161si001",
          aluno: "Ana da Silva",
          turma: "Programação I - 2021.1",
          intervalo: "01/01/2021 - 01/06/2021",
          faltas: 10,
          status: "Pendente",
        },

        {
          matricula: "20161si002",
          aluno: "Beatriz de Souza",
          turma: "Programação I - 2021.1",
          intervalo: "01/01/2021 - 01/06/2021",
          faltas: 8,
          status: "Pendente",
        },

        {
          matricula: "20161si003",
          aluno: "Carlos Pereira",
          turma: "Programação I - 2021.1",
          intervalo: "01/01/2021 - 01/06/2021",
          faltas: 2,
          status: "Pendente",
        },
        {
          matricula: "20161si004",
          aluno: "Danilo Silveira",
          turma: "Programação I - 2021.1",
          intervalo: "01/01/2021 - 01/06/2021",
          faltas: 4,
          status: "Pendente",
        },
      ],
      headers: [
        {
          text: "Matricula",
          align: "start",
          value: "matricula",
          width: "25%",
        },
        { text: "Alunos", value: "aluno" },
        { text: "Turma", value: "turma" },
        { text: "Intervalo", value: "intervalo" },
        { text: "Faltas", value: "faltas" },
        { text: "Status", value: "status" },
      ],
    };
  },
};
</script>