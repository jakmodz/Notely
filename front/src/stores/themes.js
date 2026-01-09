import { defineStore } from 'pinia';
import { ref, watch } from 'vue';

export const useThemeStore = defineStore('theme', () => {
  const theme = ref(localStorage.getItem("theme") || "light");
  
  const initTheme = () => {
    applyTheme(theme.value);
  };
  
  const applyTheme = (newTheme) => { 
    const html = document.documentElement;
    
    if (newTheme === "dark") {
      html.classList.add("dark");
    } else {
      html.classList.remove("dark");
    }
    localStorage.setItem("theme", newTheme);
    void html.offsetHeight;
  };
  
  const setTheme = (newTheme) => { 
    theme.value = newTheme;
    applyTheme(newTheme);
  };
  
  const toggleTheme = () => { 
    const newTheme = theme.value === "light" ? "dark" : "light";
    setTheme(newTheme);
  };
  
  watch(theme, (newTheme) => {
    applyTheme(newTheme);
  });
  
  initTheme();
  
  return { theme, setTheme, initTheme, toggleTheme };
});
