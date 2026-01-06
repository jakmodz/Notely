import {defineStore} from 'pinia';
import { ref } from 'vue';
import authService from '../api/services/authService';

export const useAuthStore = defineStore('auth',() =>{
  const accessToken = ref(localStorage.getItem('accessToken') || null);
  const user = ref(JSON.parse(localStorage.getItem('user')) || null);
  const isValidating = ref(false);
  
  const isLoggedIn = ()=> {
    return !!accessToken.value;    
  }
  
  const setUser = (userData)=>{
    user.value = userData;
    if (userData) {
      localStorage.setItem('user', JSON.stringify(userData));
    } else {
      localStorage.removeItem('user');
    }
  }
  
  const setAccessToken = (token)=>{
    accessToken.value = token;
    if (token) {
      localStorage.setItem('accessToken', token);
    } else {
      localStorage.removeItem('accessToken');
    }
  }

  const logout = async () => {
     try {
       await authService.logout();
     } catch (error) {
       console.error('Error during logout:', error);
     } finally { 
       accessToken.value = null;
       setUser(null);
       localStorage.removeItem('accessToken');
     }
   }
  
  const getAuthHeaders = () => {
    return {
      'Authorization': `Bearer ${accessToken.value}`
    }
  }
  return {
    accessToken,
    user,
    isValidating,
    setAccessToken,
    setUser,
    logout,
    isLoggedIn,
    getAuthHeaders,
  }
  
})
