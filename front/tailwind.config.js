/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',

  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
  ],

  theme: {
    extend: {
      colors: {
        background: {
          light: '#ffffff',
          dark: '#0f172a',
        },
        text: {
          light: '#020617',
          dark: '#f8fafc',
        },
        primary: {
          light: '#2563eb',
          dark: '#3b82f6',
        },
      },
    },
  },

  plugins: [],
}
