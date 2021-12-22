import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';
import Recipe from "./components/recipe/Recipe";
import { fetchAllRecipes } from './services/RecipeService';
import RecipeForm from './components/recipe/RecipeForm';

interface RecipeProps {
  id: number;
  title: string;
  text: string;
  tags: string[];
  postedDate: string;
}

function App() {

  const[recipes, setRecipes] = useState<RecipeProps[]>([]);


  const setStates = (recipes: RecipeProps[]) => {
    setRecipes(recipes)
  }

  useEffect(() => {
    fetchAllRecipes(setStates, ()=> {console.log("error!!!!!!!!!!!")});
  }, []);
  const date = new Date();
  return (
    <div className="App">
      <RecipeForm></RecipeForm>
      {
          recipes.map((recipe) =>{
            return <Recipe id={recipe.id} title={recipe.title} text={recipe.text} postedDate={date.toDateString()} tags={recipe.tags}/>
          })
        }
    </div>
  );
}

export default App;
