export type Post = {
    id: string,
    title: string,
    content: string,
    authorNickname: string,
    authorFullname?: string | null,
    createdAt: string,
    likesCount?: number,
    isLiked?: boolean
};

export type CreatePostPayload = {
    title: string,
    content: string
};

export type PostsResponse = {
    posts: Post[]
}