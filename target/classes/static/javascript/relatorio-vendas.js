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
    
        const subtotalItem = quantidade * valorUnitario;
        const subtotalComDesconto = subtotalItem - desconto;
    
        subtotalGeral += subtotalComDesconto;
        totalDescontos += desconto;
    
        linha.cells[4].textContent = formatarMoeda(valorUnitario);
        linha.cells[5].textContent = formatarMoeda(desconto);
        linha.cells[6].textContent = formatarMoeda(subtotalComDesconto);
    });
    
    document.getElementById('total-descontos').textContent = formatarMoeda(totalDescontos);
    document.getElementById('total-geral').textContent = formatarMoeda(subtotalGeral);
}

window.onload = calcularSubtotalETotalGeral;
