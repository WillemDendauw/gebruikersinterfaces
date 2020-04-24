let saved_id;
let posts;
let rest_url = "/posts";

let formSubmit = (event) => {
    event.preventDefault();

    let title = document.getElementById("txtTitle").value;
    let content = document.getElementById("txtContent").value;

    if(saved_id === undefined){
        add(title,content);
    }else{
        //update(title,content); //to do (deel 2 van de opgave)
    }
};

let formReset = (event) => {
    saved_id = undefined;
};

let clearFields = () => {
    document.getElementById("txtTitle").value="";
    document.getElementById("txtContent").value="";
};
let success = (message) => {
    document.getElementById("success_alert").innerText = message;
    document.getElementById("success_alert").hidden = false;
    document.getElementById("fail_alert").hidden = true;
};
let fail = (message) => {
    document.getElementById("fail_alert").innerText = message;
    document.getElementById("success_alert").hidden = true;
    document.getElementById("fail_alert").hidden = false;
};

let add =(title, content) =>{
    fetch(rest_url, {
        method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"title": title, "content": content})
    })
        .then(() => {
            success("Added blogpost");
            clearFields();
            refreshTable();
        })
        .catch(() => {
            fail("Error adding blogpost");
        })
};

let refreshTable = () => {
    fetch("posts")
        .then(data => data.json())
        .then(data => {
            document.getElementsByTagName("tbody")[0].innerHTML = "";
            posts = data;

            for(let post of data){
                let tr = document.createElement("tr");
                tr.setAttribute("data-id", post.uuid);

                let td_1 = document.createElement("td");
                td_1.innerText = post.title;

                let td_2 = document.createElement("td");
                let btn_delete = doucment.createElement("button");
                btn_delete.setAttribute("class","btn");
                btn_delete.setAttribute("onclick","deleteHandler(this)");
                btn_delete.innerText = "delete";
                td_2.appendChild(btn_delete);

                let td_3 = document.createElement("td");
                let btn_edit = document.createElement("button");
                btn_edit.setAttribute("class","btn");
                btn_edit.setAttribute("onclick","editHandler(this)");
                btn_edit.innerText = "edit";
                td_3.appendChild(btn_edit);

                tr.appendChild(td_1);
                tr.appendChild(td_2);
                tr.appendChild(td_3);

                document.getElementsByTagName("tbody")[0].appendChild(tr);
            }
        });

    saved_id = undefined;
};


function main(){
    document.getElementById("success_alert").hidden = true;
    document.getElementById("fail_alert").hidden = true;
    document.getElementById("frm").addEventListener("submit", formSubmit);
    document.getElementById("frm").addEventListener("reset",formReset);

    refreshTable();
}

main();






















