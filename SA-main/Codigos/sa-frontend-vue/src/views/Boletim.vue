<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-toolbar-title>Boletim</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-combobox item-text="periodo" label="Período" clearable></v-combobox>
      </v-toolbar>
    </v-container>
    <v-container>
      <v-data-table
        :headers="headers"
        :items="disciplinas"
        class="elevation-1"
        :hide-default-footer="true"
        :loading="carregando"
        loading-text="Aguarde... Carregando"
      >
      </v-data-table>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      disciplinas: [
        {
          disciplina: "Programação I",
          cargaHoraria: 80,
          nota: 0,
          faltas: 4,
          status: "Cursando",
        },
        {
          disciplina: "Cálculo II",
          cargaHoraria: 60,
          nota: 0,
          faltas: 0,
          status: "Cursando",
        },
        {
          disciplina: "Administração",
          cargaHoraria: 30,
          nota: 90,
          faltas: 6,
          status: "Aprovado",
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
        { text: "Nota", value: "nota" },
        { text: "Faltas", value: "faltas" },
        { text: "Status", value: "status" },
      ],
    };
  },
};
</script>