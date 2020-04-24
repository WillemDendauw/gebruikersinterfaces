let rest_url = "/scores";
// haal dit uit commentaar als je de tip wil gebruiken
let eerste_kaart = null;
let tweede_kaart = null;
let dubbels_gevonden = 0;
let score = 0;  // verhoogt (+1) telkens dat een kaart wordt omgedraaid

let kaarten;
let dataJSON = () => {
    fetch("./tiles0.json")
        .then(data => data.json())
        .then(data => {
            kaarten = data;
        });
};

//creeren tabel bij opstart van spel
function createTable() {
    console.log("starten met creëeren van table");
    let table = document.getElementById("idTable");
    for(let i = 0; i<4;i++){
        let row = document.createElement("tr");
        for(let j=0; j<4; j++){
            let td = document.createElement("td");
            let img = document.createElement("img");
            img.setAttribute("src","./images/gedekt.png");
            img.setAttribute("class","kaart");
            img.setAttribute("id",""+ (kaarten[i][j]));
            img.setAttribute("onclick","clickHandler(this)");
            td.appendChild(img);
            row.appendChild(td);
        }
        table.appendChild(row);
    }
    console.log("Table gecreëerd");
}

//de verschillende buttonhandlers
let speelHandler = (speel_btn) => {
    console.log("Speelbutton ingedrukt");
    let verberg_btn = document.getElementById("idVerberg")
    verberg_btn.setAttribute("class","btn btn-primary");
    createTable();
};

let clickHandler = (image) =>{
    //hier worden de kaarten omgedraaid, volgens mij zit de bug van het niet terugdraaien hier ergens maar ik kan ze niet vinden
    score++;
    console.log("kaart geklikt");
    let id = image.id;
    if(tweede_kaart === null) {
        if(eerste_kaart === null){
            eerste_kaart = id;
            console.log("eerste id="+eerste_kaart);
            image.setAttribute("src","./images/img"+id+".png");
        }
        else if(eerste_kaart !== null) {
            tweede_kaart = id;
            console.log("tweede id="+tweede_kaart);
            image.setAttribute("src","./images/img"+id+".png");
            if(eerste_kaart === tweede_kaart){
                dubbels_gevonden++;
                console.log("er zijn "+dubbels_gevonden+" dubbels gevonden!");
                eerste_kaart = null;
                tweede_kaart = null;
                console.log("dubbel gevonden!");
            }
        }
    }
    if(dubbels_gevonden === 8){
        console.log("gewonnnen!!!!");
        let timer = setTimeout(gewonnen,2000);
    }
};

let verbergHandler = (verberg) => {
    if(eerste_kaart !== null){
        let kaart1 = document.getElementById(eerste_kaart);
        kaart1.setAttribute("src","./images/gedekt.png");
        console.log(eerste_kaart+" verborgen");
        eerste_kaart = null;
        if(tweede_kaart !== null){
            let kaart2 = document.getElementById(tweede_kaart);
            kaart2.setAttribute("src","./images/gedekt.png");
            console.log(tweede_kaart+" verborgen");
            tweede_kaart = null;
        }
    }
};

let scoreHandler = (button) => {
    updateScores();
};


//de functies die meer op het einde van het spel belangrijk zijn
function gewonnen(){
    //vervangen van de kaarten naar hoera
    console.log("gewonnen!");
    let images = document.getElementsByClassName("kaart");
    for(let i=0;i<images.length;i++){
        images[i].setAttribute("src","./images/hoera.png");
    }
    let div = document.getElementById("play_field");
    let image = document.createElement("img");
    image.setAttribute("src","./images/hoera.png");
    image.setAttribute("class","eind");
    div.appendChild(image);
    verwijderTabel();
    voegScoreToe();

}

function verwijderTabel(){
    let div = document.getElementById("play_field");
    let tabel = document.getElementById("idTable");
    div.removeChild(tabel);
}

//score naar server sturen
function voegScoreToe(){
    let naam = window.prompt("Wat is je naam");

    fetch(rest_url, {
        method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"name": naam, "score": score})
    })
        .then(() => {
            updateScores();
            console.log("succesvol naar server gestuurd");
        })
        .catch(() => {
            console.log("fout bij versturen");
        });
}

//ophalen van de scores van de server en ze aan html toevoegen
function updateScores() {
    let resultaat;
    fetch(rest_url)
        .then(data => data.json())
        .then(data => {
            for(let spelers of data){
                resultaat += ""+spelers.name+ " " + spelers.score + "\n";
            }
        });
    let scores = document.getElementById("idScoresPlainText");
    scores.innerText = resultaat;
}


function main(){
    dataJSON(); //kaart data uit json halen (zie vanboven)
    //alle handlers instellen voor de 3 knoppen
    let speel_btn = document.getElementById("idSpeelButton");
    speel_btn.setAttribute("onclick","speelHandler(this)");
    let verberg_btn = document.getElementById("idVerberg");
    verberg_btn.setAttribute("onclick","verbergHandler(this)");
    let score_btn = document.getElementById("idScoresButton");
    score_btn.setAttribute("onclick","scoreHandler(this)");
}

main();

