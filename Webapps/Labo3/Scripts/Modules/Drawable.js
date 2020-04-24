export default class Drawable {
    constructor(x,y,world_width,world_height){
        this.x = x;
        this.y = y;
        this.world_width = world_width;
        this.world_height = world_height;
    }

    draw(context){
        throw "not implemented yet";
    }
}