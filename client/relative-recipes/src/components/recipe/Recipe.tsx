
import React, {ChangeEvent, FormEvent, useState, useEffect} from "react";
import ReactMarkdown from 'react-markdown';

interface RecipeProps {
  id: number;
  title: string;
  text: string;
  postedDate: string;
}

const Recipe = ({ id, title, text, postedDate }: RecipeProps): JSX.Element => {

  const [stateId, setId] = useState<number>(0);
  const [stateTitle, setTitle] = useState<string>('');
  const [stateText, setText] = useState<string>('');

  useEffect(() => {
    setId(id);
    setTitle(title);
    setText(text);
  }, []);

  return (
    <div>
      <h1>{title}</h1>
      <aside>{id}</aside>
      <div>
        <ReactMarkdown>{text}</ReactMarkdown>
        <p>{postedDate}</p>
      </div>
    </div>
  )
};

export default Recipe;
