// get the json data and log
/*fetch("posts.json")
    .then(data => data.json())
    .then(data => {
            console.log(data);
        }
    );*/

fetch("posts")
    .then(data => data.json())
    .then(data => {
        let main = document.getElementsByTagName("main")[0];

        for(let post of data){
            console.log(post);
            let card = document.createElement("div");
            card.setAttribute("class", "card mb-4");

            let card_header = document.createElement("div");
            card_header.setAttribute("class", "card-header");
            card_header.innerText = post.title;
            let card_body = document.createElement("div");
            card_body.setAttribute("class", "card-body");

            let card_body_text = document.createElement("p");
            card_body_text.setAttribute("class", "card-text");
            card_body_text.innerText = post.content;
            let card_body_btn = document.createElement("a");
            card_body_btn.setAttribute("class", "btn btn-primary");
            card_body_btn.setAttribute("href", "#");
            card_body_btn.innerText = "Read more";

            card_body.appendChild(card_body_text);
            card_body.appendChild(card_body_btn);
            card.appendChild(card_header);
            card.appendChild(card_body);

            main.appendChild(card);
        }
        }
    );

