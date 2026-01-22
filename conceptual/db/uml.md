%% UML Class Diagram - Kixi Backend (Entities + Controllers com DTOs claros)

classDiagram
    %% ENTITIES
    class SchoolYear {
        +Long id
        +int startYear
        +int endYear
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Term {
        +Long id
        +int number
        +String name
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Subject {
        +String code
        +String name
        +String shortName 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Course {
        +String code
        +String name
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Class {
        +String code
        +String grade
        +Long courseId
        +Long schoolYearId
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Account {
        +Long id
        +String username
        +String email
        +String passwordHash
        +Boolean emailVerified
        +Boolean active
        +Date lastLogin 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class User {
        +Long id
        +Long accountId
        +String firstName
        +String lastName
        +String photo 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Role {
        +Long id
        +String name
        +String description 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class AccountRole {
        +Long accountId
        +Long roleId
        +Date createdAt
        +Date deletedAt 
    }

    class Session {
        +Long id
        +Long accountId
        +String token
        +String ipAddress 
        +Date expiresAt
        +Date lastUsed 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Statement {
        +Long id
        +String examType
        +int durationMinutes
        +String variant 
        +String title
        +String instructions 
        +int totalMaxScore
        +Long schoolYearId
        +Long termId
        +Long subjectId
        +Long classId 
        +Long courseId 
        +Long createdBy
        +Boolean visible
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Question {
        +Long id
        +Long statementId
        +int number
        +String text
        +String questionType
        +int maxScore
        +int orderIndex 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class QuestionImage {
        +Long id
        +Long questionId
        +String imageUrl
        +String caption 
        +int orderIndex 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class QuestionOption {
        +Long id
        +Long questionId
        +String optionLabel
        +String optionText
        +Boolean isCorrect
        +int orderIndex 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class Simulation {
        +Long id
        +Long accountId
        +Long statementId
        +Long schoolYearId 
        +Date startedAt
        +Date finishedAt 
        +int timeSpentSeconds 
        +float finalScore 
        +String status 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    class SimulationAnswer {
        +Long id
        +Long simulationId
        +Long questionId
        +Long selectedOptionId 
        +String answerText 
        +float scoreObtained 
        +Boolean isCorrect 
        +Date answeredAt 
        +Date createdAt
        +Date updatedAt
        +Date deletedAt 
    }

    %% RELATIONS
    Term --> SchoolYear : "belongsTo"
    Class --> Course : "belongsTo"
    Class --> SchoolYear : "belongsTo"
    User --> Account : "belongsTo"
    AccountRole --> Account : "accountId"
    AccountRole --> Role : "roleId"
    Session --> Account : "accountId"
    Statement --> SchoolYear : "schoolYearId"
    Statement --> Term : "termId"
    Statement --> Subject : "subjectId"
    Statement --> Class : "classId"
    Statement --> Course : "courseId"
    Question --> Statement : "statementId"
    QuestionImage --> Question : "questionId"
    QuestionOption --> Question : "questionId"
    Simulation --> Account : "accountId"
    Simulation --> Statement : "statementId"
    Simulation --> SchoolYear : "schoolYearId"
    SimulationAnswer --> Simulation : "simulationId"
    SimulationAnswer --> Question : "questionId"
    SimulationAnswer --> QuestionOption : "selectedOptionId"

    %% CONTROLLERS COM DTOs
    class SchoolYearController {
        +listAllActive() : Mono<ResponseEntity<List<SchoolYearResponse>>>
        +listTrashed() : Mono<ResponseEntity<List<SchoolYearResponse>>>
        +getById(id: Long) : Mono<ResponseEntity<SchoolYearResponse>>
        +create(request: SchoolYearRequest) : Mono<ResponseEntity<SchoolYearResponse>>
        +update(id: Long, request: SchoolYearRequest) : Mono<ResponseEntity<SchoolYearResponse>>
        +softDelete(id: Long) : Mono<ResponseEntity<Void>>
        +restore(id: Long) : Mono<ResponseEntity<Void>>
        +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
    }

    class TermController {
        +listAllActive() : Mono<ResponseEntity<List<TermResponse>>>
        +listTrashed() : Mono<ResponseEntity<List<TermResponse>>>
        +getById(id: Long) : Mono<ResponseEntity<TermResponse>>
        +create(request: TermRequest) : Mono<ResponseEntity<TermResponse>>
        +update(id: Long, request: TermRequest) : Mono<ResponseEntity<TermResponse>>
        +softDelete(id: Long) : Mono<ResponseEntity<Void>>
        +restore(id: Long) : Mono<ResponseEntity<Void>>
        +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
    }

    class SubjectController {
        +listAllActive() : Mono<ResponseEntity<List<SubjectResponse>>>
        +listTrashed() : Mono<ResponseEntity<List<SubjectResponse>>>
        +getById(id: Long) : Mono<ResponseEntity<SubjectResponse>>
        +create(request: SubjectRequest) : Mono<ResponseEntity<SubjectResponse>>
        +update(id: Long, request: SubjectRequest) : Mono<ResponseEntity<SubjectResponse>>
        +softDelete(id: Long) : Mono<ResponseEntity<Void>>
        +restore(id: Long) : Mono<ResponseEntity<Void>>
        +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
    }

    class CourseController {
        +listAllActive() : Mono<ResponseEntity<List<CourseResponse>>>
        +listTrashed() : Mono<ResponseEntity<List<CourseResponse>>>
        +getById(id: Long) : Mono<ResponseEntity<CourseResponse>>
        +create(request: CourseRequest) : Mono<ResponseEntity<CourseResponse>>
        +update(id: Long, request: CourseRequest) : Mono<ResponseEntity<CourseResponse>>
        +softDelete(id: Long) : Mono<ResponseEntity<Void>>
        +restore(id: Long) : Mono<ResponseEntity<Void>>
        +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
    }

    class ClassController {
        +listAllActive() : Mono<ResponseEntity<List<ClassResponse>>>
        +listTrashed() : Mono<ResponseEntity<List<ClassResponse>>>
        +getById(id: Long) : Mono<ResponseEntity<ClassResponse>>
        +create(request: ClassRequest) : Mono<ResponseEntity<ClassResponse>>
        +update(id: Long, request: ClassRequest) : Mono<ResponseEntity<ClassResponse>>
        +softDelete(id: Long) : Mono<ResponseEntity<Void>>
        +restore(id: Long) : Mono<ResponseEntity<Void>>
        +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
    }

    class AccountController {
        +listAllActive() : Mono<ResponseEntity<List<AccountResponse>>>
        +listTrashed() : Mono<ResponseEntity<List<AccountResponse>>>
        +getById(id: Long) : Mono<ResponseEntity<AccountResponse>>
        +create(request: AccountRequest) : Mono<ResponseEntity<AccountResponse>>
        +update(id: Long, request: AccountRequest) : Mono<ResponseEntity<AccountResponse>>
        +softDelete(id: Long) : Mono<ResponseEntity<Void>>
        +restore(id: Long) : Mono<ResponseEntity<Void>>
        +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
    }

    class UserController {
        +listAllActive() : Mono<ResponseEntity<List<UserResponse>>>
        +listTrashed() : Mono<ResponseEntity<List<UserResponse>>>
        +getById(id: Long) : Mono<ResponseEntity<UserResponse>>
        +create(request: UserRequest) : Mono<ResponseEntity<UserResponse>>
        +update(id: Long, request: UserRequest) : Mono<ResponseEntity<UserResponse>>
        +softDelete(id: Long) : Mono<ResponseEntity<Void>>
        +restore(id: Long) : Mono<ResponseEntity<Void>>
        +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
    }

   %% CONTROLLERS RESTANTES COM DTOs

class RoleController {
    +listAllActive() : Mono<ResponseEntity<List<RoleResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<RoleResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<RoleResponse>>
    +create(request: RoleRequest) : Mono<ResponseEntity<RoleResponse>>
    +update(id: Long, request: RoleRequest) : Mono<ResponseEntity<RoleResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

class AccountRoleController {
    +listAllActive() : Mono<ResponseEntity<List<AccountRoleResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<AccountRoleResponse>>>
    +getById(accountId: Long, roleId: Long) : Mono<ResponseEntity<AccountRoleResponse>>
    +create(request: AccountRoleRequest) : Mono<ResponseEntity<AccountRoleResponse>>
    +update(accountId: Long, roleId: Long, request: AccountRoleRequest) : Mono<ResponseEntity<AccountRoleResponse>>
    +softDelete(accountId: Long, roleId: Long) : Mono<ResponseEntity<Void>>
    +restore(accountId: Long, roleId: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(accountId: Long, roleId: Long) : Mono<ResponseEntity<Void>>
}

class SessionController {
    +listAllActive() : Mono<ResponseEntity<List<SessionResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<SessionResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<SessionResponse>>
    +create(request: SessionRequest) : Mono<ResponseEntity<SessionResponse>>
    +update(id: Long, request: SessionRequest) : Mono<ResponseEntity<SessionResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

class StatementController {
    +listAllActive() : Mono<ResponseEntity<List<StatementResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<StatementResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<StatementResponse>>
    +create(request: StatementRequest) : Mono<ResponseEntity<StatementResponse>>
    +update(id: Long, request: StatementRequest) : Mono<ResponseEntity<StatementResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

class QuestionController {
    +listAllActive() : Mono<ResponseEntity<List<QuestionResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<QuestionResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<QuestionResponse>>
    +create(request: QuestionRequest) : Mono<ResponseEntity<QuestionResponse>>
    +update(id: Long, request: QuestionRequest) : Mono<ResponseEntity<QuestionResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

class QuestionImageController {
    +listAllActive() : Mono<ResponseEntity<List<QuestionImageResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<QuestionImageResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<QuestionImageResponse>>
    +create(request: QuestionImageRequest) : Mono<ResponseEntity<QuestionImageResponse>>
    +update(id: Long, request: QuestionImageRequest) : Mono<ResponseEntity<QuestionImageResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

class QuestionOptionController {
    +listAllActive() : Mono<ResponseEntity<List<QuestionOptionResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<QuestionOptionResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<QuestionOptionResponse>>
    +create(request: QuestionOptionRequest) : Mono<ResponseEntity<QuestionOptionResponse>>
    +update(id: Long, request: QuestionOptionRequest) : Mono<ResponseEntity<QuestionOptionResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

class SimulationController {
    +listAllActive() : Mono<ResponseEntity<List<SimulationResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<SimulationResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<SimulationResponse>>
    +create(request: SimulationRequest) : Mono<ResponseEntity<SimulationResponse>>
    +update(id: Long, request: SimulationRequest) : Mono<ResponseEntity<SimulationResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

class SimulationAnswerController {
    +listAllActive() : Mono<ResponseEntity<List<SimulationAnswerResponse>>>
    +listTrashed() : Mono<ResponseEntity<List<SimulationAnswerResponse>>>
    +getById(id: Long) : Mono<ResponseEntity<SimulationAnswerResponse>>
    +create(request: SimulationAnswerRequest) : Mono<ResponseEntity<SimulationAnswerResponse>>
    +update(id: Long, request: SimulationAnswerRequest) : Mono<ResponseEntity<SimulationAnswerResponse>>
    +softDelete(id: Long) : Mono<ResponseEntity<Void>>
    +restore(id: Long) : Mono<ResponseEntity<Void>>
    +hardDelete(id: Long) : Mono<ResponseEntity<Void>>
}

