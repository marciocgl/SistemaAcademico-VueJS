<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-toolbar-title>Disciplinas Disponíveis</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
    </v-container>
    <v-container>
      <v-data-table
        :headers="headers"
        :items="disciplinasDisponiveis"
        class="elevation-1"
        :hide-default-footer="true"
      >
        <template v-slot:item.marcado="{ item }">
          <v-simple-checkbox v-model="item.marcado"></v-simple-checkbox>
        </template>
      </v-data-table>
    </v-container>
    <v-container>
      <v-toolbar flat>
        <v-toolbar-title>Disciplinas Escolhidas</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
    </v-container>
    <v-container>
      <v-data-table
        :headers="headers"
        :items="disciplinasMarcadas"
        class="elevation-1"
        :hide-default-footer="true"
      >
        <template v-slot:item.marcado="{ item }">
          <v-simple-checkbox v-model="item.marcado"></v-simple-checkbox>
        </template>
      </v-data-table>
    </v-container>
    <v-container>
      <div class="d-flex justify-end mb-6">
        <div>
          <v-btn block color="primary">Salvar</v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      disciplinas: [
        {
          disciplina: "Programação",
          cargaHoraria: 80,
          marcado: false,
        },
        {
          disciplina: "Probabilidade e Estatistica",
          cargaHoraria: 60,
          marcado: false,
        },
      ],
      headers: [
        {
          text: "Disciplina",
          align: "start",
          value: "disciplina",
          width: "50%",
        },
        { text: "Carga horária", value: "cargaHoraria" },
        { text: "Matrícula", value: "marcado" },
      ],
    };
  },
  computed: {
    disciplinasMarcadas() {
      return this.disciplinas.filter((d) => d.marcado);
    },
    disciplinasDisponiveis() {
      return this.disciplinas.filter((d) => !d.marcado);
    },
  },
};
</script>