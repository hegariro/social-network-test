export type LoginForm = {
    nickname: string,
    password: string,
    rememberMe: boolean
};

export type SignupForm = {
    fullname: string,
    nickname: string,
    password: string,
    confirmPassword: string
};

export type AuthResponse = {
    token: string,
    nickname: string
};

export type User = {
    token: string,
    nickname: string,
    fullname?: string | null
};

export type PayloadLogin = {
    nickname: string,
    passwd: string
};

export type PayloadSignUp = {
    nickname: string,
    name: string,
    password: string
};
