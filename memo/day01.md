### IoC(Inversion of Control)

### 스프링 컨테이너

### Bean

- `@Component`
- `@Mapper`
- `@Repository`
- `@Service`
- `@Controller`
- `@Configuration`
- `@Bean`

### DI (Dependency Injection)

### 3-Layer

### Spring MVC

### 컨트롤러

- `@RequestParam`
- `@RequestBody`
- `@ResponseBody`
- `@RestController`

### Transaction

### AOP

# JPA

## 객체와 페러다임 불일치 문제

1. 상속

- 일반적인 학생, 직장인 테이블 모델

  |   학생   |  직장인  |
  | :------: | :------: |
  |   번호   |   번호   |
  |   이름   |   이름   |
  | 생년월일 | 생년월일 |
  |   학년   |   경력   |
  |   등급   |   직급   |

Extends ER모델

- 슈퍼

  |   사람   |
  | :------: |
  |   번호   |
  |   이름   |
  | 생년월일 |

- 서브

  |    학생     |   직장인    |
  | :---------: | :---------: |
  | 번호(fk,pk) | 번호(fk,pk) |
  |    학년     |    경력     |
  |    등급     |    직급     |
