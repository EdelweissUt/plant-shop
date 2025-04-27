/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/**/*.{js,ts,jsx,tsx}",
        "!./src/pages/**/*",
        "./node_modules/tailwind-datepicker-react/dist/**/*.js",
    ],
    corePlugins: {
        preflight: false,
    },
    darkMode: 'class',
    theme: {
        extend: {
            colors: {
                primary: '#ff5252',
            },
            keyframes: {
                fadeInUp: {
                    '0%': { opacity: 0, transform: 'translateY(20px)' },
                    '100%': { opacity: 1, transform: 'translateY(0)' },
                },
            },
            animation: {
                fadeInUp: 'fadeInUp 0.6s ease-out forwards',
            },
        },
    },
    plugins: [],
};
