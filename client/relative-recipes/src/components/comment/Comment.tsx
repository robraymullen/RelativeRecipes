import { CommentProps } from "../../Types";

const Comment = ({id, text, postedDate, author} : CommentProps) : JSX.Element => {
    return (
        <div>
            <p>{text}</p>
            <small>{author}</small>
            <small>{postedDate}</small>
        </div>
    )
}

export default Comment;