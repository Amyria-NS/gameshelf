import { getGameById, createGame, updateGame, deleteGame } from "./api.js";

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

document.getElementById('create-game').addEventListener('submit', async (event)=>{
    event.preventDefault();
    const values = {
        title : document.getElementById("create-game-title").value,
        platform : document.getElementById("create-game-platform").value,
        status : document.getElementById("create-game-status").value,
        notes : document.getElementById("create-game-notes").value
    }
    
    const res = await createGame(values);
    document.querySelector('#create-game-output').innerHTML =  `${res.message}`;
});

document.getElementById('update-game').addEventListener('submit', async (event)=>{
    event.preventDefault();
    const values = {
        id : document.getElementById("update-game-id").value,
        title : document.getElementById("update-game-title").value,
        platform : document.getElementById("update-game-platform").value,
        status : document.getElementById("update-game-status").value,
        notes : document.getElementById("update-game-notes").value
    }
    const res = await updateGame(values);
    document.querySelector('#update-game-output').innerHTML =  `${res.message}`;
});

document.getElementById('delete-game').addEventListener('submit', async (event)=>{
    event.preventDefault();
    const res = await deleteGame(document.getElementById("game-id-input-delete").value);
    document.querySelector('#delete-game-output').innerHTML =  `${res.message}`;
    console.log(res);
});

