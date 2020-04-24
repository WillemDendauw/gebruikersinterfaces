import Drawable from "./Drawable.js";

export default class Block extends Drawable{
    constructor(x,y,world_width, world_height, size =20){
        super(x,y,world_width,world_height);
        this.width = size;
    }

    draw(context){
        context.fillRect(this.x,this.y,this.width,this.height);
    }
}