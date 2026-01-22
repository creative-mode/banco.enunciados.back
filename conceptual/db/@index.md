## Estudo de caso

O sistema proposto tem como objetivo centralizar e organizar a gestão de provas, questões, simulações e usuários de forma eficiente e segura. Ele permite que diferentes tipos de usuários interajam com os conteúdos e funcionalidades de acordo com os seus papéis, garantindo rastreabilidade e histórico completo de todas as operações.

No sistema, cada ano letivo é registrado com a sua data de início e fim, permitindo a associação de turmas e provas a períodos específicos. Cada período (term) é identificado por um número e nome, servindo para organizar as avaliações dentro do ano letivo. As disciplinas contêm código, nome e abreviação, sendo vinculadas às provas para definir o conteúdo avaliado. As cursos armazenam código, nome e descrição, permitindo a organização das turmas e a ligação com provas aplicáveis a grupos específicos.

As turmas são associadas a um curso e a um ano letivo, podendo ser identificadas por código e grau. Cada usuário do sistema possui uma conta de autenticação, incluindo nome de usuário, email, senha, verificação de email e status de atividade, e está associado a um perfil que armazena dados pessoais, como nome, sobrenome e foto. Usuários podem receber papéis (roles) que determinam as permissões de acesso e ações no sistema, com histórico de atribuição registrado na tabela de associação.

O sistema permite a criação de sessões para cada login de usuário, registrando token, endereço IP, datas de expiração e último uso, garantindo rastreabilidade e segurança.

As provas (statements) contêm informações como tipo de exame, duração, variante, título, instruções, pontuação máxima, visibilidade e o usuário que as criou. Cada prova pode estar associada a uma turma, curso, disciplina, ano letivo e período, possibilitando provas genéricas ou específicas.

As provas são compostas por questões, cada uma com número, enunciado, tipo (objetiva ou subjetiva), pontuação máxima e ordem de apresentação. Questões podem ter imagens associadas para ilustração e opções de resposta, cada opção podendo ser marcada como correta ou incorreta.

Os alunos realizam simulações, que registram o usuário, a prova aplicada, o ano letivo, datas de início e fim, tempo gasto, pontuação final e status da simulação. Cada simulação contém respostas, que podem indicar a opção selecionada ou o texto respondido, com registro da pontuação obtida, se a resposta estava correta e o momento em que foi respondida.

O sistema garante que uma prova possa ter várias questões, uma questão possa ter várias opções e imagens, e uma simulação possa conter várias respostas. Cada usuário pode realizar várias simulações, mas cada simulação pertence a apenas um usuário e a apenas uma prova. A pontuação total de cada prova é registrada para permitir validação e relatórios de desempenho.