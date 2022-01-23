
import React, {ChangeEvent, FormEvent, useState, useEffect} from "react";
import ReactMarkdown from 'react-markdown';
import { CommentProps, RecipeProps } from "../../Types";
import Comment from '../comment/Comment';

const Recipe = ({ id, title, text, tags, postedDate, comments }: RecipeProps): JSX.Element => {

  const [stateId, setId] = useState<string>('');
  const [stateTitle, setTitle] = useState<string>('');
  const [stateTags, setTags] = useState<string[]>([]);
  const [stateText, setText] = useState<string>('');
  const [stateComments, setComments] = useState<CommentProps[]>([]);

  useEffect(() => {
    setId(id);
    setTitle(title);
    setText(text);
    setTags(tags);
    setComments(comments);
  }, []);

  return (
    <div>
      <h1>{title}</h1>
      <aside>{id}</aside>
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
        comments.map((comment) => {
          return <Comment id={comment.id} text={comment.text} author={comment.author} postedDate={comment.postedDate}></Comment>
        })
      }
    </div>
  )
};

export default Recipe;
