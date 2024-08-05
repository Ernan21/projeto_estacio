# Manual de Instalação

### Programas necessários para o funcionamento do projeto
- [Visual Studio Code]
- [Java Development Kit]
- [DBeaver]

### Visual Studio Code
#### Passos para instalar o **Visual Studio Code**
1. Primeiro passo, baixe o instalador do [Visual Studio Code](https://code.visualstudio.com/Download) no seguinte link "<em>https://code.visualstudio.com/Download</em>"
2. Abra o arquivo que você acabou de baixar
3. Aceite o **acordo de licença** e clique em **Próximo**

    ![step1](IMG/docs_vscode/step%201.png)

4. Clique em **Próximo** e logo após clique em **Instalar**

    ![step2](IMG/docs_vscode/step%202.png)

    ![step3](IMG/docs_vscode/step%203.png)

5. Por fim, só clique em **Concluir**

    ![step4](IMG/docs_vscode/step%204.png)

6. Esta é a tela inicial do Visual Studio Code.

    ![step5](IMG/docs_vscode/step%205.png)

7. Agora, vamos baixar as [extensões](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) do Java para o Visual Studio Code. Utilize o link "<em>https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack</em>" para o site com o pack de extensões
8. Clique em ![btn_install](IMG/docs_vscode/btn_install.png). Se a seguinte mensagem aparecer:

    ![alert_msg](IMG/docs_vscode/alert_msg.png)

    Basta clicar em continuar.

    ![question_open_vscode](IMG/docs_vscode/question_open_vscode.png)

    Abra com o Visual Studio Code.

    ![step9](IMG/docs_vscode/step%209.png)

9. Agora é só clicar em **Install** e aguardar.

### Java Development Kit
#### Passos para instalar o **Java Development Kit**
1. Primeiro passo, baixe o instalador do [Java Development Kit](https://www.oracle.com/br/java/technologies/downloads/#jdk22-windows) no seguinte link "<em>https://www.oracle.com/br/java/technologies/downloads/#jdk22-windows</em>"

    ![version_jdk](IMG/docs_JDK/version_jdk.png)

    Utilize a versão installer para os próximos passos.
2. Vá clicando em **Next** até que a instalação se inicie

    ![step1](IMG/docs_JDK/step1.png)

    ![step2](IMG/docs_JDK/step2.png)

3. Clique em **Close** para finalizar a instalação

    ![step3](IMG/docs_JDK/step3.png)

#### Verificação da instalação
4. Abra o Prompt de comando e coloque o seguinte código `java --version`. Se a seguinte tela aparecer, significa que o Java foi instalado corretamente

    ![step4](IMG/docs_JDK/step4.png)

### DBeaver Community
#### Passos para instalar o **DBeaver Community**
> Para este projeto, vamos utilizar o DBeaver para manipular o banco de dados.

1. Vamos baixar a versão portátil do [DBeaver](https://dbeaver.io/download/) no seguinte link "<em>https://dbeaver.io/download/</em>"
2. No site do DBeaver, clique em ![windows_zip_btn](IMG/docs_dbeaver/windows_zip_btn.png) para fazer o download do portátil.
3. Quando o download concluir, localize o diretório do arquivo e extraia-o.

    ![unzip file](IMG/docs_dbeaver/unzip%20file.png)

4. Abra a pasta que você acabou de extrair e localize o arquivo dbeaver e abra-o

    ![dbeaver.exe](IMG/docs_dbeaver/dbeaver.exe.png)

5. Clique em não

    ![step2](IMG/docs_dbeaver/step2.png)

6. Clique em Postgres e clique em Avançar

    ![step3](IMG/docs_dbeaver/step3.png)

7. Utilize os dados abaixo para preencher os campos de conexão e depois clique em concluir
    - **host:** aws-0-sa-east-1.pooler.supabase.com
    - **banco:** postgres
    - **usuário:** postgres.gwciqljtnkhyojnomxbm
    - **senha:** )?)PRzkRi~7KxKs

    ![database_login](IMG/docs_dbeaver/database_login.png)

8. Faça o download de todos os requisitos

    ![donwload_requisitos](IMG/docs_dbeaver/donwload_requisitos.png)

#### Teste de Conexão
9. Agora, vamos fazer um teste. Abra um novo console e digite `SELECT 1+1` e depois execute-o clicando em ![execute_img](IMG/docs_dbeaver/execute_img.png)

    ![console](IMG/docs_dbeaver/console.png)

10. Se você receber um resultado igual ao da imagem abaixo, está tudo nos conformes

    ![result](IMG/docs_dbeaver/result.png)

---

### Informações Adicionais
- Arquivo `.properties` que formata locais de usabilidade do escopo
- Repositório onde pode ser alterado o caminho do projeto, fazendo com que seja renderizada outra tela
- Alteração no HTML
- Mudança de escopo de busca em Java e JavaScript
