import apiClient from "../axios";

export default{
  getNote(uuid){
    return apiClient.get(`/notes/${uuid}`);
  },
  getAllNotes(page = 0, size = 12, sortBy = 'created', sortDirection = 'DESC'){
    return apiClient.get('/notes', {
      params: { page, size, sortBy, sortDirection }
    });
  },
  deleteNote(uuid){
    return apiClient.delete(`/notes/${uuid}`);
  },
  updateNote(uuid, noteData){
    return apiClient.put(`/notes/${uuid}`, noteData);
  },
  createNote(noteData){
      return apiClient.post('/notes/create', noteData);
  }
  
}
