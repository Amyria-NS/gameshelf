import { getGameList } from "./api.js";

const options = {
    direction: "ASC",
    sortBy: "title"
}


async function displayGames(options){
    const games = await getGameList(options);
    const gameList = games.value;
    let mainHtml = ``;

    gameList.forEach(game=>{
        mainHtml += `
            <div class = "game-card">
                <div class="game-card-title">${game.title}</div>
                <img src = "images/game-placeholder.jpg">
                <div class="game-card-line">Status : ${game.status}</div>
                <div class="game-card-line game-card-notes">Notes: ${game.notes}</div>
                <div class="game-card-line">Date added: ${game.dateAdded}</div>
                <div class="game-card-line">Not completed</div>
            </div>   

        `;

    });

    console.log(mainHtml);
    document.getElementById("main-content").innerHTML = mainHtml;


}

displayGames(options);