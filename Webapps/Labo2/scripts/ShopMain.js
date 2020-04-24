class Product{
    constructor(naam, prijs, aantal){
        this.naam = naam;
        this.prijs = prijs;
        this.aantal = aantal;
    }

    koop(){
        this.aantal++;
    }

    totaalPrijs(){
        return this.aantal * this.prijs;
    }

    informatie(){
        return this.naam;
    }
}

class Winkelkar {
    constructor() {
        this.map = new Map();
        this.map.set("tekst per mail",new Product("tekst per mail", 0.5,0));
        this.map.set("zeefdruk op papier",new Product("zeefdruk op papier", 10,0));
        this.map.set("zeefdruk op papier, ingekaderd",new Product("zeefdruk op papier, ingekaderd", 40,0));
    }

    koop(naam){
        let product = this.map.get(naam);
        product.koop();
        console.log("product ("+naam+") gekocht");
    }

    BerekenTotaal(){
        let prijs = 0;
        for(let value of this.map.values()){
            prijs += value.prijs;
        }
        console.log("totaalprijs berekend: "+prijs);
        return prijs;
    }

    geefInformatie(){
        let tekst = "";
        for(let keys of this.map.keys()){
            let product = this.map.get(keys);
            if(product.aantal != 0){
                tekst += product.aantal + "x " + keys +"\n";
            }
        }
        tekst += "\nTotaal: " + this.BerekenTotaal()
        console.log(tekst);
        return tekst;
    }

    resetWinkelkar(){
        for(let value of this.map.values()){
            value.aantal = 0;
        }
        let textarea = document.getElementById("idTextArea");
        textarea.value = "voorlopig nog niet besteld";
        console.log("winkelkar gereset");
    }
}

let winkelkar = new Winkelkar();

function go(){
    console.log("go!");
    let knop = document.getElementById("idVoegToeAanWinkelkar");
    knop.onclick = voegToeAanWinkelkar;
    let reset = document.getElementById("idMaakLeeg");
    reset.onclick = maakWinkelkarLeeg;
    document.getElementById("idForm").onsubmit = voerTestUitVoorSubmit;

    let zeef = document.getElementById("idZeefdruk");

    let kader = document.getElementById("idIngekaderd");
    zeef.onclick = voegVeldToe;
    kader.onclick = voegVeldToe;
//geen haakjes na voegToeAanWinkelkar want het is gewoon de naam van de functie en niet de functieoproep

}

function voegVeldToe(){
    console.log("veld toevoegen");
    let ouder = document.getElementById("idInputGroupGegevens");
    let kind1 = document.createElement("adres");
    //kind1.setAttribute("class","form-control");
    kind1.setAttribute("type","text");
    let kind2 = document.createElement("adres2");
    kind2.setAttribute("value","adres");
    ouder.appendChild(kind2);
    ouder.appendChild(kind1);
}

function voegToeAanWinkelkar(){
    let textarea = document.getElementById("idTextArea");
    if(document.getElementById("idElektronisch").checked){
        winkelkar.koop("tekst per mail");
    }
    if(document.getElementById("idZeefdruk").checked){
        winkelkar.koop("zeefdruk op papier")
    }
    if(document.getElementById("idIngekaderd").checked){
        winkelkar.koop("zeefdruk op papier, ingekaderd");
    }
    console.log("winkelkargeupdate");
    textarea.value = winkelkar.geefInformatie();
}

function maakWinkelkarLeeg(){
    winkelkar.resetWinkelkar();
}

function voerTestUitVoorSubmit() {
    if (document.getElementById("idBetaalkaart").checked) {
        let betaalkaartCode = prompt("Geef de code van uw betaalkaart in", "xxxx xxxx xxxx xxxx");
        if (betaalkaartCode === null || !controleerCode(betaalkaartCode)) {
            alert("Deze code was niet juist; probeer opnieuw");
            return false;
        } else {
            alert("Met dank, uw bestelling wordt verwerkt");
            return true;
        }
    }
}

function cijfersom(getal) {
    let som = 0;
    while (getal != 0) {
        som += getal % 10;
        getal = Math.floor(getal / 10);
    }
    return som;
}

function controleerCode(codeIn) {
    let code = codeIn.replace(/\s/g, "");

    // de code (string) opsplitsen in aparte letters en toevoegen aan een array
    let codeInArray = [...code];

    // controleren of er alleen digits staan
    let allesDigits = codeInArray.every(c => "0123456789".includes(c));
    if (allesDigits === false) {
        return false;
    }

    // om de twee indices de cijfers met twee vermenigvuldigen (en desgewenst cijfersom nemen);
    // kies die indices waarbij het voorlaatste cijfer zit
    // merk op: de functie 'cijfersom' zorgt zelf voor interpretatie als int
    for (let i = codeInArray.length - 2; i >= 0; i -= 2) {
        codeInArray[i] = cijfersom(2 * codeInArray[i]);
    }

    // probleem: zijn de elementen nu eigenlijk getallen of strings?!
    // als we onderstaande omzetting niet doen, zal het accumuleren van de elementen van de array
    // neerkomen op het samenplakken van strings - dat willen we niet
    let getallenInArray = codeInArray.map(x => parseInt(x));

    // alle getallen optellen, moet tienvoud zijn
    let som = getallenInArray.reduce((accumulator, item) => accumulator + item);

    return som % 10 === 0;
}

go();