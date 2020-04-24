import Block from "./Block.js";
import Drawable from "./Drawable.js";

export default class Snake extends Drawable {
    constructor(world_width, world_height, length = 5){
        super(Math.random()*world_width,Math.random()*world_height, world_width, world_height);
        console.log(this.x, this.y);
        this.blocks = [];
        for(let i=0; i<length; i++){
            this.blocks.push(new Block(this.x,this.y,world_width, world_height));
        }
        if(Math.random()>0.5){
            this.dx = Math.round(Math.random())*2 - 1;
            this.dy = 0;
        }
        else {
            this.dx = 0;
            this.dy = Math.round(Math.random())*2-1;
        }
    }

    draw(context){
        for(let block of this.blocks){
            block.draw(context);
        }
    }

    move(){
        for(let i=this.blocks.length-1;i>0;i--){
            this.blocks[i].x=this.blocks[i-1].x;
            this.blocks[i].y=this.blocks[i-1].y;
        }
        this.blocks[0].x=this.blocks[0].x + this.blocks[0].width*this.dx;
        this.blocks[0].y=this.blocks[0].y + this.blocks[0].width*this.dy;
        this.x=this.blocks[0].x;
        this.y=this.blocks[0].y;
        console.log("current location x:"+this.x+" y:"+this.y);
    }

    setDirection(dx,dy){
        if(this.dx * dx !== -1 && this.dy * dy !== -1){
            this.dx = dx;
            this.dy = dy;
        }
    }

    grow(elements=5){
        for(let i=0;i<elements;i++){
            this.blocks.push(new Block(this.blocks[this.blocks.length-1].x, this.blocks[this.blocks.length-1].y, this.world_width, this.world_height));
        }
    }

    intersects_self(){
        for(let i=1;i<this.blocks.length;i++){
            if(this.blocks[0].x === this.blocks[i].x && this.blocks[0].y === this.blocks[i].y){
                return true;
            }
        }
        return false;
    }
}