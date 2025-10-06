<script setup lang="ts"> </script>

<template>

  <header class="bg-white shadow-sm">
    <nav class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <!-- Logo/Brand -->
        <NuxtLink to="/" class="flex items-center space-x-2">
          <UIcon name="i-heroicons-home" class="w-8 h-8 text-primary" />
          <span class="text-xl font-bold text-gray-900">Social Network</span>
        </NuxtLink>

        <!-- Navigation Links -->
        <div class="flex items-center space-x-4">
          <UButton
              to="/login"
              color="secondary"
              variant="ghost"
              label="Login"
          />
          <UButton
              to="/signup"
              color="primary"
              label="Registro"
          />
        </div>
      </div>
    </nav>
  </header>
</template>

<style scoped> </style>