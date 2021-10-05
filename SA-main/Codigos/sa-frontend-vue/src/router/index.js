import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue')
  },
  {
    path: '/administrador',
    name: 'Administrador',
    component: () => import(/* webpackChunkName: "administrador" */ '../views/Administrador.vue')
  },
  {
    path: '/aluno',
    name: 'Aluno',
    component: () => import(/* webpackChunkName: "aluno" */ '../views/Aluno.vue')
  },
  {
    path: '/professor',
    name: 'Professor',
    component: () => import(/* webpackChunkName: "professor" */ '../views/Professor.vue')
  },
  {
    path: '/curso',
    name: 'Curso',
    component: () => import(/* webpackChunkName: "curso" */ '../views/Curso.vue')
  },
  {
    path: '/periodoLetivo',
    name: 'PeriodoLetivo',
    component: () => import(/* webpackChunkName: "periodoLetivo" */ '../views/PeriodoLetivo.vue')
  },
  {
    path: '/turma',
    name: 'Turma',
    component: () => import(/* webpackChunkName: "turma" */ '../views/Turma.vue')
  },
  {
    path: '/atividadeAvaliativa',
    name: 'AtividadeAvaliativa',
    component: () => import(/* webpackChunkName: "atividadeAvaliativa" */ '../views/AtividadeAvaliativa.vue')
  },
  {
    path: '/disciplina',
    name: 'Disciplina',
    component: () => import(/* webpackChunkName: "disciplina" */ '../views/Disciplina.vue')
  },
  {
    path: '/cidade',
    name: 'Cidade',
    component: () => import(/* webpackChunkName: "cidade" */ '../views/Cidade.vue')
  },
  {
    path: '/uf',
    name: 'UF',
    component: () => import(/* webpackChunkName: "uf" */ '../views/Uf.vue')
  },
  {
    path: '/bairro',
    name: 'Bairro',
    component: () => import(/* webpackChunkName: "bairro" */ '../views/Bairro.vue')
  },
  {
    path: '/registroDeNotas',
    name: 'Registro de Notas',
    component: () => import(/* webpackChunkName: "registroDeNotas" */ '../views/RegistroDeNotas.vue')
  },
  {
    path: '/matriculaDisciplina',
    name: 'Matricula em Disciplinas',
    component: () => import(/* webpackChunkName: "matriculaDisciplina" */ '../views/MatriculaDisciplina.vue')
  },
  {
    path: '/registroDePresenca',
    name: 'Registro de presenca',
    component: () => import(/* webpackChunkName: "registroDePresenca" */ '../views/RegistroDePresenca.vue')
  },
  {
    path: '/boletim',
    name: 'Boletim',
    component: () => import(/* webpackChunkName: "boletin" */ '../views/Boletim.vue')
  },
  {
    path: '/registroAprovacao',
    name: 'Registro de Aprovação',
    component: () => import(/* webpackChunkName: "registroAprovacao" */ '../views/RegistroAprovacao.vue')
  },
  {
    path: '/listagemAprovacoes',
    name: 'Listagem de Registro de Aprovação',
    component: () => import(/* webpackChunkName: "listagemAprovacoes" */ '../views/ListagemAprovacoes.vue')
  },
  {
    path: '/listagemNotas',
    name: 'ListagemNotas',
    component: () => import(/* webpackChunkName: "boletin" */ '../views/ListagemNotas.vue')
  },
  {
    path: '/listagemFaltas',
    name: 'Listagem de Faltas',
    component: () => import(/* webpackChunkName: "listagemDeFaltas" */ '../views/ListagemDeFaltas.vue')
  },

]

const router = new VueRouter({
  routes
})

export default router
