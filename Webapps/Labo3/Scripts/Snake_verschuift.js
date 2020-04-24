class Vierkant{
    constructor(){
        this.canvas = document.getElementById("myCanvas");
        this.bepaalStart(this.canvas);
        this.startBewegen();
        this.draw();
        console.log("Maakt vierkant aan");
    }

    bepaalStart(){
        let x = Math.floor(this.canvas.offsetWidth * Math.random());
        let y = Math.floor(this.canvas.offsetWidth * Math.random());
        while(x < this.canvas.offsetWidth/4 || x > this.canvas.offsetWidth*3/4){
            x = Math.floor(this.canvas.offsetWidth * Math.random());
        }
        while(y < this.canvas.offsetHeight/4 || y > this.canvas.offsetHeight*3/4){
            y = Math.floor(this.canvas.offsetHeight * Math.random());
        }
        this.x = x;
        this.y = y;
        console.log("bepaalt startpositie");
    }

    draw(){
        let ctx = this.canvas.getContext("2d");
        ctx.clearRect(0,0,this.canvas.offsetWidth,this.canvas.offsetHeight);
        ctx.fillRect(this.x,this.y,10,10);
        console.log("tekent");
    }

    startBewegen(){
        this.richting = Math.round(Math.random());
        this.linksrechts = Math.round(2*Math.random())-1;
        while(this.linksrechts === 0){
            this.linksrechts = Math.round(2*Math.random())-1;
        }
        if(this.richting){
            this.x += this.linksrechts;
        }
        else {
            this.y += this.linksrechts;
        }
        console.log("is begonnen met bewegen");
        console.log("x: "+this.x + " y: "+this.y);
    }

    beweegEnKeerAanRand(){
        if(this.x === this.canvas.offsetWidth - 10 || this.x === 0) {
            this.linksrechts *= -1;
            console.log("botst horizontaal");
        }
        if(this.y === this.canvas.offsetHeight - 10 || this.y === 0) {
            this.linksrechts *= -1;
            console.log("botst verticaal");
        }
        if(this.richting){
            this.x += this.linksrechts;
        }
        else {
            this.y += this.linksrechts;
        }
        //console.log("Beweegt");
        this.draw();
        //console.log("x: "+this.x + " y: "+this.y);
    }
}

let v = new Vierkant();

function move(){
    v.beweegEnKeerAanRand();
}


setInterval(move,10);