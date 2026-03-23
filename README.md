# pointtester

Utilitario para manter a sessao ativa com baixa interferencia no usuario.

## Modos

- `prevent-idle` (padrao): usa API do Windows (`SetThreadExecutionState`) para evitar ocioso sem mover mouse.
- `micro-jiggle`: move 1 pixel e volta imediatamente (fallback visual minimo).
- `disabled`: nao executa nenhuma acao.

## Configuracao

Propriedades em `src/main/resources/application.properties`:

- `pointtester.mode=prevent-idle`
- `pointtester.interval-ms=20000`
- `pointtester.mouse-jiggle-pixels=1`

## Observacoes

- Em ambientes com politica corporativa (GPO), a protecao de tela pode prevalecer sobre apps de usuario.
- O modo `prevent-idle` e o menos intrusivo para quem esta usando a maquina.
