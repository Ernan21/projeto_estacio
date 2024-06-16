function submitForm() {
    var descricao = document.getElementById("descricao_completa").value;
    var estoque = document.getElementById("estoque").value;
    var custoProduto = document.getElementById("custo_produto").value;
    var precoVenda = document.getElementById("preco_venda").value;
    var messageArea = document.getElementById("messageArea");

    fetch("/check-description", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ descricao: descricao }),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao verificar a descrição do produto. Status: ' + response.status);
        }
        return response.json();
    })
    .then(data => {
        if (data.exists) {
            messageArea.innerText = "Produto já cadastrado!";
            messageArea.classList.remove("success");
            messageArea.classList.add("error");
        } else {
            if (estoque <= 0) {
                messageArea.innerText = "Quantidade de Estoque deve ser maior que zero.";
                messageArea.classList.remove("success");
                messageArea.classList.add("error");
                return;
            }

            if (custoProduto <= 0) {
                messageArea.innerText = "Custo do Produto deve ser maior que zero.";
                messageArea.classList.remove("success");
                messageArea.classList.add("error");
                return; 
            }

            if (precoVenda <= 0) {
                messageArea.innerText = "Preço de Venda deve ser maior que zero.";
                messageArea.classList.remove("success");
                messageArea.classList.add("error");
                return; 
            }

            messageArea.innerText = "Produto cadastrado com sucesso!";
            messageArea.classList.remove("error");
            messageArea.classList.add("success");
            messageArea.style.display = "block";

            setTimeout(() => {
                document.getElementById("cadastroForm").submit();
            }, 1500);
        }
        messageArea.style.display = "block";
    })
    .catch(error => {
        console.error('Erro ao realizar a solicitação:', error);
        messageArea.innerText = "Ocorreu um erro ao verificar a descrição do produto. Tente novamente mais tarde.";
        messageArea.classList.remove("success");
        messageArea.classList.add("error");
        messageArea.style.display = "block";
    });

    return false;
}
