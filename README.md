# 🌳 Árvore Binária de Busca — BST em Java

> **Disciplina:** Estrutura de Dados II  
> **Instituto Federal de Goiás — Câmpus Luziânia**  
> Implementação didática e interativa de uma Árvore Binária de Busca: 
> com menu em console, travessias recursivas e remoção com regra do Sucessor em Ordem.

---

## 📑 Sumário

- [Visão Geral](#visão-geral)
- [Conceitos Fundamentais](#conceitos-fundamentais)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Classe `ArvoreBinaria`](#classe-arvorebinaria)
  - [Classe interna `No`](#classe-interna-no)
  - [Método `arvoreVazia`](#método-arvorevazia)
  - [Método `insere`](#método-insere)
  - [Método `pesquisar`](#método-pesquisar)
  - [Método `localizar`](#método-localizar)
  - [Método `remover`](#método-remover)
  - [Método `minimo` e `maximo`](#método-minimo-e-maximo)
  - [Método `altura`](#método-altura)
  - [Método `totalNos`](#método-totalNos)
  - [Travessias](#travessias)
  - [Método `ehEstritamenteBinaria`](#método-ehestritamentebinaria)
- [Classe `ArvoreBinariaInterativa`](#classe-Programaarvorebinaria)
  - [Fase 1 — Construção inicial](#fase-1--construção-inicial)
  - [Fase 2 — Menu interativo](#fase-2--menu-interativo)
- [Os Três Casos de Remoção](#os-três-casos-de-remoção)
- [Travessias em Detalhe](#travessias-em-detalhe)
- [Como Executar](#como-executar)
- [Exemplo de Uso](#exemplo-de-uso)
- [Referências](#referências)

---

## Visão Geral

Este projeto materializa o aprendizado de **Estrutura de Dados II** por meio da implementação completa de uma **Árvore Binária de Busca (BST — Binary Search Tree)** em Java puro.

A BST combina o melhor de dois mundos:

| Estrutura | Vantagem |
|-----------|----------|
| Vetor ordenado | Busca rápida |
| Lista encadeada | Inserção e remoção rápidas |
| **Árvore Binária** | **As duas ao mesmo tempo** ✓ |

O projeto é dividido em dois arquivos independentes:

| Arquivo | Propósito |
|---------|-----------|
| `ArvoreBinaria.java` | Implementação pura das estruturas e algoritmos |
| `ArvoreBinariaInterativa.java` | Versão interativa com menu em console para o usuário construir e manipular sua própria árvore |

---

## Conceitos Fundamentais

```
              41          ← raiz
            /    \
          27      81      ← nós internos
         /  \    /  \
        07  38  67  84    ← nós internos
       / \   \  /  /  \
      02 11  33 62 82  92 ← nós folha (sem filhos)
```

### Propriedade BST

Para qualquer nó **x** da árvore:

- Todo nó **y** na **sub-árvore esquerda** satisfaz: `y.chave ≤ x.chave`
- Todo nó **y** na **sub-árvore direita** satisfaz: `y.chave > x.chave`

### Terminologia

| Termo | Definição |
|-------|-----------|
| **Raiz** | Nó topo da árvore, sem pai |
| **Folha** | Nó sem filhos (filhos `null`) |
| **SAE** | Sub-Árvore Esquerda |
| **SAD** | Sub-Árvore Direita |
| **Altura** | Comprimento do caminho mais longo da raiz a uma folha |
| **Sucessor** | Menor nó da sub-árvore direita de um nó dado |

---

## Estrutura do Projeto

```
📦 arvore-binaria/
 ┣ 📄 ArvoreBinaria.java           → implementação estática com main de demonstração
 ┗ 📄 ProgramaArvoreBinaria.java → programa interativo com menu para o usuário
```

---

## Classe `ArvoreBinaria`

Implementação orientada a objetos com **classe interna estática `No`** e **classe envolvente `ArvoreBinaria`** que gerencia a raiz e expõe todos os métodos públicos.

---

### Classe interna `No`

```java
static class No {
    int     valor;   // chave / dado armazenado
    No      sae;     // ponteiro para sub-árvore esquerda
    No      sad;     // ponteiro para sub-árvore direita

    No(int valor) { this.valor = valor; }
}
```

Cada nó é um **TAD (Tipo Abstrato de Dados)** com três atributos:

| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `valor` | `int` | A chave numérica do nó |
| `sae` | `No` | Referência ao filho esquerdo (`null` se ausente) |
| `sad` | `No` | Referência ao filho direito (`null` se ausente) |

---

### Método `arvoreVazia`

```java
public boolean arvoreVazia() {
    return raiz == null;
}
```

Verifica se a árvore está completamente vazia checando se a **raiz é `null`**.  
Retorna `true` se não há nenhum nó, `false` caso contrário.

---

### Método `insere`

```java
public void insere(int valor) {
    raiz = inserirRec(raiz, valor);
}

private No inserirRec(No no, int valor) {
    if (no == null) return new No(valor);   // posição encontrada

    if (valor <= no.valor)
        no.sae = inserirRec(no.sae, valor); // vai para esquerda
    else
        no.sad = inserirRec(no.sad, valor); // vai para direita

    return no;
}
```

**Funcionamento passo a passo:**

1. Começa na raiz
2. Compara o novo valor com o nó atual
3. Se `valor ≤ nó atual` → desce pela **esquerda**
4. Se `valor > nó atual` → desce pela **direita**
5. Repete até encontrar uma posição `null`
6. Cria o novo nó naquela posição

**Exemplo** — inserindo `45` na árvore:

```
60                    60
 \          →          \
  40                   40
 /  \                 /  \
30   50              30   50
                         /
                        45   ← novo nó
```

---

### Método `pesquisar`

```java
public boolean pesquisar(int valor) {
    return pesquisarRec(raiz, valor);
}

private boolean pesquisarRec(No no, int valor) {
    if (no == null)        return false;  // não encontrou
    if (valor == no.valor) return true;   // encontrou!
    if (valor < no.valor)  return pesquisarRec(no.sae, valor);
    return                        pesquisarRec(no.sad, valor);
}
```

Percorre a árvore comparando a chave buscada com cada nó:

- `valor == nó` → **encontrado** ✓
- `valor < nó` → busca na **esquerda**
- `valor > nó` → busca na **direita**
- `nó == null` → **não encontrado** ✗

Complexidade média: **O(log n)** em árvores balanceadas.

---

### Método `localizar`

```java
public No localizar(int valor) {
    return localizarRec(raiz, valor);
}
```

Variante de `pesquisar` que retorna a **referência ao nó** encontrado (ou `null`).  
Útil internamente para operações que precisam inspecionar o nó além de confirmar sua existência.

---

### Método `remover`

O método de remoção é o mais complexo da BST. Há **três casos** distintos:

```java
public boolean remover(int valor) {
    if (!pesquisar(valor)) return false;
    raiz = removerRec(raiz, valor);
    return true;
}
```

Veja a seção [Os Três Casos de Remoção](#os-três-casos-de-remoção) para o detalhamento completo.

---

### Método `minimo` e `maximo`

```java
public int minimo() {
    No cur = raiz;
    while (cur.sae != null) cur = cur.sae;  // sempre à esquerda
    return cur.valor;
}

public int maximo() {
    No cur = raiz;
    while (cur.sad != null) cur = cur.sad;  // sempre à direita
    return cur.valor;
}
```

| Operação | Direção de caminhamento | Motivo |
|----------|-------------------------|--------|
| `minimo()` | sempre à **esquerda** | menores valores ficam à esquerda |
| `maximo()` | sempre à **direita** | maiores valores ficam à direita |

---

### Método `altura`

```java
public int altura() {
    return alturaRec(raiz);
}

private int alturaRec(No no) {
    if (no == null) return -1;              // árvore vazia = -1
    return 1 + Math.max(alturaRec(no.sae), alturaRec(no.sad));
}
```

| Situação | Altura |
|----------|--------|
| Árvore vazia | `-1` |
| Apenas raiz (folha) | `0` |
| Árvore com h níveis | `h - 1` |

---

### Método `totalNos`

```java
private int totalRec(No no) {
    if (no == null) return 0;
    return 1 + totalRec(no.sae) + totalRec(no.sad);
}
```

Percorre **todos os nós** recursivamente e os conta.  
Em uma Árvore Binária Completa (ABC) de altura **h**, o total segue a fórmula:

```
N = 2^h - 1
```

---

### Travessias

Três formas de percorrer todos os nós. A diferença está **no momento em que o nó é visitado**:

```java
// PRÉ-ORDEM:  visita → esquerda → direita
private void preOrdemRec(No no) {
    if (no == null) return;
    System.out.print(no.valor + " ");  // ← visita ANTES
    preOrdemRec(no.sae);
    preOrdemRec(no.sad);
}

// EM-ORDEM:  esquerda → visita → direita
private void inOrdemRec(No no) {
    if (no == null) return;
    inOrdemRec(no.sae);
    System.out.print(no.valor + " ");  // ← visita NO MEIO
    inOrdemRec(no.sad);
}

// PÓS-ORDEM:  esquerda → direita → visita
private void posOrdemRec(No no) {
    if (no == null) return;
    posOrdemRec(no.sae);
    posOrdemRec(no.sad);
    System.out.print(no.valor + " "); // ← visita DEPOIS
}
```

Veja a seção [Travessias em Detalhe](#travessias-em-detalhe) para exemplos completos.

---

### Método `ehEstritamenteBinaria`

```java
private boolean estritaRec(No no) {
    if (no == null) return true;
    boolean temEsq = (no.sae != null);
    boolean temDir = (no.sad != null);
    if (temEsq != temDir) return false;  // exatamente 1 filho → viola
    return estritaRec(no.sae) && estritaRec(no.sad);
}
```

Uma árvore é **estritamente binária** quando **todo nó tem 0 ou 2 filhos** — nunca exatamente 1.  
Nós internos sempre possuem dois filhos; nós folha nunca possuem nenhum.

| Árvore | Estritamente binária? |
|--------|-----------------------|
| Todo nó tem 0 ou 2 filhos | ✅ Sim |
| Algum nó tem exatamente 1 filho | ❌ Não |

---

## Classe `ProgramaArvoreBinaria`

Programa completo em console que permite ao usuário **construir sua própria árvore do zero** e executar todas as operações via menu.

### Fase 1 — Construção inicial

```
===========================================
  Bem-vindo ao programa de Árvore Binária!
===========================================
  Vamos construir sua árvore do ZERO.
  Digite os valores iniciais um por vez.
  Para finalizar a inserção inicial, digite 0 (zero).
-------------------------------------------
  Valor (0 para terminar inserção): 41
  ✓ 41 inserido na árvore.
  Valor (0 para terminar inserção): 27
  ✓ 27 inserido na árvore.
  ...
```

Ao digitar `0`, a fase de construção encerra e o programa exibe automaticamente o **resumo** e as **três travessias** da árvore recém-criada.

---

### Fase 2 — Menu interativo

```
===========================================
  ÁRVORE BINÁRIA DE BUSCA – IFG Luziânia
===========================================
  [1] Inserir número
  [2] Remover número
  [3] Pesquisar / Localizar número
  [4] Listar em Pré-ordem
  [5] Listar em Em-ordem (crescente)
  [6] Listar em Pós-ordem
  [7] Listar nas 3 ordens
  [8] Mínimo e Máximo
  [9] Resumo da árvore
  [0] Sair
===========================================
```

| Opção | Operação | Complexidade média |
|-------|----------|--------------------|
| `[1]` | Inserir um número | O(log n) |
| `[2]` | Remover um número (3 casos + sucessor) | O(log n) |
| `[3]` | Pesquisar / Localizar | O(log n) |
| `[4]` | Pré-ordem | O(n) |
| `[5]` | Em-ordem (resultado crescente) | O(n) |
| `[6]` | Pós-ordem | O(n) |
| `[7]` | Todas as 3 travessias | O(n) |
| `[8]` | Mínimo e Máximo | O(h) |
| `[9]` | Resumo completo | O(n) |
| `[0]` | Encerrar | — |

---

## Os Três Casos de Remoção

### Caso 1 — Nó folha (sem filhos)

O nó é simplesmente desconectado. O ponteiro do pai passa a apontar para `null`.

```
Antes:          Depois:
  10              10
  /               /
 5               5     ← ponteiro do pai = null
/ \
3   7
        remove(7)
```

### Caso 2 — Nó com um único filho

O pai do nó removido passa a apontar diretamente para o filho do nó removido, "cortando" o nó da cadeia.

```
Antes:          Depois:
  80              80
  /               /
 52              52
/  \            /  \
48  71          48  63
   /                 \
  63                 67
   \
   67
        remove(71)
```

### Caso 3 — Nó com dois filhos (Regra do Sucessor)

O nó não pode ser simplesmente removido. Em vez disso:

1. Encontra-se o **sucessor em ordem** do nó a remover
2. O sucessor é o **menor nó da sub-árvore direita** do nó a remover
3. O valor do nó é substituído pelo valor do sucessor
4. O sucessor é removido da sub-árvore direita (caso 1 ou 2)

```
Antes:          Depois:
     50              50
    /               /
   25              30   ← sucessor de 25 ocupa seu lugar
  /  \            /  \
 15  35          15  35
    /  \             \
   30  40            40
        remove(25) → sucessor = 30
```

**Como encontrar o sucessor:**

```
  38              → nó a remover
   ↓
  ir para sad (72)
   ↓
  ir para sae (55)
   ↓
  ir para sae (41)
   ↓
  sem sae → SUCESSOR = 41
```

O programa informa em tela qual caso foi aplicado a cada remoção:

```
  → Caso 1: nó folha removido diretamente.
  → Caso 2: nó com apenas filho à direita. Filho sobe.
  → Caso 3: dois filhos. Sucessor em ordem = 30
```

---

## Travessias em Detalhe

Usando a árvore do exercício:

```
              41
            /    \
          27      81
         /  \    /  \
        07  38  67  84
       / \   \  /  /  \
      02 11  33 62 82  92
```

| Travessia | Sequência | Característica |
|-----------|-----------|----------------|
| **Pré-ordem** | `41 27 07 02 11 38 33 81 67 62 84 82 92` | Raiz sempre primeiro |
| **Em-ordem** | `02 07 11 27 33 38 41 62 67 81 82 84 92` | Resultado em **ordem crescente** ✓ |
| **Pós-ordem** | `02 11 07 33 38 27 62 67 82 92 84 81 41` | Raiz sempre por último |

### Aplicações de cada travessia

| Travessia | Uso prático |
|-----------|-------------|
| Pré-ordem | Copiar / serializar a árvore; notação **pré-fixa** de expressões (`*A+BC`) |
| Em-ordem | Listar dados em **ordem crescente**; verificar se é BST válida |
| Pós-ordem | Deletar a árvore; avaliar expressões em notação **pós-fixa** (`ABC+*`) |

---

## Como Executar

### Pré-requisitos

- **Java JDK 8+** instalado
- Terminal / Prompt de Comando

### Compilar e executar

```bash
# Clonar ou baixar os arquivos
# Compilar
javac ArvoreBinaria.java
javac ArvoreBinariaInterativa.java

# Executar a versão de demonstração (pré-carregada)
java ArvoreBinaria

# Executar a versão interativa (você constrói a árvore)
java ArvoreBinariaInterativa
```

### Entrada sugerida para reproduzir o exercício

```
41 → 27 → 81 → 7 → 38 → 67 → 84 → 2 → 11 → 33 → 62 → 82 → 92 → 0
```

---

## Exemplo de Uso

```
===========================================
  Bem-vindo ao programa de Árvore Binária!
===========================================
  Valor (0 para terminar inserção): 50
  ✓ 50 inserido na árvore.
  Valor (0 para terminar inserção): 25
  ✓ 25 inserido na árvore.
  Valor (0 para terminar inserção): 75
  ✓ 75 inserido na árvore.
  Valor (0 para terminar inserção): 0

  Árvore construída! Visão geral:
  Total de nós : 3
  Altura        : 1
  Mínimo        : 25
  Máximo        : 75
  Estritamente binária? true

Pré-ordem  : 50 25 75
Em-ordem   : 25 50 75
Pós-ordem  : 25 75 50

  Escolha uma opção: 2
  Número a remover: 50
  Removendo 50...
  → Caso 3: dois filhos. Sucessor em ordem = 75
  ✓ 50 removido com sucesso.
  Em-ordem atual:
Em-ordem   : 25 75
```

---

## Referências

- CORMEN, Thomas H. et al. **Algoritmos — Teoria e Prática**. 3ª ed. Rio de Janeiro: Elsevier, 2012.
- GOODRICH, Michael T.; TAMASSIA, Roberto. **Estrutura de Dados e Algoritmos em Java**. 5ª ed. Porto Alegre: Bookman, 2013.
- EDELWEISS, Nina; GALANTE, Renata. **Estruturas de Dados**. Porto Alegre: Bookman, 2009.

---

<div align="center">

**Estrutura de Dados II — IFG Câmpus Luziânia**  
Implementado em Java · Disciplina de Estruturas de Dados  

</div>
