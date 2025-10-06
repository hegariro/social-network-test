// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    compatibilityDate: '2025-07-15',
    devtools: { enabled: true },
    srcDir: 'app/',
    modules: [
        '@nuxt/fonts',
        '@nuxt/icon',
        '@nuxt/ui',
        '@pinia/nuxt'
    ],
    //css: ['~/app/assets/css/main.css'],
    tailwindcss: {
        exposeConfig: true,
        viewer: true,
    },
    alias: {
        '@slides': '~/components/slides',
        '@layouts': '~/layouts',
        '@utils': '~/utils',
        '@components': '~/components',
        '@stores': '~/stores',
        '@types': '~/types',
        '@handlers': '~/utils/http/handlers',
    },
});