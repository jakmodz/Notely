import { defineStore } from 'pinia';
import notebooksService from '@/api/services/notebookService.js';
import errorHandler from '@/util/apiError.js';

export const useNotebooksStore = defineStore('notebooks', {
  state: () => ({
    treeData: [],
    loading: false,
    error: null,
  }),

  actions: {
    async loadNotebooksAndNotes() {
      this.loading = true;
      this.error = null;
      
      try {
        const response = await notebooksService.getAllNotebooksWithNotes();
        this.treeData = response.data;
      } catch (error) {
        this.error = errorHandler.handleError(error);
        console.error('Error loading notebooks:', error);
      } finally {
        this.loading = false;
      }
    },

    async reloadTree() {
      await this.loadNotebooksAndNotes();
    },

    clearTree() {
      this.treeData = [];
      this.error = null;
    }
  },

  getters: {
    hasNotebooks: (state) => state.treeData.length > 0,
    isLoading: (state) => state.loading,
    getError: (state) => state.error,
  }
});