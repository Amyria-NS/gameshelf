import { getGameById } from "./api.js";

async function displayGameInfo(id){
    const obj = await getGameById(id);
    if (!obj.success){
        document.querySelector('#get-game-output').innerHTML=`
            ${obj.message}
        `;
        return;
    }
    const value = obj.value;
    console.log(obj);
    document.querySelector('#get-game-output').innerHTML=`
        GAME NAME = ${value.title} <br>
        PLATFORM = ${value.platform} <br>
        NOTES = ${value.notes} <br>
        STATUS = ${value.status} <br>
        DATE ADDED = ${value.dateAdded} <br>
    `
}

document.getElementById('get-game-input').addEventListener('submit', async (event)=>{
    event.preventDefault();
    const value = document.getElementById("game-id-input").value;
    if (Number.isInteger(Number(value))){
        const id = Number(value);
        if (id <= 0){
            console.log("Invalid Input");
            return;
        }
        displayGameInfo(id);

    }
    else{
        console.log("Invalid Input");
    }
});