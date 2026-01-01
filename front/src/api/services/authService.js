import apiClient from "../axios";

export default {
  login(credentials){
    
    return apiClient.post('/auth/login',credentials);
  },
  logout(){
   return apiClient.post('/auth/logout');
  },
  register(credentials){
    return apiClient.post('/auth/register',credentials);
  }
}