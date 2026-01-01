import {defineStore} from 'pinia';
import { ref } from 'vue';

export const useAuthStore = defineStore('auth',() =>{
  const accessToken = ref(localStorage.getItem('accessToken') || null);
  const user = ref(null);
  
  const isLoggedIn = ()=> {
    return !!accessToken.value;
  }
  
  const setAccessToken = (token)=>{
    accessToken.value = token;
    if (token) {
      localStorage.setItem('accessToken', token);
    } else {
      localStorage.removeItem('accessToken');
    }
  }

  const logout = ()=>{
    accessToken.value = null;
    user.value = null;
    localStorage.removeItem('accessToken');
  }
  
  return {
    accessToken,
    setAccessToken,
    logout,
    isLoggedIn,
  }
  
})
