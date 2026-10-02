import { API_BASE_URL } from "./baseURLConstant.js";

export async function getGameImageByGameId(gameId){
    const response = await fetch(`${API_BASE_URL}/games/images/${gameId}`);

    if (!response.ok) {
        let message;
        switch(response.status){
            case 404: 
                message = "No game found with that ID";
                break;
            default:
                message = `Failed to retrieve image. Status code: ${response.status}`
                break
        }
        return{
            success: false,
            message: message,
            status: response.status
        }
    }
    const blob = await response.blob();
    const imgURL = URL.createObjectURL(blob);
    return {
        success: true,
        message: "Entry retrieved",
        status: response.status,
        value: imgURL
    };
}

export async function uploadGameImage(formData){
    const response = await fetch(`${API_BASE_URL}/games/images`,{
        method : "POST",
        body: formData
    });

    if (!response.ok) {
        const error = await response.json();
        return{
            success: false,
            message: error.message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Image uploaded successfully",
        status: response.status,
        value: json
    }
}


export async function deleteGameImageByGameId(gameId){
    const response = await fetch(`${API_BASE_URL}/games/images/${gameId}`,{
        method : "DELETE",
    });

    if (!response.ok) {
        const error = await response.json();
        return{
            success: false,
            message: error.message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Image deleted successfully",
        status: response.status,
        value: json
    }
}
