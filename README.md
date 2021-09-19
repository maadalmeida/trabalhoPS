# Sobre
TRABALHO PARA DISCIPLINA DE PROGRAMAÇÃO DE SISTEMAS - COMPUTAÇÃO - UFPEL - Integrantes do grupo: Thalía Longaray, Lucas Araujo, Cleber Berndsen, Mateus Almeida, Alejandro Alberoni, Rafael Martins e Joáz Silva - Entrega do primeiro trabalho: Projeto de um processador de macros para o sistema computacional hipotético Z808.


# Introdução

O trabalho descrito a seguir consiste em implementar um PROCESSADOR DE MACROS para o ASSEMBLY da MV Z808 - conforme apresentado no livro Tradução de programas – Da montagem a carga. Cristian Koliver.

## Funcionamento

Temos uma interface bastante intuitiva, iniciando a execução o usuário é convidado a inserir o arquivo que conterá a fonte para montagem(com a extensão.asm), quando esse arquivo for carregado, gerará um outro arquivo de saída, este mesmo será exibido na interface gráfica e também será salvo um arquivo de saída, este mostrará o nome que será salvo.
Temos também a mesma versão do programa sem a interface gráfica, esse irá ler o arquivo chamado entrada.asm e gerará um arquivo de saida.asm, não há interações nessa versão, então após a execução, o arquivo de saída já estará disponível no mesmo diretório do arquivo de entrada.asm

## Padrões de projeto e nomenclaturas
### Organização de arquivos no GitHub

Na entrada do projeto no github temos duas pastas, e o arquivo readme.
> **ProcessadorMacro:** é o projeto do trabalho, nele se encontra todo o código do projeto.

> **ProcessadorMacro - GUI:** Possui o mesmo projeto de **ProcessadorMacro** porém com interface gráfica

### Funções e métodos

A primeira palavra do método é iniciada com letra minúscula e as demais palavras, se existirem, terão sua primeira letra maiúscula, sendo que não há qualquer tipo de separação entre as palavra. Sua nomenclatura está em português e está bem descritiva, de modo que possa ser observado pelo seu cabeçalho o que a função faz, ou seja, utilizamos a nomenclatura camelCase. Ex: _modoEscrita_, _fazLeitura_, _pegaTalCoisa_.
