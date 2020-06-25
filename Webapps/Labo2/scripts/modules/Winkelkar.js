import Product from "./Product.js"

export default class Winkelkar {
    constructor(){
        this.mandje = new Map();
        this.mandje.set("tekst per mail", new Product("tekst per mail", 0.50));
        this.mandje.set("zeefdruk op papier", new Product("zeefdruk op papier",0.10));
        this.mandje.set("zeefdruk op papier, ingekaderd", new Product("zeefdruk op papier, ingekaderd", 40.0));
    }

    koop(naam){
        this.mandje.get(naam).koop();
    }

    toString(){
        let antwoord="";
        for (let prod of this.mandje.values()){
            if(prod.subTotaal() != 0){
                antwoord += prod.toString() + "\n";
            }
        }
        return antwoord + "\nTOT € "+ this.totaal();
    }

    totaal(){
        let totaal=0;
        for (let prod of this.mandje.values()){
            totaal += prod.subTotaal();
        }
        return totaal;
    }

    maakLeeg(){
        for (let prod of this.mandje.values()){
            prod.koopNiet();
        }
    }
}