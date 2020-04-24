fetch("./posts.json")
    .then((response) => {
        return response.json();
    })
    .then((data) => {
        for(let posts of data){
            let main = document.getElementById("main");
            let post = document.createElement("div");
            post.setAttribute("class","card mb-4");
            let titeldiv = document.createElement("div");
            titeldiv.setAttribute("class","card-header");
            let titel = document.createTextNode(posts.title);
            titeldiv.appendChild(titel);
            post.appendChild(titeldiv);
            let postdiv = document.createElement("div");
            postdiv.setAttribute("class","card-body");
            let artikel = document.createElement("p");
            artikel.setAttribute("class","card-text");
            let tekst = document.createTextNode(posts.content);
            artikel.appendChild(tekst);
            postdiv.appendChild(artikel);
            post.appendChild(postdiv);
            main.appendChild(post);
        }
    });
