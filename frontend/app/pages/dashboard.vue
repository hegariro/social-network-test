<template>
  <div class="max-w-3xl mx-auto">
    <h1 class="text-3xl font-bold text-gray-900 mb-8">Feed</h1>

    <!-- Refresh Button -->
    <div class="mb-6">
      <UButton
          @click="postsStore.refresh()"
          :loading="postsStore.isLoading && postsStore.currentPage === 1"
          icon="i-heroicons-arrow-path"
          label="Recargar"
          color="white"
      />
    </div>

    <!-- Posts List -->
    <div v-if="postsStore.postsCount > 0" class="space-y-4">
      <UCard v-for="post in postsStore.allPosts" :key="post.id">
        <div class="space-y-3">
          <!-- Author Info -->
          <div class="flex items-center space-x-3">
            <UAvatar size="sm">
              <span class="text-xs">{{ post.authorNickname.substring(0, 2).toUpperCase() }}</span>
            </UAvatar>
            <div>
              <p class="font-semibold text-gray-900">{{ post.authorFullname || post.authorNickname }}</p>
              <p class="text-sm text-gray-500">@{{ post.authorNickname }}</p>
            </div>
          </div>

          <!-- Content -->
          <p class="text-gray-700">{{ post.content }}</p>

          <!-- Footer -->
          <div class="flex items-center justify-between pt-3 border-t">
            <span class="text-sm text-gray-500">{{ formatDate(post.createdAt) }}</span>

            <div class="flex items-center space-x-2">
              <UButton
                  :icon="post.isLiked ? 'i-heroicons-heart-solid' : 'i-heroicons-heart'"
                  :color="post.isLiked ? 'red' : 'gray'"
                  variant="ghost"
                  size="sm"
                  @click="toggleLike(post)"
              />
              <span class="text-sm text-gray-600">{{ post.likesCount || 0 }}</span>
            </div>
          </div>
        </div>
      </UCard>
    </div>

    <!-- Empty State -->
    <UCard v-else-if="!postsStore.isLoading">
      <div class="text-center py-12">
        <UIcon name="i-heroicons-document-text" class="w-16 h-16 mx-auto text-gray-400 mb-4" />
        <h3 class="text-lg font-semibold text-gray-900 mb-2">A&uacute;n no hay publicaciones</h3>
        <p class="text-gray-600 mb-4">¡Se el primero en publicar!</p>
        <UButton to="/create-post" color="primary" icon="i-heroicons-plus" label="Crear Publicación" />
      </div>
    </UCard>

    <!-- Loading State -->
    <div v-if="postsStore.isLoading && postsStore.currentPage === 1" class="text-center py-12">
      <UIcon name="i-heroicons-arrow-path" class="w-12 h-12 mx-auto animate-spin text-primary mb-4" />
      <p class="text-gray-600">Cargando publicaciones...</p>
    </div>

    <!-- Load More Button -->
    <div v-if="postsStore.hasMore" class="mt-6 text-center">
      <UButton
          @click="postsStore.loadMore()"
          :loading="postsStore.isLoading && postsStore.currentPage > 1"
          label="Cargar más publicaciones"
          color="white"
          block
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { usePostsStore } from '@/stores/posts';
import type { Post } from '@/types/posts.types';

definePageMeta({
  layout: 'main'
});

const postsStore = usePostsStore();

// Cargar posts al montar
onMounted(async () => {
  await postsStore.fetchPosts();
});

// Toggle like/unlike
const toggleLike = async (post: Post) => (await postsStore.likePost(post.id));

// Formatear fecha
const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric'
  });
}
</script>