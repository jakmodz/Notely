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
  }
}