import axios from "axios";
import { useAuthStore } from "../stores/auth";
import router from "@/router/index";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/",
  headers: {
    "Content-Type": "application/json",
    "Accept": "application/json",
  },
  withCredentials: true,
  
});
apiClient.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore();
    if (authStore.accessToken) {
      config.headers.Authorization = `Bearer ${authStore.accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    const authStore = useAuthStore();
    if (originalRequest.url?.includes('/auth/login') || 
            originalRequest.url?.includes('/auth/register')) {
          return Promise.reject(error);
    }
    if(error.response?.status === 401 && !originalRequest._retry){
      originalRequest._retry = true;
      try{
        const response = await apiClient.post('/auth/refresh');
        const newAccessToken = response.data.accessToken;
        authStore.setAccessToken(newAccessToken);
        originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
        return apiClient(originalRequest);
      }catch (err){
        authStore.logout();
        return Promise.reject(err);
      }
    }
    return Promise.reject(error);
  }
)
export default apiClient;