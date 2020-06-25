import Winkelkar from "./modules/Winkelkar.js"

function cijfersom(getal){
    let som = 0;
    while(getal != 0){
         som += getal % 10;
         getal = Math.floor(getal/10);
    }
    return som;
}

function controleerCode(codeIn) {
    let code = codeIn.replace(/\s/g, "");

    //de code (string) opsplitsen in aparte letters en toevoegen aan ee narray
    let codeInArray = [...code];

    //controleren of er alleen digits staan
    let allesDigits = codeInArray.every(c => "0123456789".includes(c));
    if (allesDigits === false) {
        return false;
    }

    //om de twee indices de cijfers met twee vermenigvuldigen ( en desgewenst cijfersom nemen);
    //keis die indices waarbij het voorlaatste cijfer zit
    //merk op: de functie cijfersom zorgt zelf voor interpretatie als int
    for (let i = codeInArray.length - 2; i>=0 ; i-=2){
        codeInArray[i]=cijfersom(2*codeInArray[i]);
    }

    //probleem: zijn de elementen nu eigenlijk getallen of strings ?!
    // als we onderstaande omzetting niet doen, zal het accumuleren van de elementen van de array
    //neerkomen op het samenplakken van strings - dat willen we niet
    let getallenInArray = codeInArray.map(x => parseInt(x));

    //alle getallen optellen, moet tienvoud zijn
    let som = getallenInArray.reduce((accumulator, item) => accumulator + item);

    return som % 10 === 0;
}

function voegToeAanWinkelkar(){
    if(document.getElementById("idElektronisch").checked){
        mijnWinkelkar.koop("tekst per mail");
    }
    if(document.getElementById("idZeefdruk").checked){
        mijnWinkelkar.koop("zeefdruk op papier");
    }
    if(document.getElementById("idIngekaderd").checked){
        mijnWinkelkar.koop("zeefdruk op papier, ingekaderd");
    }
    document.getElementById("idTextArea").textContent = mijnWinkelkar.toString();
}

function maakWinkelkarLeeg(){
    mijnWinkelkar.maakLeeg();
    document.getElementById("idTextArea").textContent = "geen aankopen";
}

function voerTestUitVoorSubmit() {
    if (document.getElementById("idBetaalkaart").checked) {
        let betaalkaartCode = prompt("Geef de code van uw betaalkaart in", "xxxx xxxx xxxx xxxx");
        if (!controleerCode(betaalkaartCode)) {
            alert("Deze code was niet juist; probeer opnieuw");
            return false;
        } else {
            alert("Met dank, uw bestelling wordt verwerkt");
            return true;
        }
    }
}

function toonMeerGegevens(){
    //eerst nagaan of het nieuwe element er al staat of niet
    let elt = document.getElementById("idNieuwElement");
    if(elt === null){
        console.log("ik toon meer");
        let hoofdelt = document.getElementById("idInputGroupGegevens");
        let nieuwelt = document.createElement("div");
        nieuwelt.setAttribute("class","input-group input-group-sm mb-3");
        nieuwelt.setAttribute("id","idNieuwElement");

        let div2 = document.createElement("div");
        div2.setAttribute("class","input-group-prepend");

        let span = document.createElement("span");
        span.setAttribute("class","input-group-text");
        span.textContent = "straat en nr";

        let input = document.createElement("input");
        input.setAttribute("class","form-control");
        input.setAttribute("type","text");
        input.setAttribute("id","idStraat");

        nieuwelt.appendChild(div2);
        div2.appendChild(span);
        nieuwelt.appendChild(input);

        hoofdelt.appendChild(nieuwelt);
    }
}

function toonMinderGegevens(){
    let elt = document.getElementById("idNieuwElement");
    if(elt != null){
        elt.remove();
    }
}

function controleerMeerGegevens(){
    if(document.getElementById("idIngekaderd").checked || document.getElementById("idZeefdruk").checked){
        toonMeerGegevens();
    }
    else {
        toonMinderGegevens();
    }
}

let mijnWinkelkar = new Winkelkar();

function go(){
    alert("script gevonden!");
    document.getElementById("idVoegToeAanWinkelkar").onclick = voegToeAanWinkelkar;
    document.getElementById("idMaakLeeg").onclick = maakWinkelkarLeeg;
    document.getElementById("idForm").onsubmit = voerTestUitVoorSubmit;
    document.getElementById("idIngekaderd").onclick = controleerMeerGegevens;
    document.getElementById("idZeefdruk").onclick = controleerMeerGegevens;
}

go();