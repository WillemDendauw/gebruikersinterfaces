export default class Product {
    constructor(naam, prijs){
        this.naam = naam;
        this.prijs = prijs;
        this.aantal = 0;
    }

    koop() {
        this.aantal+=1;
    }

    toString(){
        return this.aantal + "x " + this.naam + " (" + (this.aantal * this.prijs) + ")";
    }

    subTotaal(){
        return this.aantal * this.prijs;
    }

    koopNiet(){
        this.aantal=0;
    }
}