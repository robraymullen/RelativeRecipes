
import React, {ChangeEvent, FormEvent, useState, useEffect} from "react";
import ReactMarkdown from 'react-markdown';

interface RecipeProps {
  id: string;
  title: string;
  text: string;
  tags: string[];
  postedDate: string;
}

const Recipe = ({ id, title, text, tags, postedDate }: RecipeProps): JSX.Element => {

  const [stateId, setId] = useState<string>(0);
  const [stateTitle, setTitle] = useState<string>('');
  const [stateTags, setTags] = useState<string[]>([]);
  const [stateText, setText] = useState<string>('');

  useEffect(() => {
    setId(id);
    setTitle(title);
    setText(text);
    setTags(tags);
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
    </div>
  )
};

export default Recipe;
