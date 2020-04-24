import Snake from "./Modules/Snake.js";
import Food from "./Modules/Food.js";

let snake;
let context;
let food = [];
let score = 0;
let world_width;
let world_height;
let timer;
let name;
let scores = [];

let intersectsFood = (snake,food) => {
    return food.intersects(snake.blocks[0].x, snake.blocks[0].y, snake.blocks[0].width, snake.blocks[0].width, food.x, food.y, food.width, food.width);
};

let addScoreToTable = (s) => {
    let table = document.querySelector("table");
    let tr = document.createElement("tr");

    for(let e of [s.date,s.name,s.score]){
        let td = document.createElement("td");
        td.innerText = e;
        tr.appendChild(td);
    }
    table.appendChild(tr);
};

let addScore =() => {
    let s = {"name": name, "date": new Date().toLocaleDateString('en'), "score": score};
    scores.push(s);
    addScoreToTable(s);
    localStorage.setItem("scores", JSON.stringify(scores));
};

let loadScores = () => {
    let t = localStorage.getItem("scores");
    if(t!==null){
        for(let s of JSON.parse(t)){
            addScoreToTable(s);
            scores.push(s);
        }
    }
};

let gameLoop =() => {
    snake.move();
    context.clearRect(0,0,700,600);
    snake.draw(context);
    for(let f of food){
        if(intersectsFood(snake,f)){
            f.reposition();
            snake.grow(f.score);
            score += f.score;
        }
        f.draw(context);
    }
    if(snake.intersects_self() || snake.x < 0 || snake.x >= world_width || snake.y<0 || snake.y >= world_height){
        window.clearInterval(timer);
        addScore();
        alert("Game over!");
    }
    context.fillText("Score" + score, 520, 50);
};

let keyHandler = (e) => {
    if(e.keyCode === 37) { //links
        snake.setDirection(-1, 0);
    }
    else if (e.keyCode === 38){ //boven
        snake.setDirection(0,-1);
    }
    else if (e.keyCode === 39) { //recchts
        snake.setDirection(1,0);
    }
    else if (e.keyCode === 40) { //onder
        snake.setDirection(0,1);
    }
};

window.addEventListener("load", () => {
    loadScores();
    name = prompt("Wat is je naam?");
    let canvas = document.querySelector("canvas");
    world_width = canvas.width;
    world_height = canvas.height;

    context = canvas.getContext("2d");
    context.font = "30px Arial";
    context.fillStyle = "#FF0000";

    snake = new Snake(world_width, world_height);

    food.push(new Food(world_width, world_height, "Images/food1.png",1));
    food.push(new Food(world_width, world_height, "Images/food2.png",3));
    food.push(new Food(world_width, world_height, "Images/food3.png",5));
    food.push(new Food(world_width, world_height, "Images/food4.png",7));
    food.push(new Food(world_width, world_height, "Images/food5.png",10));

    timer = window.setInterval(gameLoop, 250);
    document.addEventListener("keydown", keyHandler);
})