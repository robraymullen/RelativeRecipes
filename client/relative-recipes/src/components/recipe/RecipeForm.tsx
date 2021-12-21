import React, { useState } from "react";
import { addRecipe } from "../../services/RecipeService";
import { EditorState, convertToRaw } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import './editor.css';
// import { convertToHTML } from 'draft-convert';
import { draftToMarkdown } from 'markdown-draft-js';

const RecipeForm = () => {

    const [title, setTitle] = useState<string>("");
    const [editorState, setEditorState] = useState(() => EditorState.createEmpty());

    const submitHandler = (event: React.FormEvent) => {
        event.preventDefault();
        const markdown = draftToMarkdown(convertToRaw(editorState.getCurrentContent()));
        console.log("submitting form:" + title + " text:" + markdown);
        addRecipe(title, markdown);

    };

    return (
        <div>
            <form onSubmit={submitHandler}>
                <label>
                    Recipe Title
                    <input type="text" value={title} onChange={e => setTitle(e.target.value)} />
                </label>
                <label>
                    Recipe
                </label>
                <div>
                <Editor
                        wrapperClassName="wrapper-class"
                        editorClassName="editor-class"
                        toolbarClassName="toolbar-class"
                        editorState={editorState}
                        onEditorStateChange={setEditorState} />
                </div>
                <input type="submit" value="Submit"></input>
            </form>
        </div>
    );
};

export default RecipeForm;
