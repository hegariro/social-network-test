<template>
  <div class="min-h-[calc(100vh-12rem)] flex items-center justify-center">
    <UCard class="w-full max-w-md">
      <template #header>
        <div class="text-center">
          <UIcon name="i-heroicons-lock-closed" class="w-12 h-12 mx-auto text-primary mb-2" />
          <h2 class="text-2xl font-bold text-gray-900">Bienvenido de nuevo</h2>
          <p class="text-gray-600 mt-1">Ingresa en tu cuenta</p>
        </div>
      </template>

      <UForm :state="loginForm" @submit="onSubmit" class="space-y-4">
        <!-- Email/Username Field -->
        <UFormGroup label="Email or Username" name="identifier" required>
          <UInput
              v-model="loginForm.nickname"
              type="text"
              placeholder="Inserta tu nickname"
              icon="i-heroicons-user"
              size="lg"
              :disabled="loading"
          />
        </UFormGroup>

        <!-- Password Field -->
        <UFormGroup label="Password" name="password" required>
          <UInput
              v-model="loginForm.password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="Inserta tu password"
              icon="i-heroicons-lock-closed"
              size="lg"
              :disabled="loading"
          >
            <template #trailing>
              <UButton
                  color="gray"
                  variant="link"
                  :icon="showPassword ? 'i-heroicons-eye-slash' : 'i-heroicons-eye'"
                  @click="showPassword = !showPassword"
                  :disabled="loading"
              />
            </template>
          </UInput>
        </UFormGroup>

        <!-- Remember Me & Forgot Password -->
        <div class="flex items-center justify-between">
          <UCheckbox v-model="loginForm.rememberMe" label="Recuerda mis datos" />
          <UButton
              color="primary"
              variant="link"
              label="Olvidaste tu password?"
              :padded="false"
              size="sm"
          />
        </div>

        <!-- Submit Button -->
        <UButton
            type="submit"
            color="primary"
            size="lg"
            block
            :loading="loading"
            :disabled="!isFormValid"
        >
          Ingresar
        </UButton>

        <!-- Error Message -->
        <UAlert
            v-if="errorMessage"
            color="red"
            variant="soft"
            :title="errorMessage"
            icon="i-heroicons-exclamation-triangle"
            :close-button="{ icon: 'i-heroicons-x-mark', color: 'red', variant: 'link' }"
            @close="errorMessage = ''"
        />
      </UForm>

      <template #footer>
        <div class="text-center text-sm text-gray-600">
          No tienes una cuenta?
          <UButton
              to="/signup"
              color="primary"
              variant="link"
              label="Registrate"
              :padded="false"
          />
        </div>
      </template>
    </UCard>
  </div>
</template>

<script setup lang="ts">
import type { LoginForm } from "@types/auth.types";
import { useAuthStore } from '@/stores/auth';

definePageMeta({
  layout: 'default'
});

const authStore = useAuthStore();
const router = useRouter();

const loginForm = reactive<LoginForm>({
  nickname: '',
  password: '',
  rememberMe: false
});
const showPassword = ref(false);

const isFormValid = computed(() =>
    (loginForm.nickname.trim() !== '' && loginForm.password.trim() !== ''));

const onSubmit = async () => {
  const result = await authStore.login({nickname: loginForm.nickname, passwd: loginForm.password});

  if (result.success) {
    await router.push('/dashboard');
  }
};

onMounted(() => {
  if (authStore.isAuthenticated) {
    router.push('/dashboard');
  }
});
</script>
