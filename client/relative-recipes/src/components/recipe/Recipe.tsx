
import ReactMarkdown from 'react-markdown';
import { CommentProps, RecipeProps } from "../../Types";
import Comment from '../comment/Comment';

const Recipe = ({ id, title, text, tags, postedDate, comments }: RecipeProps): JSX.Element => {

  return (
    <div>
      <h1>{title}</h1>
      <div>
        <ReactMarkdown>{text}</ReactMarkdown>
        <p>{postedDate}</p>
      </div>
      <p>
      {
        tags.join(", ")
      }
      </p>
      {
        comments.map((comment: CommentProps) => {
          return <Comment id={comment.id} text={comment.text} author={comment.author} postedDate={comment.postedDate}></Comment>
        })
      }
    </div>
  )
};

export default Recipe;
