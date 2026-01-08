import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/auth.js";

const LoginView = () => import("@/views/LoginView.vue");
const RegisterView = () => import("@/views/RegisterView.vue");
const HomePage = () => import("@/views/HomePage.vue");
const SettingsPage = () => import("@/views/SettingsPage.vue");
const NotFound = () => import("@/views/NotFound.vue");
const NoteDetails = () => import("@/views/NotesDetailsView.vue");
const NotesView = () => import("@/views/NotesView.vue");
const NoteEditView = () => import("@/views/NoteEditView.vue");
const CreateNoteView = () => import("@/views/CreateNoteView.vue");

const publicRoutes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: { requiresGuest: true }
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView,
    meta: { requiresGuest: true }
  }
];

const protectedRoutes = [
  {
    path: '/',
    name: 'Home',
    component: HomePage,
    meta: { requiresAuth: true }
  },
  {
    path: '/settings',
    name: 'Settings',
    component: SettingsPage,
    meta: { requiresAuth: true }
  },
  {
    path: "/notes",
    children: [
      {path: "new", name: "CreateNote", component: CreateNoteView, meta: { requiresAuth: true }},
      {path: ":id/edit", name: "NoteEdit", component: NoteEditView, meta: { requiresAuth: true }},
      {path: ":id", name: "NoteDetails", component: NoteDetails, meta: { requiresAuth: true }},
      {path: "", component: NotesView, meta: { requiresAuth: true }}
    ]
  }
];
const NotFoundRoute = {
  path: '/:pathMatch(.*)*',
  name: 'NotFound',
  component: NotFound
}
const routes = [
  ...publicRoutes,
  ...protectedRoutes,
  NotFoundRoute
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore();
  const isAuthenticated = authStore.isLoggedIn();
  
  if (to.name === 'NotFound') {
     return next();
   }
  
  if (to.meta.requiresGuest && isAuthenticated) {
    return next({ name: 'Home' });
  }

  if (to.meta.requiresAuth && !isAuthenticated) {
    return next({ name: 'Login' });
  }
  
  next();
});

export default router;
