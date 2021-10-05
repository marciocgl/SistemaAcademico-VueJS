<template>
  <div>
    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <v-toolbar-title
            ><center>Registro de Presenças</center></v-toolbar-title
          >
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

            <v-text-field
              v-model="numAula"
              hide-details
              outlined
              label="Numero de Aulas"
              type="number"
            />

            <v-text-field
              v-model="dataAula"
              hide-details
              outlined
              type="date"
            />
          </v-row>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-toolbar flat>
        <v-spacer>
          <br />

          <div class="mb-6">
            <div>
              <v-text-field
                v-model="numAulas"
                hide-details
                outlined
                label="Descrição da aula"
              />
            </div>
          </div>
        </v-spacer>
      </v-toolbar>
    </v-container>

    <v-container>
      <v-data-table
        v-model="selected"
        :headers="headers"
        :items="alunos"
        :single-select="singleSelect"
        item-key="aluno"
        show-select
        class="elevation-1"
        
      >
        <template v-slot:top>
          <v-switch
            v-model="singleSelect"
            label="Single select"
            class="pa-3"
          ></v-switch>
        </template>
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
          <v-btn block color="red">Cancelar</v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      singleSelect: false,
      selected: [],
      alunos: [
        {
          aluno: "Ana da Silva",
          matricula: "20181SI",
          faltas: 0,
        },
        {
          aluno: "Beatriz de Souza",
          matricula: "2020SI60",
          faltas: 2,
        },
        {
          aluno: "Carlos Pereira",
          matricula: "2019SI30",
          faltas: 8,
        },
        {
          aluno: "Danilo Silveira",
          matricula: "2017SI30",
          faltas: 4,
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
        { text: "Faltas", value: "faltas" },
      ],
    };
  },
};
</script>