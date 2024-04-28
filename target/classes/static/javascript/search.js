function search() {
    var searchTerm = document.getElementById("searchInput").value.trim();
    if (searchTerm === "") {
        document.getElementById("productTable").innerHTML = "";
        document.getElementById("messageArea").innerText = "Por favor, insira um termo de pesquisa.";
        document.getElementById("messageArea").style.display = "block";
        return; 
    }

    var url = "/search?term=" + searchTerm;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                document.getElementById("productTable").innerHTML = xhr.responseText;
                var tableRows = document.getElementById("productTable").getElementsByTagName("tr");
                if (tableRows.length === 1) { // Apenas o cabeçalho da tabela
                    document.getElementById("messageArea").innerText = "Nenhuma informação encontrada para o termo mencionado: \"" + searchTerm + "\"";
                    document.getElementById("messageArea").style.display = "block";
                } else {
                    document.getElementById("messageArea").style.display = "none";
                }
            } else {
                console.error('Erro ao realizar a solicitação.');
            }
        }
    };
    xhr.open("GET", url, true);
    xhr.send();
}
