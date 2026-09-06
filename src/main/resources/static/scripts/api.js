const API_BASE_URL = "http://localhost:8080/api";

export async function getGameById(id){
    const response = await fetch(`${API_BASE_URL}/games/${id}`);

    if (!response.ok) {
        let message;
        switch(response.status){
            case 404: 
                message = "No entry found with that ID";
                break;
            default:
                message = `Failed to retrieve game. Status code: ${response.status}`
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

export async function createGame(gameObj){
    const response = await fetch(`${API_BASE_URL}/games`, {
        method: "POST",
        headers: {
            'Content-Type' : 'application/json'
        },
        body:JSON.stringify(gameObj)
    });

    if (!response.ok){
        let message;
        switch(response.status){
            case 400:
                message = "Bad request, fields failed to validate"
                break;
            default:
                message = `POST failed with error code ${response.status}`
                break;
        }
        return {
            success:false,
            message: message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Game created successfully",
        status: response.status,
        value: json
    }
}

export async function updateGame(gameObj){
    const response = await fetch(`${API_BASE_URL}/games/${gameObj.id}`, {
        method: "PUT",
        headers: {
            'Content-Type' : 'application/json'
        },
        body:JSON.stringify(gameObj)
    });

    if (!response.ok){
        let message;
        switch(response.status){
            case 400:
                message = "Bad request, fields failed to validate"
                break;
            default:
                message = `PUT failed with error code ${response.status}`
                break;
        }
        return {
            success:false,
            message: message,
            status: response.status
        }
    }
    const json = await response.json();
    return {
        success: true,
        message: "Game updated successfully",
        status: response.status,
        value: json
    }
}

export async function deleteGame(id){
    const response = await fetch(`${API_BASE_URL}/games/${id}`, {
        method: "DELETE"
    });

    if (!response.ok){
        console.log(response);
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
        message: "Game deleted successfully",
        status: response.status,
        value: json
    }

}

export async function getGameList(options){
    const queryString = new URLSearchParams(options).toString();
    const response = await fetch(`${API_BASE_URL}/games?${queryString}`);
    

    if (!response.ok) {
        let message;
        switch(response.status){
            case 404: 
                message = "No entry found with that ID";
                break;
            default:
                message = `Failed to retrieve game. Status code: ${response.status}`
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