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
              class="fixed top-16 left-0 h-[calc(100vh-4rem)] w-100 bg-white dark:bg-slate-800 border-r border-slate-200 dark:border-slate-700 z-40 shadow-xl overflow-y-auto"
            >
            <div class="p-4 space-y-2">
              <div class="mb-6">
                 <div class="flex items-center justify-between px-3 mb-4">
                  <h2 class="text-xs font-semibold text-slate-500 dark:text-slate-400 uppercase tracking-wider">
                    Notebooks
                  </h2>
                  <button 
                    @click.stop="onAddClick(data)" 
                    class="p-1.5 rounded-lg hover:bg-emerald-50 dark:hover:bg-emerald-900/20 transition-all duration-200 group"
                    title="Add new notebook"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-400 dark:text-slate-500 group-hover:text-emerald-600 dark:group-hover:text-emerald-400 transition-colors duration-200" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                  </button>
                 </div>
                
                <div class="px-4">
                    <el-tree
                        ref="treeRef"
                        :data="notebooksStore.treeData"
                        :props="treeProps"
                        class="custom-tree"
                        :default-expand-all="false"
                        node-key="id"
                        @node-click="handleNodeClick"
                    >
                    <template #default="{ node, data }">
                        <span 
                          class="custom-tree-node flex items-center gap-2"
                          @dblclick="handleNodeDoubleClick(node, data)"
                        >
                            <svg v-if="isNotebook(data)" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-emerald-600 dark:text-emerald-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z" />
                            </svg>
                            <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-slate-400 dark:text-slate-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                            </svg>
                            <span class="node-label">{{ node.label }}
                            </span>
                        </span>
                        <div v-if="isNotebook(data)">
                            <button 
                              @click.stop="onAddClick(data)" 
                              class="p-1 rounded-lg hover:bg-emerald-50 dark:hover:bg-emerald-900/20 transition-all duration-200 group opacity-0 group-hover:opacity-100"
                              title="Add note to notebook"
                            >
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-slate-400 dark:text-slate-500 group-hover:text-emerald-600 dark:group-hover:text-emerald-400 transition-colors duration-200" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
                                </svg>
                            </button>
                        </div>
                    </template>
                    </el-tree>
                </div>
              </div>
             
            </div>
            
            
          </aside>
        </Transition>
    <router-view />
  </div>
  <ModalsContainer />
</template>
<script setup>
import { computed, onMounted, ref } from "vue";
import { useAuthStore } from "@/stores/auth.js";
import { useThemeStore } from "@/stores/themes.js";
import { useRouter } from "vue-router";
import { ModalsContainer, useModal } from 'vue-final-modal';
import CreateItemModal from '@/components/CreateItemModal.vue';
import notebooksService from '@/api/services/notebookService.js';
import errorHandler from '@/util/apiError.js';
import { useNotebooksStore } from "@/stores/notebooks.js";
const authStore = useAuthStore();
const themeStore = useThemeStore();
const notebooksStore = useNotebooksStore();

const router = useRouter();
const sideBar = ref(false);
const treeRef = ref(null);
const treeData = ref([]);

const treeProps = {
  children: 'children',
  label: 'label',
};
const isAuthenticated = computed(() => authStore.isLoggedIn());
onMounted(async () => {
  themeStore.initTheme();
  if (isAuthenticated.value) {
    await notebooksStore.loadNotebooksAndNotes();
  }
});


const isNotebook = (element) => { 
  return element.type === 'notebook';
}

const handleLogout = async () => {
  await authStore.logout();
  router.push('/login');
};

const handleNodeClick = (data, node, element) => {
  if (data.type == 'note') {
    sideBar.value = false;
    router.push(`/notes/${data.id}`);
  }
};

const handleNodeDoubleClick = (node, data) => {
  if (data.children && data.children.length > 0) {
    if (node.expanded) {
      treeRef.value.store.nodesMap[data.id].collapse();
    } else {
      treeRef.value.store.nodesMap[data.id].expand();
    }
  }
};

const onAddClick = (data) => {
  const { open, close } = useModal({
    component: CreateItemModal,
    attrs: {
      parentData: data,
      onClose() {
        close();
      },
      onSuccess(itemData) {
        if (itemData.type === "note") {
          sideBar.value = false;
        } 
        notebooksStore.reloadTree();
        close();
      }
    }
  });
  
  open();
};
</script>
<style>
@import "tailwindcss";
@plugin "@tailwindcss/typography";
@custom-variant dark (&:where(.dark, .dark *));
@theme {
  --color-background-light: #ffffff;
  --color-background-dark: #0f172a;
}
.custom-tree {
  background: transparent !important;
  color: rgb(71 85 105);
}

.dark .custom-tree {
  color: rgb(203 213 225);
}

.custom-tree .el-tree-node__content {
  background-color: transparent !important;
  border-radius: 0.5rem;
  padding: 8px 4px;
  margin-bottom: 2px;
  transition: all 0.2s ease;
}

.custom-tree .el-tree-node__content:hover {
  background-color: rgb(241 245 249) !important;
}

.dark .custom-tree .el-tree-node__content:hover {
  background-color: rgb(51 65 85) !important;
}

/* Show add button on hover */
.custom-tree .el-tree-node__content:hover button {
  opacity: 1 !important;
}

.custom-tree .el-tree-node__expand-icon {
  display: inline-flex !important;
  align-items: center;
  justify-content: center;
  width: 20px !important;
  height: 20px !important;
  margin-right: 6px !important;
  color: rgb(100 116 139) !important;
  transition: transform 0.2s ease, color 0.2s ease;
  font-size: 14px !important;
  cursor: pointer;
}

.dark .custom-tree .el-tree-node__expand-icon {
  color: rgb(148 163 184) !important;
}

.custom-tree .el-tree-node__expand-icon:hover {
  color: rgb(15 118 110) !important;
}

.dark .custom-tree .el-tree-node__expand-icon:hover {
  color: rgb(52 211 153) !important;
}

.custom-tree .el-tree-node__expand-icon.expanded {
  transform: rotate(90deg);
}

.custom-tree .el-tree-node__expand-icon.is-leaf {
  visibility: hidden;
  width: 15px !important;
}

.custom-tree .el-tree-node__children {
  padding-left: 20px !important;
}

.custom-tree .node-label {
  font-size: 0.8rem;
  font-weight: 700;
  color: rgb(71 85 105);
  transition: color 0.2s ease;
  cursor: pointer;
  user-select: none;
}

.dark .custom-tree .node-label {
  color: rgb(203 213 225);
}

.custom-tree .el-tree-node.is-current > .el-tree-node__content {
  background-color: rgb(236 254 255) !important;
  color: rgb(15 118 110);
}

.dark .custom-tree .el-tree-node.is-current > .el-tree-node__content {
  background-color: rgb(6 78 59) !important;
  color: rgb(110 231 183);
}

.custom-tree .el-tree-node.is-current > .el-tree-node__content .node-label {
  color: rgb(15 118 110);
  font-weight: 600;
}

.dark .custom-tree .el-tree-node.is-current > .el-tree-node__content .node-label {
  color: rgb(110 231 183);
  font-weight: 600;
}

.custom-tree .el-tree-node:focus > .el-tree-node__content {
  outline: none;
}

.custom-tree .el-tree-node {
  white-space: nowrap;
}

</style>