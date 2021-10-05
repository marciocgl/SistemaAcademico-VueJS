<template>
  <v-container>
    <v-row class="text-center">
      <v-col cols="12">
        <v-img
          :src="require('../assets/logo.png')"
          class="my-3"
          contain
          height="200"
        />
      </v-col>

      <v-col class="mb-4">
        <h1 class="display-2 font-weight-bold mb-3">
          BEM VINDO AO SISTEMA ACADÊMICO !
        </h1>

        <p class="subheading font-weight-regular">
          <tr><td>
          Foi proposto o desenvolvimento de um Sistema Acadêmico, que vai
          informatizar as funções de matricula em disciplina, registro no notas,
          faltas e também o processo de aprovação dos alunos. O objetivo do
          sistema é agilizar o processos e garantir maior segurança, ao mesmo
          tempo possibilitar um melhor controle das informações por parte da
          diretoria. Deverão ser gerados relatórios de relacionados aos
          cadastros básicos, bem como aos eventos de notas, faltas, matricula e
          aprovação dos alunos.
        </td></tr>
      </v-col>


      <v-col class="mb-5" cols="12">
        <h2 class="headline font-weight-bold mb-3">Important Links</h2>
        <v-row justify="center">
          <a
            v-for="(link, i) in importantLinks"
            :key="i"
            :href="link.href"
            class="subheading mx-3"
            target="_blank"
          >
            {{ link.text }}
          </a>
        </v-row>
      </v-col>
      <v-col class="mb-5" cols="12"> </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "HelloWorld",

  data: () => ({
    importantLinks: [
      {
        text: "Código Fonte do Projeto",
        href: "https://github.com/LeandroFerreira1/SA",
      },
    ],
  }),
};
</script>
