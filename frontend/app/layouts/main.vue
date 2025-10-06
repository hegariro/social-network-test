<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header/Navbar -->
    <header class="bg-white shadow-sm sticky top-0 z-50">
      <nav class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <!-- Logo/Brand -->
          <div class="flex items-center space-x-8">
            <NuxtLink to="/dashboard" class="flex items-center space-x-2">
              <UIcon name="i-heroicons-home" class="w-8 h-8 text-primary" />
              <span class="text-xl font-bold text-gray-900">Social Network</span>
            </NuxtLink>

            <!-- Navigation Links -->
            <div class="hidden md:flex items-center space-x-4">
              <UButton
                  to="/dashboard"
                  color="white"
                  variant="ghost"
                  icon="i-heroicons-newspaper"
                  label="Feed"
              />
              <UButton
                  to="/my-posts"
                  color="white"
                  variant="ghost"
                  icon="i-heroicons-document-text"
                  label="My Posts"
              />
            </div>
          </div>

          <!-- User Menu -->
          <div class="flex items-center space-x-4">
            <!-- Create Post Button -->
            <UButton
                to="/create-post"
                color="primary"
                icon="i-heroicons-plus"
                label="New Post"
                class="hidden sm:flex"
            />

            <!-- User Dropdown -->
            <UDropdown :items="userMenuItems" :popper="{ placement: 'bottom-end' }">
              <UAvatar
                  :alt="authStore.displayName"
                  size="md"
                  class="cursor-pointer"
              >
                <template #default>
                  <span class="text-sm font-medium">{{ userInitials }}</span>
                </template>
              </UAvatar>

              <template #item="{ item }">
                <span class="truncate">{{ item.label }}</span>
                <UIcon :name="item.icon" class="flex-shrink-0 h-4 w-4 ms-auto" />
              </template>
            </UDropdown>
          </div>
        </div>
      </nav>
    </header>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <slot />
    </main>

    <!-- Loading Overlay -->
    <div v-if="authStore.isLoading" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <UCard>
        <div class="flex items-center space-x-3">
          <UIcon name="i-heroicons-arrow-path" class="w-6 h-6 animate-spin text-primary" />
          <span>Loading...</span>
        </div>
      </UCard>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore();
const router = useRouter();

// Verificar autenticación
onMounted(() => {
  authStore.restoreSession()

  if (!authStore.isAuthenticated) {
    router.push('/login');
  }
})

// Watch para redirigir si pierde la sesión
watch(() => authStore.isAuthenticated, (isAuth) => {
  if (!isAuth) {
    router.push('/login');
  }
})

// Computed para iniciales del usuario
const userInitials = computed(() => {
  const name = authStore.displayName
  if (!name || name === 'User') return '?'
  return name.substring(0, 2).toUpperCase()
})

// Menu items del usuario
const userMenuItems = computed(() => [
  [{
    label: authStore.displayName,
    slot: 'account',
    disabled: true
  }],
  [{
    label: 'Profile',
    icon: 'i-heroicons-user',
    click: () => router.push('/profile')
  }, {
    label: 'Settings',
    icon: 'i-heroicons-cog-6-tooth',
    click: () => router.push('/settings')
  }],
  [{
    label: 'Logout',
    icon: 'i-heroicons-arrow-right-on-rectangle',
    click: () => authStore.logout()
  }]
])
</script>