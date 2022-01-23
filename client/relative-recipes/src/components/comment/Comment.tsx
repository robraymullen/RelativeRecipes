import { useEffect, useState } from "react"
import { CommentProps } from "../../Types";

const Comment = ({id, text, postedDate, author} : CommentProps) : JSX.Element => {

    const [stateId, setId] = useState<string>('');
    const [stateText, setText] = useState<string>('');
    const [statePostedDate, setPostedDate] = useState<string>('');
    const [stateAuthor, setAuthor] = useState<string>('');

    useEffect(() => {
        setId(id);
        setText(text);
        setPostedDate(postedDate);
        setAuthor(author);
    });

    return (
        <div>
            <p>{text}</p>
            <small>{author}</small>
            <small>{postedDate}</small>
        </div>
    )
}

export default Comment;