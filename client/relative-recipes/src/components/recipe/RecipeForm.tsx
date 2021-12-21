import React, { useState } from "react";
import { addRecipe } from "../../services/RecipeService";

const RecipeForm = () => {

    const [title, setTitle] = useState<string>("");
    const [text, setText] = useState<string>("");

    const submitHandler = (event: React.FormEvent) => {
        event.preventDefault();
        console.log("submitting form:"+title+" text:"+text);
        //push the return of the addRecipe into a list of recipes that are stored on client side.
        //updating the list will force a re-render to the App.tsx
        addRecipe(title, text);
    };

    return (
        <div>
            <form onSubmit={submitHandler}>
                <label>
                    Recipe Title
                    <input type="text" value={title} onChange={e => setTitle(e.target.value)}/>
                </label>
                <label>
                    Recipe
                    <textarea onChange={e => setText(e.target.value)}>{text}</textarea>
                </label>
                <input type="submit" value="Submit"></input>
            </form>
        </div>
    );
};

export default RecipeForm;
