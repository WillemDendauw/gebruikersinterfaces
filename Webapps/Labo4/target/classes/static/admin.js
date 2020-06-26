let saved_id;
let posts;
let rest_url = "./posts";

let formSubmit = (event) => {
    event.preventDefault();

    let title = document.getElementById("txtTitle").value;
    let content = document.getElementById("txtContent").value;

    if(saved_id === undefined){
        add(title, content);
    }else{
        update(title, content);
    }
};

let formReset = (event) => {
    saved_id = undefined;
};

let clearFields = () => {
    document.getElementById("txtTitle").value = "";
    document.getElementById("txtContent").value = "";
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

let add = (title, content) => {
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
            fail("Error adding blogpost")
        });
};

let update = (title, content) => {
    fetch(rest_url+"/"+saved_id, {
        method: 'put',
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"uuid": saved_id, "title": title, "content": content})}
    )
        .then(() => {
            success("updated blogpost");
            clearFields();
            refreshTable();
        })
        .catch(() => {
            fail("Error adding blogpost");
        })
};

let remove = (id) => {
    fetch(rest_url+"/"+id, {
        method: "delete"
    })
        .then(() => {
            success("deleted blogpost");
            refreshTable();
        })
        .catch(() => {
            fail("Error deleting blogpost");
        });
};

let refreshTable = () => {
    fetch("posts")
        .then(data => data.json())
        .then(data => {
            document.getElementsByTagName("tbody")[0].innerHTML = "";
            posts = data;

            for (let post of data) {
                let tr = document.createElement("tr");
                tr.setAttribute("data-id", post.uuid);

                let td_1 = document.createElement("td");
                td_1.innerText = post.title;

                let td_2 = document.createElement("td");
                let btn_delete = document.createElement("button");
                btn_delete.setAttribute("class", "btn ");
                btn_delete.setAttribute("onclick", "deleteHandler(this)");
                btn_delete.innerText = "delete"
                td_2.appendChild(btn_delete);

                let td_3 = document.createElement("td");
                let btn_edit = document.createElement("button");
                btn_edit.setAttribute("class", "btn ");
                btn_edit.setAttribute("onclick", "editHandler(this)");
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

let deleteHandler = (blog_post_delete_btn) => {
    let id = blog_post_delete_btn.parentElement.parentElement.dataset.id;
    remove(id);
};

let editHandler = (blog_post_edit_btn) => {
    saved_id = blog_post_edit_btn.parentElement.parentElement.dataset.id;

    let selected_post = getPostById(saved_id);
    document.getElementById("txtTitle").value = selected_post.title;
    document.getElementById("txtContent").value = selected_post.content;
};

let getPostById = (id) => {
    return posts.filter(p => p.uuid === id)[0];
};

function main() {
    document.getElementById("success_alert").hidden = true;
    document.getElementById("fail_alert").hidden = true;
    document.getElementById("frm").addEventListener("submit", formSubmit);
    document.getElementById("frm").addEventListener("reset", formReset);

    refreshTable();
}

main();
