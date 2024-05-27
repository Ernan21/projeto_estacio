function submitForm() {
    var descricao = document.getElementById("descricao_completa").value;
    var messageArea = document.getElementById("messageArea");

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.exists) {
                    messageArea.innerText = "Produto já cadastrado!";
                    messageArea.classList.remove("success");
                    messageArea.classList.add("error");
                } else {
                    messageArea.innerText = "Produto cadastrado com sucesso!";
                    messageArea.classList.remove("error");
                    messageArea.classList.add("success");
                    document.getElementById("cadastroForm").submit();
                }
                messageArea.style.display = "block";
            } else {
                console.error('Erro ao realizar a solicitação.');
            }
        }
    };
    xhr.open("POST", "/check-description", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({ descricao: descricao }));

    return false;
}
