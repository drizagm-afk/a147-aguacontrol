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