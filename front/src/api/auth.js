import {defineStore} from 'pinia';
import { ref } from 'vue';
import axios from 'axios';
import authService from './services/authService';
const API_URL = 'http://localhost:8080';

export const useAuthStore = defineStore('auth',() =>{
  const accessToken = ref(localStorage.getItem('accessToken') || null);
  const user = ref(null);
  const isValidating = ref(false);
  
  const isLoggedIn = ()=> {
    return !!accessToken.value;    
  }
  
  const validateToken = async () => {
    if (!accessToken.value) return false;
    
    try {
      isValidating.value = true;
      const response = await axios.get(`${API_URL}/api/auth/validate`, {
        headers: {
          'Authorization': `Bearer ${accessToken.value}`
        }
      });
      
      if (response.data.user) {
        user.value = response.data.user;
      }
      return true;
    } catch (error) {
      logout();
      return false;
    } finally {
      isValidating.value = false;
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
       user.value = null;
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
    logout,
    isLoggedIn,
    validateToken,
    getAuthHeaders,
  }
  
})
