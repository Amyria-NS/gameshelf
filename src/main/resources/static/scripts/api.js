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