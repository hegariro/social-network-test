import { httpClient } from '@/utils/http/httpClient';
import type { PayloadLogin, PayloadSignUp, AuthResponse } from '@/types/auth';

class AuthHandler {
    private readonly BASE_PATH = '/api/v1/auth';

    async login(payload: PayloadLogin): Promise<AuthResponse> {
        try {
            const response = await httpClient.post<AuthResponse>(
                `${this.BASE_PATH}/login`,
                payload
            );

            // Guardar en localStorage si rememberMe es true
            if (credentials.rememberMe) {
                this.saveSession(response, null);
            }

            return response;
        } catch (error: any) {
            throw this.handleLoginError(error)
        }
    }

    async signup(payload: PayloadSignUp): Promise<AuthResponse> {
        try {
            const response = await httpClient.post<AuthResponse>(
                `${this.BASE_PATH}/registry`,
                payload
            );

            this.saveSession(response, data.fullname);
            return response;
        } catch (error: any) {
            throw this.handleSignupError(error);
        }
    }

    async logout(): Promise<void> {
        try {
            await httpClient.post(`${this.BASE_PATH}/logout`)
            this.clearSession()
        } catch (error) {
            // Limpiar sesión incluso si falla la petición
            this.clearSession()
        }
    }

    // Métodos privados para manejo de errores específicos
    private handleLoginError(error: any): Error {
        const status = error.response?.status
        const message = error.response?.data?.message

        if (status === 401) {
            return new Error('Invalid credentials. Please check your email and password.')
        }

        if (status === 403) {
            return new Error('Your account has been disabled. Please contact support.')
        }

        if (status === 422) {
            return new Error('the data submitted is syntactically correct, but it\'s' +
                'semantically invalid or incomplete according to the server\'s rules or business logic.');
        }

        if (status === 429) {
            return new Error('Too many login attempts. Please try again later.');
        }

        return new Error(message || 'Login failed. Please try again.');
    }

    private handleSignupError(error: any): Error {
        const status = error.response?.status;
        const message = error.response?.data?.message;

        if (status === 422) {
            return new Error('Nickname already exists.');
        }

        if (status === 400) {
            return new Error(message || 'Invalid registration data.');
        }

        return new Error(message || 'Signup failed. Please try again.');
    }

    // Métodos de utilidad para manejo de sesión
    private saveSession(response: AuthResponse, fullname: string): void {
        if (process.client) {
            sessionStorage.setItem('auth_token', response.token);
            sessionStorage.setItem('auth_nickname', response.nickname);
            if (response.fullname) {
                sessionStorage.setItem('auth_fullname', response.fullname);
            }
        }
    }

    private clearSession(): void {
        if (process.client) {
            sessionStorage.removeItem('auth_token');
            sessionStorage.removeItem('auth_nickname');
            sessionStorage.removeItem('auth_fullname');
        }
    }
}

// Exportar instancia única
export const authHandler = new AuthHandler();
