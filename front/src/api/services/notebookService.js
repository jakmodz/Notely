import apiClient from "../axios";

export default {
  getAllNotebooksWithNotes() {
    return apiClient.get('/notebooks');
  },
  createNotebook(notebookData) {
    return apiClient.post('/notebooks/create', notebookData);
  },
  deleteNotebook(uuid) {
    return apiClient.delete(`/notebooks/${uuid}`);
  }
}
