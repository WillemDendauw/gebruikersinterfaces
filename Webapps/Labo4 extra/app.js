let som = 0;

function leesGetal(){
        return new Promise(function(resolve, reject){
            let getal = window.prompt("Geef een getal");
            if(isNaN(getal)){
                reject(getal+" is geen getal!");
            }
            else {
                resolve(getal);
            }

    });
}

function telBij(getal){
    return new Promise(function(resolve, reject) {
        som= +som+ +getal;
        if(som > 100){
            reject("De som is groter dan 100");
        }
        else {
            resolve(som);
        }
    });
}

function fout(bericht){
    window.alert(bericht)
}

function printSom(){
    window.alert("De som is = "+som);
}

leesGetal().then(telBij).then(leesGetal).then(telBij).then(printSom).catch(fout);