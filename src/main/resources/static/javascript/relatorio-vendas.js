function formatarMoeda(valor) {
    return 'R$ ' + valor.toFixed(2).replace('.', ',').replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

function calcularSubtotalETotalGeral() {
    const linhasTabela = document.querySelectorAll('#tabela-vendas tr');
    let subtotalGeral = 0;
    let totalDescontos = 0;

    linhasTabela.forEach((linha) => {
        const quantidade = parseInt(linha.cells[3].textContent);
        const valorUnitario = parseFloat(linha.cells[4].textContent);
        const desconto = parseFloat(linha.cells[5].textContent);
    
        const subtotalItem = quantidade * valorUnitario; // Calcula o subtotal sem o desconto
        const subtotalComDesconto = subtotalItem - desconto; // Calcula o subtotal com desconto
    
        subtotalGeral += subtotalComDesconto; // Adiciona o subtotal com desconto ao subtotal geral
        totalDescontos += desconto; // Adiciona o desconto ao total de descontos
    
        linha.cells[4].textContent = formatarMoeda(valorUnitario); // Formata o valor unitário
        linha.cells[5].textContent = formatarMoeda(desconto); // Formata o desconto
        linha.cells[6].textContent = formatarMoeda(subtotalComDesconto); // Formata o subtotal com desconto
    });
    
    document.getElementById('total-descontos').textContent = formatarMoeda(totalDescontos); // Formata o total de descontos
    document.getElementById('total-geral').textContent = formatarMoeda(subtotalGeral); // Formata o total geral
}

// Chamar a função para calcular os subtotais e o total geral quando a página for carregada
window.onload = calcularSubtotalETotalGeral;
