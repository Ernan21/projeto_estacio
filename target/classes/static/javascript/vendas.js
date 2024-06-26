function atualizarTotal() {
    let subtotal = 0;
    const linhasTabela = document.querySelectorAll('#corpo tr');
    linhasTabela.forEach((linha) => {
        const quantidade = parseInt(linha.cells[2].querySelector('input').value);
        const valorUnitario = parseFloat(linha.cells[3].textContent);
        const subtotalItem = quantidade * valorUnitario;
        linha.cells[4].textContent = subtotalItem.toFixed(2);
        subtotal += subtotalItem;
    });
    document.getElementById('subtotal').textContent = subtotal.toFixed(2);
    const desconto = parseFloat(document.getElementById('desconto').value || 0);
    const total = subtotal - desconto;
    document.getElementById('totalValor').textContent = total.toFixed(2);
}

function confirmarFinalizarVenda() {
    const linhasTabela = document.querySelectorAll('#corpo tr');
    let hasInvalidQuantities = false;

    linhasTabela.forEach((linha) => {
        const quantidade = linha.cells[2].querySelector('input').value.trim();
        if (!/^\d+$/.test(quantidade) || parseInt(quantidade) <= 0) {
            alert('Por favor, insira uma quantidade que não seja zero e seja um valor inteiro positivo.');
            hasInvalidQuantities = true;
            return;
        }
    });

    if (hasInvalidQuantities) {
        return;
    }

    const desconto = parseFloat(document.getElementById('desconto').value || 0);
    if (desconto < 0 || desconto !== desconto) {
        alert('Por favor, insira um desconto válido.');
        return;
    }

    let subtotal = 0;
    linhasTabela.forEach((linha) => {
        const valorUnitario = parseFloat(linha.cells[3].textContent);
        const quantidade = parseInt(linha.cells[2].querySelector('input').value);
        subtotal += valorUnitario * quantidade;
    });

    if (desconto > subtotal) {
        alert('O desconto não pode ser maior que o total dos produtos.');
        return;
    }

    if (confirm('Tem certeza que deseja finalizar a venda?')) {
        const idsProdutos = [];
        const quantidades = [];
        const valoresUnitarios = [];

        linhasTabela.forEach((linha) => {
            idsProdutos.push(linha.cells[0].textContent);
            quantidades.push(linha.cells[2].querySelector('input').value);
            valoresUnitarios.push(parseFloat(linha.cells[3].textContent));
        });

        fetch('/finalizar_venda', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'id_produtos': idsProdutos,
                'quantidades': quantidades,
                'valores_unitarios': valoresUnitarios,
                'desconto': desconto
            })
        })
        .then(response => {
            if (response.ok) {
                alert('Venda finalizada com sucesso!');
                window.location.href = "/screen_vendas";
            } else {
                throw new Error('Erro ao finalizar a venda');
            }
        })
        .catch(error => console.error('Erro ao finalizar a venda:', error));
    }
}

function confirmarCancelarVenda() {
    if (confirm('Tem certeza que deseja cancelar a venda?')) {
        window.location.href = "/screen_vendas";
    }
}

function incrementarQuantidade(button) {
    const inputQuantidade = button.parentElement.querySelector('input[type="number"]');
    inputQuantidade.value = parseInt(inputQuantidade.value) + 1;
    atualizarTotal();
}

function decrementarQuantidade(button) {
    const inputQuantidade = button.parentElement.querySelector('input[type="number"]');
    if (parseInt(inputQuantidade.value) > 1) {
        inputQuantidade.value = parseInt(inputQuantidade.value) - 1;
    }
    atualizarTotal();
}

const produtosAdicionados = new Set();

function selecionarProduto(id, descricao, valor) {
    if (produtosAdicionados.has(id)) {
        alert('Este produto já foi adicionado.');
        return;
    }

    const corpoTabela = document.getElementById('corpo');
    const newRow = corpoTabela.insertRow();
    newRow.setAttribute('data-id', id);
    newRow.innerHTML = `
        <td>${id}</td>
        <td>${descricao}</td>
        <td><input type="number" class="form-control" value="1" onchange="atualizarTotal()"></td>
        <td>${valor.toFixed(2)}</td>
        <td>${valor.toFixed(2)}</td>
        <td><button class="btn btn-danger" onclick="removerProduto(this, ${id})">Excluir</button></td>
    `;
    atualizarTotal();
    produtosAdicionados.add(id);

    const modalElement = document.getElementById('searchModal');
    const modalInstance = bootstrap.Modal.getInstance(modalElement);
    modalInstance.hide();
    document.getElementById('searchTerm').value = '';
}

function removerProduto(button, id) {
    const row = button.closest('tr');
    produtosAdicionados.delete(id);
    row.remove();
    atualizarTotal();
}

function verificarEnter(event) {
    if (event.key === 'Enter') {
        buscarProdutoPorCodigo();
    }
}

function buscarProdutoPorCodigo() {
    const codigoInput = document.getElementById('codigo');
    const codigo = codigoInput.value.trim();
    if (codigo) {
        fetch(`/produtos?codigo=${codigo}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    const produto = data[0];
                    selecionarProduto(produto.id, produto.descricao_completa, produto.preco_venda);
                } else {
                    alert('Produto não encontrado');
                }
            })
            .catch(error => console.error('Erro ao buscar produto por código:', error))
            .finally(() => {
                codigoInput.value = '';
            });
    } else {
        alert('Por favor, digite um código de produto.');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const searchModal = document.getElementById('searchModal');
    searchModal.addEventListener('shown.bs.modal', function() {
        const searchTermInput = document.getElementById('searchTerm');
        const term = searchTermInput.value.trim();
        if (term !== '') {
            carregarProdutos(term);
        } else {
            const produtoTableBody = document.getElementById('produtoTableBody');
            produtoTableBody.innerHTML = '';
        }
    });

    const searchTermInput = document.getElementById('searchTerm');
    searchTermInput.addEventListener('input', function() {
        const term = this.value.trim();
        if (term !== '') {
            carregarProdutos(term);
        } else {
            const produtoTableBody = document.getElementById('produtoTableBody');
            produtoTableBody.innerHTML = '';
        }
    });
});

function carregarProdutos(term = '') {
    const produtoTableBody = document.getElementById('produtoTableBody');
    fetch(`/produtos?descricao=${term}`)
        .then(response => response.json())
        .then(data => {
            produtoTableBody.innerHTML = '';
            data.forEach(produto => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${produto.id}</td>
                    <td>${produto.descricao_completa}</td>
                    <td>${produto.preco_venda.toFixed(2)}</td>
                    <td><button class="btn btn-primary" onclick="selecionarProduto(${produto.id}, '${produto.descricao_completa}', ${produto.preco_venda})">Selecionar</button></td>
                `;
                produtoTableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Erro ao carregar produtos:', error));
}
