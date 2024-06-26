import {RECIPE_API} from "./Constants";

const fetchRecipe = (resolve: Function, reject: Function, id?: number) => {
    const headers = { 'Content-Type': 'application/json' };
    fetch(`${RECIPE_API.GET_BY_ID}${id !== undefined ? id : ""}`, {headers})
        .then(response => response.json())
        .then(data => {
            resolve(data)
        })
        .catch(
            reject());
};

export const fetchRecipeById = (resolve: Function, reject: Function, id: number) => {
    fetchRecipe(resolve, reject, id);
};

export const fetchAllRecipes = (resolve: Function, reject: Function) => {
    fetchRecipe(resolve, reject);
};

export const addRecipe = (title:string, text: string, tags: string) => {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: title, text: text, tags: tags.split(",") })
    };
    fetch(RECIPE_API.POST, requestOptions)
        .then(response => response.json())
        .then(data => console.log(data));
}
