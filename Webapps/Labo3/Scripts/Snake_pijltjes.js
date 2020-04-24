let canvas = document.getElementById("myCanvas");
let context = canvas.getContext("2d");
context.fillStyle = "#FF0000";

let inPauze = false;
let timeOutPeriode = 64;

let blokgrootte = 20;
let width = canvas.width;
let height = canvas.height;
let x= width /4 + Math.random() * width / 2;
let y = height / 4 + Math.random() * height / 2;

let dx = 0;
let dy = 0;
let kans = Math.random();
if (kans < 0.25){
    dx = 1;
}
else if (kans < 0.5) {
    dx = -1;
}
else if (kans < 0.75){
    dy = 1;
}
else {
    dy = -1;
}

function gameLoop(){
    verschuifEnTekenVierkant();
}

function verschuifEnTekenVierkant(){
    context.clearRect(0,0,canvas.width,canvas.height);
    if(x+dx+blokgrootte > width - 1 || x+ dx < 0){
        dx *= -1;
    }
    if(y + dy + blokgrootte > height - 1 || y + dy < 0){
        dy *= -1;
    }
    x += dx;
    y += dy;
    tekenVierkant();
}

function tekenVierkant(){
    context.fillRect(x,y,blokgrootte,blokgrootte);
}

function togglePauze() {
    inPauze = !inPauze;
    if(inPauze){
        window.clearInterval(metronoom);
    } else {
        if(timeOutPeriode / 2 >= 1){
            timeOutPeriode /= 2;
        }
        metronoom = window.setInterval(verschuifEnTekenVierkant, timeOutPeriode);
    }
}

let keyHandler = (e) => {
    if(e.key === "ArrowLeft"){
        dx = -1;
        dy = 0;
    } else if (e.key === "ArrowUp") {
        dx = 0;
        dy = -1;
    } else if (e.key === "ArrowRight") {
        dx = 1;
        dy = 0;
    } else if (e.key === "ArrowDown"){
        dx = 0;
        dy = 1;
    } else if (e.key === "p"){
        togglePauze();
    }
};

document.addEventListener("keydown",keyHandler);
let metronoom = window.setInterval(gameLoop, timeOutPeriode);















