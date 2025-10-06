import { httpClient } from '@/utils/http/httpClient';
import type { Post, CreatePostPayload, PostsResponse } from '@/types/posts.types';

export class PostsHandler {
    private readonly BASE_PATH = '/api/v1/user/posts'

    async getAllPosts(): Promise<PostsResponse> {
        try {
            return await httpClient.get<PostsResponse>(
                `${this.BASE_PATH}/all`
            )
        } catch (error: any) {
            throw new Error(error.response?.data?.message || 'Failed to fetch posts')
        }
    }

    async createPost(payload: CreatePostPayload): Promise<Post> {
        try {
            return await httpClient.post<Post>(this.BASE_PATH, payload)
        } catch (error: any) {
            throw this.handleCreateError(error)
        }
    }

    /* ToDo implementar Server-Side Events (SSE) para este empoint */
    async likePost(postId: string): Promise<void> {
        try {
            await httpClient.post(`${this.BASE_PATH}/${postId}/like`)
        } catch (error: any) {
            throw new Error(error.response?.data?.message || 'Failed to like post')
        }
    }

    private handleCreateError(error: any): Error {
        const status = error.response?.status
        const message = error.response?.data?.message

        if (status === 400) {
            return new Error('Invalid post content')
        }

        if (status === 401) {
            return new Error('You must be logged in to create a post')
        }

        return new Error(message || 'Failed to create post')
    }
}

export const postsHandler = new PostsHandler();
