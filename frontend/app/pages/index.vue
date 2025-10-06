<script setup lang="ts">
definePageMeta({
  layout: 'default'
})
</script>

<template>
  <div class="text-center">
    <div class="max-w-3xl mx-auto">
      <!-- Hero Section -->
      <div class="py-12">
        <UIcon name="i-heroicons-users" class="w-20 h-20 mx-auto text-primary mb-6" />
        <h1 class="text-4xl font-bold text-gray-900 mb-4">
          Bienvenido a tu Red Social
        </h1>
        <p class="text-xl text-gray-600 mb-8">
          Conecta con amigos, comparte pensamientos y descubre contenido asombroso.
        </p>

        <!-- CTA Buttons -->
        <div class="flex justify-center gap-4">
          <UButton
              to="/signup"
              size="xl"
              color="primary"
              label="Empieza ahora"
              icon="i-heroicons-arrow-right"
              trailing
          />
          <UButton
              to="/login"
              size="xl"
              color="secondary"
              label="Login"
          />
        </div>
      </div>

      <!-- Features Section -->
      <div class="grid md:grid-cols-3 gap-8 mt-16">
        <UCard>
          <template #header>
            <div class="flex justify-center">
              <UIcon name="i-heroicons-chat-bubble-left-right" class="w-12 h-12 text-primary" />
            </div>
          </template>
          <div class="text-center">
            <h3 class="text-lg font-semibold mb-2">Share Posts</h3>
            <p class="text-gray-600">Create and share your thoughts with the community</p>
          </div>
        </UCard>

        <UCard>
          <template #header>
            <div class="flex justify-center">
              <UIcon name="i-heroicons-heart" class="w-12 h-12 text-primary" />
            </div>
          </template>
          <div class="text-center">
            <h3 class="text-lg font-semibold mb-2">Like & Interact</h3>
            <p class="text-gray-600">Engage with content from other users</p>
          </div>
        </UCard>

        <UCard>
          <template #header>
            <div class="flex justify-center">
              <UIcon name="i-heroicons-user-group" class="w-12 h-12 text-primary" />
            </div>
          </template>
          <div class="text-center">
            <h3 class="text-lg font-semibold mb-2">Build Community</h3>
            <p class="text-gray-600">Connect with people who share your interests</p>
          </div>
        </UCard>
      </div>
    </div>
  </div>
</template>

<style scoped> </style>