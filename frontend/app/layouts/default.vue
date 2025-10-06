<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header/Navbar -->
    <Header />

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <slot />
    </main>

    <!-- Footer -->
    <Footer />
  </div>
</template>

<script setup lang="ts">
import Footer from "@slides/DefaultFooter.vue";
import Header from "@slides/PublicHeader";
</script>