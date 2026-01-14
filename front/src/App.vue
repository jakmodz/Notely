<template>
  <div class="min-h-screen bg-slate-50 dark:bg-slate-900 transition-colors duration-300">
    <nav v-if="isAuthenticated" class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl border-b border-slate-200 dark:border-slate-700 sticky top-0 z-50 transition-colors duration-300">
      
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between h-16">
            <div class="flex items-center gap-4">
                <button 
                    @click="sideBar = !sideBar"
                    class="p-2 rounded-xl text-slate-700 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700 transition-all duration-200"
                    aria-label="Toggle sidebar"
                >
                    <svg 
                    xmlns="http://www.w3.org/2000/svg" 
                    class="h-6 w-6" 
                    fill="none" 
                    viewBox="0 0 24 24" 
                    stroke="currentColor"
                    >
                    <path 
                        stroke-linecap="round" 
                        stroke-linejoin="round" 
                        stroke-width="2" 
                        d="M4 6h16M4 12h16M4 18h16" 
                    />
                    </svg>
                </button>
            
          <div class="flex items-center gap-8 flex-1">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 bg-emerald-600 rounded-xl flex items-center justify-center shadow-lg shadow-emerald-500/20 hover:shadow-emerald-500/40 transition-all duration-300">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                </svg>
              </div>
              <span class="text-xl font-black text-slate-900 dark:text-white tracking-tight">Notely</span>
            </div>
          </div>

            <div class="flex items-center gap-2">
              <router-link 
                to="/" 
                class="px-4 py-2 text-sm font-semibold text-slate-700 dark:text-slate-300 hover:text-emerald-600 dark:hover:text-emerald-400 hover:bg-emerald-50 dark:hover:bg-emerald-900/20 rounded-xl transition-all duration-200"
              >
                Home
              </router-link>
              <router-link 
                to="/notes" 
                class="px-4 py-2 text-sm font-semibold text-slate-700 dark:text-slate-300 hover:text-emerald-600 dark:hover:text-emerald-400 hover:bg-emerald-50 dark:hover:bg-emerald-900/20 rounded-xl transition-all duration-200">
                    Notes
                </router-link>
              <router-link 
                to="/settings" 
                class="px-4 py-2 text-sm font-semibold text-slate-700 dark:text-slate-300 hover:text-emerald-600 dark:hover:text-emerald-400 hover:bg-emerald-50 dark:hover:bg-emerald-900/20 rounded-xl transition-all duration-200"
              >
                Settings
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </nav>
    <Transition
          enter-active-class="transition-opacity duration-300"
          enter-from-class="opacity-0"
          enter-to-class="opacity-100"
          leave-active-class="transition-opacity duration-300"
          leave-from-class="opacity-100"
          leave-to-class="opacity-0"
        >
          <div 
            v-if="isAuthenticated && isSidebarOpen"
            @click="closeSidebar"
            class="fixed inset-0 bg-black/50 z-40 lg:hidden"
          />
        </Transition>
    
        <Transition
          enter-active-class="transition-transform duration-300"
          enter-from-class="-translate-x-full"
          enter-to-class="translate-x-0"
          leave-active-class="transition-transform duration-300"
          leave-from-class="translate-x-0"
          leave-to-class="-translate-x-full"
        >
          <aside 
            v-if="isAuthenticated && sideBar"
            class="fixed top-16 left-0 h-[calc(100vh-4rem)] w-64 bg-white dark:bg-slate-800 border-r border-slate-200 dark:border-slate-700 z-40 shadow-xl overflow-y-auto"
          >
            <div class="p-4 space-y-2">
              <div class="mb-6">
                <h2 class="text-xs font-semibold text-slate-500 dark:text-slate-400 uppercase tracking-wider px-3">
                  Notebooks:
                </h2>
              </div>
            </div>
            <div class="px-4">
                <TreeView 
                    :nodes="config" 
                    @node-click="onNodeClick"
                />
            </div>
              
          </aside>
        </Transition>
    <router-view />
    
  </div>
  <ModalsContainer />
</template>
<script setup>
import { computed, onMounted,ref } from "vue";
import { useAuthStore } from "@/stores/auth.js";
import { useThemeStore } from "@/stores/themes.js";
import { useRouter } from "vue-router";
import { ModalsContainer } from 'vue-final-modal';
import TreeView from "vue3-treeview";
import "vue3-treeview/dist/style.css";
const authStore = useAuthStore();
const themeStore = useThemeStore();
const router = useRouter();
const sideBar = ref(false);

const isAuthenticated = computed(() => authStore.isLoggedIn());
onMounted(async () => {
  themeStore.initTheme();
});

const handleLogout = async () => {
  await authStore.logout();
  router.push('/login');
};
const onNodeClick = (node) => {
    console.log('Node clicked:', node);
};
const config = ref({
  roots: ['1', '5', '15', '19'],
  padding: 20,
  editable: false,
  checkboxes: false,
  dragAndDrop: false,
  nodes: {
    '1': {
      text: 'Applications',
      children: ['2', '3', '4']
    },
    '2': {
      text: 'Calendar'
    },
    '3': {
      text: 'Chrome'
    },
    '4': {
      text: 'Webstorm'
    },
    '5': {
      text: 'Documents',
      children: ['6', '10']
    },
    '6': {
      text: 'vuetify',
      children: ['7']
    },
    '7': {
      text: 'src',
      children: ['8', '9']
    },
    '8': {
      text: 'index.ts'
    },
    '9': {
      text: 'bootstrap.ts'
    },
    '10': {
      text: 'material2',
      children: ['11']
    },
    '11': {
      text: 'src',
      children: ['12', '13', '14']
    },
    '12': {
      text: 'v-btn.ts'
    },
    '13': {
      text: 'v-card.ts'
    },
    '14': {
      text: 'v-window.ts'
    },
    '15': {
      text: 'Downloads',
      children: ['16', '17', '18']
    },
    '16': {
      text: 'October.pdf'
    },
    '17': {
      text: 'November.pdf'
    },
    '18': {
      text: 'Tutorial.html'
    },
    '19': {
      text: 'Videos',
      children: ['20', '24', '25']
    },
    '20': {
      text: 'Tutorials',
      children: ['21', '22', '23']
    },
    '21': {
      text: 'Basic layouts.mp4'
    },
    '22': {
      text: 'Advanced techniques.mp4'
    },
    '23': {
      text: 'All about app'
    },
    '24': {
      text: 'Intro.mov'
    },
    '25': {
      text: 'Conference introduction.avi'
    }
  }
});
</script>
<style>
@import "tailwindcss";
@plugin "@tailwindcss/typography";
@custom-variant dark (&:where(.dark, .dark *));
@theme {
  --color-background-light: #ffffff;
  --color-background-dark: #0f172a;
}
.tree-view {
  color: rgb(71 85 105) !important;
}

.dark .tree-view {
  color: rgb(203 213 225) !important;
}

.tree-view .node-text {
  font-size: 0.875rem;
  font-weight: 500;
}

.tree-view .node-container:hover {
  background-color: rgb(241 245 249);
}

.dark .tree-view .node-container:hover {
  background-color: rgb(51 65 85);
}

.tree-view .node-container {
  border-radius: 0.5rem;
  transition: background-color 0.2s;
}
</style>
