flowchart TD
    %% Usuário / Frontend
    A[Aluno / Frontend] -->|1. Upload multipart/form-data| B[Backend Spring WebFlux<br>POST /statements/upload]

    %% Upload e confirmação síncrona
    B -->|2. Valida JWT/RBAC<br>Armazena temp imagens| C[Resposta 202 Accepted + uploadId<br>Síncrono rápido]
    C --> A

    %% Chamada OCR
    B -->|3. POST /ocr/extract<br>WebClient reativo + Resilience4j| D[OCR Service FastAPI<br>PaddleOCR-VL]

    %% OCR leve vs pesado
    D -->|4a. Prova pequena: processa imediato| E[JSON estruturado → Backend]
    D -->|4b. Prova grande: enfileira| F[Celery Worker GPU/CPU<br>Processa OCR pesado]
    F -->|5. Resultado pronto| E

    %% Persistência
    E -->|6. Mapeia JSON → Entidades<br>Valida confiança| G[PostgreSQL + R2DBC<br>Transacional síncrono]
    G -->|7. Commit OK| H[Publica evento<br>Kafka: StatementPersisted]

    %% Indexação AI assíncrona
    H -->|8. Consumer| I[AI-service Python + FastAPI + LangChain]
    I -->|9. Recupera Statement completo| G
    I -->|10. Gera embeddings<br>Sentence Transformers| J[PgVector / Vector DB]
    J -->|11. Indexação completa<br>Eventual consistente| I

    %% Fluxo do Chatbot
    A -->|12. Pergunta no chat<br>ex: @enunciado...| K[Backend WebFlux<br>POST /ai/chat]
    K -->|13. Valida JWT/RBAC<br>Rate limit Redis| L[AI-service]
    L -->|14. Parser referência @enunciado<br>Embed query| J
    L -->|15. Retrieval top-k<br>+ Prompt + Histórico| M[LLM: Grok / GPT / Gemini<br>Streaming]
    M -->|16. Resposta incremental| L
    L -->|17. Streaming SSE/WebSocket| K
    K -->|18. Resposta progressiva + exercícios + materiais| A

    %% Cache e otimização
    subgraph "Otimização"
        N[Redis Cache<br>Respostas frequentes + Rate Limit]
        L -.-> N
        K -.-> N
    end

    %% Legenda
    classDef sync fill:#d1e7dd,stroke:#0f5132
    classDef async fill:#fff3cd,stroke:#664d03
    classDef fila fill:#cfe2ff,stroke:#084298
    classDef evento fill:#f8d7da,stroke:#842029

    B:::sync
    C:::sync
    G:::sync
    K:::sync
    F:::fila
    H:::evento
    I:::async
    L:::sync
    M:::async