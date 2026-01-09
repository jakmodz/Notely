†/** @type {import('tailwindcss').Config} */
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
      typography: (theme) => ({
        DEFAULT: {
          css: {
            color: theme('colors.red'),
            a: {
              color: theme('colors.emerald.600'),
              '&:hover': {
                color: theme('colors.emerald.700'),
              },
            },
            strong: {
              color: theme('colors.slate.900'),
            },
            code: {
              color: theme('colors.emerald.600'),
            },
            h1: {
              color: theme('colors.slate.900'),
            },
            h2: {
              color: theme('colors.slate.900'),
            },
            h3: {
              color: theme('colors.slate.900'),
            },
            h4: {
              color: theme('colors.slate.900'),
            },
            ul: {
              listStyleType: 'disc',
              paddingLeft: '1.5em',
            },
            ol: {
              listStyleType: 'decimal',
              paddingLeft: '1.5em',
            },
            'ul > li': {
              paddingLeft: '0.375em',
            },
            'ol > li': {
              paddingLeft: '0.375em',
            },
            'ul > li::marker': {
              color: theme('colors.emerald.600'),
            },
            'ol > li::marker': {
              color: theme('colors.emerald.600'),
            },
          },
        },
        invert: {
          css: {
            color: theme('colors.slate.50'),
            a: {
              color: theme('colors.emerald.400'),
              '&:hover': {
                color: theme('colors.emerald.300'),
              },
            },
            strong: {
              color: theme('colors.white'),
            },
            code: {
              color: theme('colors.emerald.400'),
            },
            h1: {
              color: theme('colors.white'),
            },
            h2: {
              color: theme('colors.white'),
            },
            h3: {
              color: theme('colors.white'),
            },
            h4: {
              color: theme('colors.white'),
            },
            blockquote: {
              color: theme('colors.slate.400'),
              borderLeftColor: theme('colors.emerald.500'),
            },
            'ul > li::marker': {
              color: theme('colors.emerald.400'),
            },
            'ol > li::marker': {
              color: theme('colors.emerald.400'),
            },
          },
        },
      }),
    },
  },

  plugins: [
    require('@tailwindcss/typography'),
  ],
}
