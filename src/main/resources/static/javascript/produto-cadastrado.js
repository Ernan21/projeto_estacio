function submitForm() {
    var descricao = document.getElementById("descricao_completa").value;
    var estoque = parseFloat(document.getElementById("estoque").value);
    var custo_produto = parseFloat(document.getElementById("custo_produto").value);
    var preco_venda = parseFloat(document.getElementById("preco_venda").value);
    var messageArea = document.getElementById("messageArea");

    if (!(estoque > 0)) {
        showMessage("Por favor, insira uma quantidade maior que zero.", "error");
        return false;
    }
    if (!(custo_produto > 0)) {
        showMessage("Por favor, insira um custo do produto maior que zero.", "error");
        return false;
    }
    if (!(preco_venda > 0)) {
        showMessage("Por favor, insira um preço de venda maior que zero.", "error");
        return false;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.exists) {
                    showMessage("Produto já cadastrado!", "error");
                } else {
                    showMessage("Produto cadastrado com sucesso!", "success");
                    setTimeout(function() {
                        window.location.href = "/screen_cadastro";
                    }, 3000);
                }
            } else {
                console.error('Erro ao realizar a solicitação.');
            }
        }
    };
    xhr.open("POST", "/check-description", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({ descricao: descricao, estoque: estoque, custo_produto: custo_produto, preco_venda: preco_venda }));

    return false;
}

function showMessage(message, type) {
    var messageArea = document.getElementById("messageArea");
    messageArea.innerText = message;
    messageArea.classList.remove("success", "error");
    messageArea.classList.add(type);
    messageArea.style.display = "block";

    setTimeout(function() {
        messageArea.style.display = "none";
    }, 3000);
}
