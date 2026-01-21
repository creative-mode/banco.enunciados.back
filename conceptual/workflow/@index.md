# Handbook Detalhado de Workflow do Projeto (GitHub)

## Objetivo

Este handbook define o **workflow oficial** que todos os membros do projeto devem seguir ao trabalhar no GitHub. Ele garante:

* Organização do trabalho e rastreabilidade
* Qualidade do código
* Colaboração eficiente e transparente
* Histórico claro para futuras referências

O fluxo oficial é:

> **Issue → Branch → Commit → Pull Request (Code Review) → Merge**

O **GitHub Projects** será utilizado para gerir todo o trabalho (backlog, em progresso, em review, concluído), garantindo visibilidade total do progresso.

---

## Visão Geral do Workflow

O workflow completo envolve os seguintes elementos:

1. **Issue** – define o que precisa ser feito
2. **Branch** – ambiente isolado para trabalhar na Issue
3. **Commit** – unidade mínima de alteração de código
4. **Pull Request (PR)** – proposta de integração das mudanças
5. **Code Review** – revisão do código para qualidade e conformidade
6. **Merge** – integração final na branch principal

Cada etapa tem suas regras, responsabilidades e boas práticas, que detalharemos abaixo.

---

## Issues

### Definição

Uma **Issue** é uma tarefa ou problema a ser resolvido. Pode representar:

* Um bug a ser corrigido
* Uma nova funcionalidade
* Refatoração ou melhoria de código
* Documentação ou testes

### Responsabilidades

* As **Issues serão criadas exclusivamente pelo Project Manager, Abner Lourenço**
* Atribuir labels (`bug`, `feature`, `refactor`, etc.)
* Definir critérios de aceitação ou resultados esperados
* Associar a milestones ou épicos quando aplicável

### Boas práticas

* Uma Issue deve ter **um único objetivo**
* Evitar Issues muito grandes: se necessário, dividir em subtarefas
* Referenciar qualquer recurso relevante (documentação, designs, etc.)

---

## Branches

### Definição

A **Branch** é uma cópia isolada do código, permitindo trabalhar na Issue sem afetar a branch principal.

### Regras principais

* Cada Issue deve ter sua própria Branch
* Branch criada a partir da **branch principal** (`main` ou `develop`)
* Nome descritivo, seguindo padrão:

```
<tipo>/<issue-id>-<descricao-curta>
```

Exemplos:

* `feature/23-login-com-jwt`
* `bug/45-fix-null-pointer`
* `refactor/12-service-cleanup`

### Responsabilidades

* Garantir isolamento do código até que esteja pronto
* Manter branch atualizada com a principal para evitar conflitos
* Não commitar direto na branch principal

---

## Commits

### Definição

Um **commit** representa uma unidade de alteração de código, que deve ser lógica e coesa.

### Boas práticas

* Commits pequenos e frequentes
* Cada commit deve representar **uma mudança específica**
* Código deve compilar e passar testes (se existirem)
* Evitar commits genéricos ou grandes demais

### Mensagens de commit recomendadas (Conventional Commits simplificado)

```
<tipo>: descrição curta
```

Tipos comuns:

* `feat:` nova funcionalidade
* `fix:` correção de bug
* `refactor:` refatoração
* `docs:` documentação
* `test:` testes
* `chore:` tarefas internas

Exemplos:

* `feat: adicionar autenticação JWT`
* `fix: corrigir erro ao salvar usuário`

### Responsabilidades do commit

* Registrar mudanças de forma clara
* Associar cada commit à Issue quando possível (referenciando o número)
* Garantir consistência e legibilidade

---

## Pull Request (PR)

### Definição

Um **PR** é a proposta de integração da branch da Issue na branch principal.

### Quando criar um PR?

* Quando a Issue estiver concluída
* Ou quando precisar de feedback antecipado

### Regras obrigatórias

* PR deve estar **sempre ligado a uma Issue** (`Closes #n`)
* PR vai para a branch principal
* PR sem review **não pode ser mergeado**

### Conteúdo do PR

* Descrição clara do que foi feito
* Issue relacionada
* Screenshots ou logs (quando aplicável)
* Instruções de teste, se necessário

### Responsabilidades do autor

* Explicar claramente as mudanças
* Garantir que os commits estejam organizados
* Responder a comentários do Code Review

---

## Code Review

### Definição

O **Code Review** é a revisão do código por outros membros da equipe antes do merge.

### Objetivo

* Garantir qualidade e consistência do código
* Detectar bugs e problemas de design
* Melhorar legibilidade e manutenção
* Compartilhar conhecimento entre membros

### Regras

* Pelo menos **uma aprovação** necessária (ou mais, conforme definido)
* Comentários claros e respeitosos
* Autor do PR deve revisar e responder comentários
* Sugestões de melhoria são bem-vindas

---

## Merge

### Definição

O **Merge** é a integração final das mudanças aprovadas na branch principal.

### Regras e responsabilidades

* Só mergear após aprovação e checks automáticos passarem
* Resolver conflitos antes do merge
* Estratégias recomendadas:

  * **Squash and merge:** para histórico limpo
  * **Merge commit:** se quiser preservar histórico detalhado
* Nunca mergear sem revisão

### Pós-Merge

* Fechar automaticamente ou manualmente a Issue
* Mover card no Project para **Done**
* Apagar branch da Issue para manter repositório limpo

---

## Resumo das Regras de Ouro

* Sem Issue → sem código
* Uma Issue → uma Branch
* Commits claros, pequenos e frequentes
* PR obrigatório antes do merge
* Code Review necessário
* Nunca commitar direto na branch principal
* Pós-merge: fechar Issue, mover card, apagar branch

---

## Considerações Finais

Este workflow é **obrigatório para todos os membros** do projeto. Ele garante:

* Colaboração organizada e transparente
* Código de qualidade e rastreável
* Histórico claro para referência futura

Siga o fluxo, documente suas mudanças, e mantenha o repositório saudável.
