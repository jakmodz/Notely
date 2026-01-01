import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/api/auth.js";

const LoginView = () => import("../views/LoginView.vue");
const RegisterView = () => import("../views/RegisterView.vue");
const HomePage = () => import("../views/HomePage.vue");

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
    path: '/home',
    name: 'Home',
    component: HomePage,
    meta: { requiresAuth: true }
  }
];

const routes = [
  {
    path: '/',
    redirect: { name: 'Login' }
  },
  ...publicRoutes,
  ...protectedRoutes
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  const isAuthenticated = authStore.isLoggedIn();

  if (to.meta.requiresGuest && isAuthenticated) {
    return next({ name: 'Home' });
  }

  if (to.meta.requiresAuth && !isAuthenticated) {
    return next({ name: 'Login' });
  }
  next();
});

export default router;