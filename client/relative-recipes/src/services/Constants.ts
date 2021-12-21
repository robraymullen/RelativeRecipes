const recipePort = 9090;
const recipeServer = `http://localhost:${recipePort}`;

export const RECIPE_API = {
    GET_BY_ID: `${recipeServer}/recipes/`,
    DELETE: `${recipeServer}/recipes`,
    PUT: `${recipeServer}/recipes`
}