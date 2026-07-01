import * as Expr from '/js/utils/expr.js';

//FORM MODE
const mode = document.querySelector("meta[name='mode']")?.getAttribute("content") ?? "";

export const isView = mode === "VIEW";
export const isUpdate = mode === "UPDATE";
export const isCreate = mode === "CREATE";

export function ifNotView(onTrue, onFalse) {
    return Expr.If(!isView, onTrue, onFalse)
}
export function ifView(onTrue, onFalse) {
    return Expr.If(isView, onTrue, onFalse)
}

//CSRF (SECURITY)
const csrf_token = document.querySelector("meta[name='_csrf']").getAttribute("content");
const csrf_header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

export function fetchDelete(input, init){
    return fetch(input, {
        method: "DELETE",
        headers: {
            [csrf_header]: csrf_token
        }
    })
}