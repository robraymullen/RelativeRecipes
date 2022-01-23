export interface RecipeProps {
    id: string;
    title: string;
    text: string;
    tags: string[];
    postedDate: string;
    comments: CommentProps[]
};

export interface CommentProps {
    id: string;
    text: string,
    postedDate: string,
    author: string
}