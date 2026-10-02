import { API_BASE_URL } from "./baseURLConstant.js";

/** GENRE API METHODS */

export async function getGenreById(id){
    const response = await fetch(`${API_BASE_URL}/genres/${id}`);

    if (!response.ok) {
        let message;
        switch(response.status){
            case 404: 
                message = "No entry found with that ID";
                break;
            default:
                message = `Failed to retrieve genre. Status code: ${response.status}`
                break
        }
        return{
            success: false,
            message: message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Entry retrieved",
        status: response.status,
        value: json
    }
}

export async function createGenre(genreObj){
    const response = await fetch(`${API_BASE_URL}/genres`,{
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify(genreObj)
    });

    if (!response.ok) {
        let message;
        switch(response.status){
            case 400:
                message = "Bad request, fields failed to validate"
                break;
            default:
                message = `POST failed with error code ${response.status}`
                break;
        }
        return{
            success: false,
            message: message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Entry retrieved",
        status: response.status,
        value: json
    }
}

export async function updateGenre(genreObj){
    const response = await fetch(`${API_BASE_URL}/genres/${genreObj.id}`,{
        method: "PUT",
        headers: {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify(genreObj)
    });

    if (!response.ok) {
        let message;
        switch(response.status){
            case 404: 
                message = "No entry found with that ID";
                break;
            case 400:
                message = "Bad request, fields failed to validate"
                break;
            default:
                message = `PUT failed with error code ${response.status}`
                break
        }
        return{
            success: false,
            message: message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Entry retrieved",
        status: response.status,
        value: json
    }
}

export async function deleteGenre(id){
    const response = await fetch(`${API_BASE_URL}/genres/${id}`, {
        method: "DELETE"
    }) ;
    if (!response.ok){
        //console.log(response);
        let message;
        switch(response.status){
            case 404:
                message = 'No entry found with that ID'
            default:
                message = `Deletion failed with status code ${response.status}`
        }
        return {
            success: false,
            message: message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Genre deleted successfully",
        status: response.status,
        value: json
    }
}

export async function getGenreList(options){
    const queryString = new URLSearchParams(options).toString();
    const response = await fetch(`${API_BASE_URL}/genres?${queryString}`);
    if (!response.ok) {
        const message = `Failed to retrieve game. Status code: ${response.status}`;
        return{
            success: false,
            message: message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Entry retrieved",
        status: response.status,
        value: json
    }
}
