<template>
  <div class="min-h-[calc(100vh-12rem)] flex items-center justify-center">
    <UCard class="w-full max-w-md">
      <template #header>
        <div class="text-center">
          <UIcon name="i-heroicons-user-plus" class="w-12 h-12 mx-auto text-primary mb-2" />
          <h2 class="text-2xl font-bold text-gray-900">Crear cuenta</h2>
          <p class="text-gray-600 mt-1">&Uacute;nete a nuestra comunidad</p>
        </div>
      </template>

      <UForm :state="signupForm" :validate="validate" @submit="onSubmit" class="space-y-4">
        <!-- Full Name Field -->
        <UFormGroup label="Nombre completo" name="fullname" required>
          <UInput
              v-model="signupForm.fullname"
              type="text"
              placeholder="Nombre completo"
              icon="i-heroicons-user"
              size="lg"
              :disabled="authStore.isLoading"
          />
        </UFormGroup>

        <!-- Nickname Field -->
        <UFormGroup label="Nombre de usuario" name="nickname" required>
          <UInput
              v-model="signupForm.nickname"
              type="text"
              placeholder="Escoge un nombre de usuario"
              icon="i-heroicons-at-symbol"
              size="lg"
              :disabled="authStore.isLoading"
          />
        </UFormGroup>

        <!-- Password Field -->
        <UFormGroup label="Password" name="password" required>
          <UInput
              v-model="signupForm.password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="Crea un password"
              icon="i-heroicons-lock-closed"
              size="lg"
              :disabled="authStore.isLoading"
          >
            <template #trailing>
              <UButton
                  color="gray"
                  variant="link"
                  :icon="showPassword ? 'i-heroicons-eye-slash' : 'i-heroicons-eye'"
                  @click="showPassword = !showPassword"
                  :disabled="authStore.isLoading"
              />
            </template>
          </UInput>
        </UFormGroup>

        <!-- Confirm Password Field -->
        <UFormGroup label="Confirmar Password" name="confirmPassword" required>
          <UInput
              v-model="signupForm.confirmPassword"
              :type="showConfirmPassword ? 'text' : 'password'"
              placeholder="Confirma tu password"
              icon="i-heroicons-lock-closed"
              size="lg"
              :disabled="authStore.isLoading"
          >
            <template #trailing>
              <UButton
                  color="gray"
                  variant="link"
                  :icon="showConfirmPassword ? 'i-heroicons-eye-slash' : 'i-heroicons-eye'"
                  @click="showConfirmPassword = !showConfirmPassword"
                  :disabled="authStore.isLoading"
              />
            </template>
          </UInput>
        </UFormGroup>

        <!-- Terms and Conditions -->
        <div class="flex items-start space-x-2">
          <UCheckbox
              v-model="acceptTerms"
              :disabled="authStore.isLoading"
          />
          <label class="text-sm text-gray-600">
            I agree to the
            <UButton
                color="primary"
                variant="link"
                label="Términos y Condiciones"
                :padded="false"
                size="sm"
            />
          </label>
        </div>

        <!-- Submit Button -->
        <UButton
            type="submit"
            color="primary"
            size="lg"
            block
            :loading="authStore.isLoading"
            :disabled="!isFormValid"
        >
          Crear cuenta
        </UButton>

        <!-- Error Message -->
        <UAlert
            v-if="authStore.errorMessage"
            color="red"
            variant="soft"
            :title="authStore.errorMessage"
            icon="i-heroicons-exclamation-triangle"
            :close-button="{ icon: 'i-heroicons-x-mark', color: 'red', variant: 'link' }"
            @close="authStore.clearError()"
        />
      </UForm>

      <template #footer>
        <div class="text-center text-sm text-gray-600">
          Ya tienes una cuenta?
          <UButton
              to="/login"
              color="primary"
              variant="link"
              label="Login"
              :padded="false"
          />
        </div>
      </template>
    </UCard>
  </div>
</template>

<script setup lang="ts">
import type { SignupForm } from '@/types/auth.types';
import type { PayloadSignUp } from '@/types/auth.types';
import { useAuthStore } from '@/stores/auth';
import type { FormError } from '#ui/types';

definePageMeta({
  layout: 'default'
});

const authStore = useAuthStore();
const router = useRouter();

const signupForm = reactive<SignupForm>({
  fullname: '',
  nickname: '',
  password: '',
  confirmPassword: ''
});

const showPassword = ref(false);
const showConfirmPassword = ref(false);
const acceptTerms = ref(false);

const isFormValid = computed(() => {
  return (
      signupForm.fullname.trim() !== '' &&
      signupForm.nickname.trim() !== '' &&
      signupForm.password.trim() !== '' &&
      signupForm.confirmPassword.trim() !== '' &&
      signupForm.password === signupForm.confirmPassword &&
      acceptTerms.value
  )
})

const validate = (state: SignupForm): FormError[] => {
  const errors: FormError[] = [];

  // Validar fullname
  if (!state.fullname || state.fullname.trim().length < 3) {
    errors.push({
      path: 'fullname',
      message: 'Tu nombre debe contener por lo menos 3 caracteres'
    });
  }

  // Validar nickname
  if (!state.nickname || state.nickname.trim().length < 3) {
    errors.push({
      path: 'nickname',
      message: 'Tu nombre de usuario debe contener por lo menos 3 caracteres'
    });
  }

  // Validar que el nickname no tenga espacios
  if (state.nickname && /\s/.test(state.nickname)) {
    errors.push({
      path: 'nickname',
      message: 'Tu nombre de usuario no puede contener espacios'
    });
  }

  // Validar password
  if (!state.password || state.password.length < 8) {
    errors.push({
      path: 'password',
      message: 'El password debe contener por lo menos 8 caracteres'
    });
  }

  // Validar que las contraseñas coincidan
  if (state.password !== state.confirmPassword) {
    errors.push({
      path: 'confirmPassword',
      message: 'El passwords no coincide'
    });
  }

  return errors;
}

const onSubmit = async () => {
  if (!acceptTerms.value) {
    authStore.errorMessage = 'Debes aceptar los términos y condiciones';
    return;
  }

  // Construir el payload según el tipo PayloadSignUp
  const payload: PayloadSignUp = {
    nickname: signupForm.nickname.trim(),
    name: signupForm.fullname.trim(),
    password: signupForm.password
  };

  const result = await authStore.signup(payload);

  if (result.success) {
    // Redirigir al dashboard después del registro exitoso
    await router.push('/dashboard');
  }
}

// Si ya está autenticado, redirigir al dashboard
onMounted(() => {
  if (authStore.isAuthenticated) {
    router.push('/dashboard');
  }
});
</script>