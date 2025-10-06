import { defineStore } from 'pinia';
import type { PayloadLogin, PayloadSignUp, User } from '@types/auth.types';
import { authHandler } from '@handlers/authHandler';

export const useAuthStore =
defineStore('auth', () => {
    // State
    const user = ref<User | null>(null);
    const token = ref<string | null>(null);
    const loading = ref(false);
    const errorMessage = ref('');

    // Getters
    const isAuthenticated = computed(() => !!token.value);
    const isLoading = computed(() => loading.value);
    const displayName = computed(() : string => (user ? user.value?.fullname : 'User'));

    // Actions
    const login = async (credentials: PayloadLogin) => {
        loading.value = true;
        errorMessage.value = '';

        try {
            const response = await authHandler.login(credentials);

            // Actualizar estado
            token.value = response.token;
            nickname.value = response.nickname;
            fullname.value = response?.fullname || null;

            return { success: true };
        } catch (error: any) {
            errorMessage.value = error.message;
            return { success: false, error: error.message };
        } finally {
            loading.value = false;
        }
    };

    const signup = async (data: PayloadSignUp) => {
        loading.value = true;
        errorMessage.value = '';

        try {
            const response = await authHandler.signup(data);

            // Actualizar estado
            token.value = response.token;
            nickname.value = response.nickname;
            fullname.value = data.name;

            return { success: true };
        } catch (error: any) {
            errorMessage.value = error.message;
            return { success: false, error: error.message };
        } finally {
            loading.value = false;
        }
    };

    const logout = () => {
        user.value = null;
        token.value = null;
        errorMessage.value = '';

        // Limpiar localStorage
        sessionStorage.removeItem('auth_token');
        sessionStorage.removeItem('auth_user');

        // Redirigir al login
        window.location.href = '/login';
    };

    const clearError = () => {
        errorMessage.value = '';
    };

    return {
        // State
        user,
        token,
        loading,
        errorMessage,
        // Getters
        isAuthenticated,
        isLoading,
        displayName,
        // Actions
        login,
        signup,
        logout,
        clearError
    };
});
