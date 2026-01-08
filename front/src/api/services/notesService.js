import apiClient from "../axios";

export default{
  getNote(uuid){
    return apiClient.get(`/notes/${uuid}`);
  },
  getAllNotes(){
    return apiClient.get('/notes');
  },
  deleteNote(uuid){
    return apiClient.delete(`/notes/${uuid}`);
  },
  updateNote(uuid, noteData){
    return apiClient.put(`/notes/${uuid}`, noteData);
  }
}
