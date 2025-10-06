import { defineStore } from 'pinia';
import { postsHandler } from '@/utils/http/handlers/postsHandler';
import type { Post, CreatePostPayload } from '@/types/posts.types';

export const usePostsStore = defineStore('posts', () => {
    // State
    const posts = ref<Post[]>([]);
    const loading = ref(false);
    const errorMessage = ref('');
    const currentPage = ref(1);
    const totalPages = ref(1);
    const hasMore = ref(true);

    // Getters
    const isLoading = computed(() => loading.value);
    const allPosts = computed(() => posts.value);
    const postsCount = computed(() => posts.value.length);

    // Actions
    const fetchPosts = async () => {
        loading.value = true;
        errorMessage.value = '';

        try {
            const response = await postsHandler.getAllPosts();
            posts.value = response.posts;

            return { success: true };
        } catch (error: any) {
            errorMessage.value = error.message;
            return { success: false, error: error.message };
        } finally {
            loading.value = false;
        }
    }

    const createPost = async (payload: CreatePostPayload) => {
        loading.value = true;
        errorMessage.value = '';

        try {
            const newPost = await postsHandler.createPost(payload);

            // Agregar al inicio de la lista
            posts.value.unshift(newPost);

            return { success: true, post: newPost };
        } catch (error: any) {
            errorMessage.value = error.message;
            return { success: false, error: error.message };
        } finally {
            loading.value = false;
        }
    }

    const likePost = async (postId: string) => {
        try {
            await postsHandler.likePost(postId);

            // Actualizar el post en la lista
            const post = posts.value.find(p => p.id === postId)
            if (post) {
                post.likesCount = (post.likesCount || 0) + 1;
                post.isLiked = true;
            }

            return { success: true };
        } catch (error: any) {
            errorMessage.value = error.message;
            return { success: false, error: error.message };
        }
    }

    const clearError = () => {
        errorMessage.value = '';
    }

    return {
        // State
        posts,
        loading,
        errorMessage,
        currentPage,
        totalPages,
        hasMore,
        // Getters
        isLoading,
        allPosts,
        postsCount,
        // Actions
        fetchPosts,
        createPost,
        likePost,
        clearError
    }
})