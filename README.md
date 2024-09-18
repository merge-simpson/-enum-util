# Features

## 유니크 필드

`EnumUtil`은 `enum` 클래스의 어느 필드가 고유한지 확인하기 위해 다음 메서드 목록을 제공합니다.

### Getter 함수를 사용

이 메서드들은 getter 함수를 인자로 받아 사용하며, 다음 목록이 있습니다.

#### 열거 상수의 배열과 Getter 함수를 사용

이 메서드의 프로토타입은 다음과 같습니다.

```java
<E extends Enum<E>, V> boolean isFieldUnique(E[] enumConstants, Function<E, V> valueGetter);
```

이 함수의 일반적 용례는 다음과 같습니다.

```java
boolean result = EnumUtil.isFieldUnique(ExampleEnumClass.values(), ExampleEnumClass::getValue);
```

#### 클래스와 Getter 함수를 사용

이 메서드의 프로토타입은 다음과 같습니다.

```java
<E extends Enum<E>, V> boolean isFieldUnique(Class<E> enumClass, Function<E, V> valueGetter);
```

이 함수의 일반적 용례는 다음과 같습니다.

```java
boolean result = EnumUtil.isFieldUnique(ExampleEnumClass.class, ExampleEnumClass::getValue);
```

#### Getter 함수의 입출력

Getter 함수는 대표적으로 다음 타입들에 호환되는 것으로 합니다.

- `Supplier<V> getter`: 대상 열거타입의 메서드여야 합니다.  
  예를 들어, 다음 메서드가 대상 열거타입의 메서드라면 isFieldUnique 두 번째 메서드의 인자로 사용할 수 있습니다.
  ```java
  public int getValue() {
      return value;
  }
  ```
- `Function<E extends Enum<E>, V> getter`: 위 메서드의 표현 타입을 대신하는 타입입니다. `map` 함수에 사용할 수 있습니다.

### 예시: 고유한 상수 필드

**_예시: 상수 필드를 둔 열거 타입_**

다음 예시에서 상수 필드를 고유하게 관리하고 싶을 수 있습니다.

```java
public enum SampleStatus {
    PENDING(10),
    ACTIVE(20),
    REMOVED(0);
    
    private final int code;
    
    SampleStatus(int code) {
        this.code = code;
    }
    
    public int code() {
        return code;
    }
}
```

테스트 코드나 `-ea` 옵션이 있을 때 `assert` 키워드에서 [Getter 함수를 사용](#getter-함수를-사용)하는 방식으로 `code` 필드가 고유한지 확인합니다.

```java
public enum SampleStatus {
    PENDING(10),
    ACTIVE(20),
    REMOVED(0);
    
    private final int code;
    
    // enum 클래스는 독특하게도, 열거 상수의 생성자가 먼저 실행되고 static 블록을 실행합니다.
    static {
        assert EnumUtil.isFieldUnique(SampleStatus.values(), SampleStatus::code)
                : "SampleStatus의 모든 code 필드가 고유해야 합니다.";
    }
    
    SampleStatus(int code) {
        this.code = code;
    }
    
    public int code() {
        return code;
    }
}
```