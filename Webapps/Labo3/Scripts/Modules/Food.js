import Drawable from "./Drawable.js";

export default class Food extends Drawable{
    constructor(world_width,world_height,image,score,width=20){
        super(Math.random()*world_width,Math.random()*world_height, world_width, world_height);
        this.img = new Image();
        this.img.onload = () => {
            this.loaded = true;
        };
        this.img.src = image;
        this.score = score;
        this.width = width;
    }

    draw(context){
        if(this.loaded){
            context.drawImage(this.img, this.x, this.y, this.width, this.height);
        }
    }

    reposition(){
        this.x = Math.random()*this.world_width;
        this.y = Math.random()*this.world_height;
    }

    intersects(x,y,w,h){
        return Math.max(this.x,x) < Math.min(this.x+this.width, x+w) && Math.max(this.y,y) < Math.min(this.y+this.width,y+h);
    }
}