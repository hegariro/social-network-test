import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosError } from 'axios'

const BACKEND_BASE_URL: string = 'http://localhost:8081';
const REQUEST_TIMEOUT: number = 10000;
const DEFAULT_CONTENT_TYPE: string = 'application/json';

class HttpClient {
    private client: AxiosInstance

    constructor() {
        this.client = axios.create({
            baseURL: BACKEND_BASE_URL,
            timeout: REQUEST_TIMEOUT,
            headers: {
                'Content-Type': DEFAULT_CONTENT_TYPE
            }
        });

        this.setupInterceptors();
    }

    private setupInterceptors() {
        // Request interceptor - agregar token automáticamente
        this.client.interceptors.request.use(
            (config) => {
                const token = sessionStorage.getItem('auth_token')
                if (token) {
                    config.headers.Authorization = `Bearer ${token}`
                }
                return config
            },
            (error) => Promise.reject(error)
        );

        // Response interceptor - manejar errores globalmente
        this.client.interceptors.response.use(
            (response) => response,
            (error: AxiosError) => {
                this.handleError(error)
                return Promise.reject(error)
            }
        )
    }

    private handleError(error: AxiosError<any>) {
        if (!error.response) {
            console.error('Network error:', error.message);
            return;
        }

        const { status, data } = error.response;

        switch (status) {
            case 400:
                console.error('Bad Request:', data?.message)
                break
            case 401:
                console.error('Unauthorized')
                // Limpiar sesión y redirigir al login
                sessionStorage.removeItem('auth_token')
                sessionStorage.removeItem('auth_user')
                if (process.client) {
                    window.location.href = '/login'
                }
                break
            case 403:
                console.error('Forbidden:', data?.message)
                break
            case 404:
                console.error('Not Found:', data?.message)
                break
            case 422:
                console.error('Unprocessable entity:', data?.message)
                break
            case 500:
                console.error('Internal Server Error')
                break
            default:
                console.error('Error:', data?.message || 'Unknown error')
        }
    }

    // Métodos públicos
    async get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
        const response = await this.client.get<T>(url, config)
        return response.data
    }

    async post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        const response = await this.client.post<T>(url, data, config)
        return response.data
    }

    async put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        const response = await this.client.put<T>(url, data, config)
        return response.data
    }

    async delete<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
        const response = await this.client.delete<T>(url, config)
        return response.data
    }

    async patch<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        const response = await this.client.patch<T>(url, data, config)
        return response.data
    }
}

export const httpClient = new HttpClient();
