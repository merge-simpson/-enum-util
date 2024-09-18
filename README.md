# Jitpack 확인을 위해 생성한 프로젝트

Jitpack 게시 확인을 위해 생성한 레포지터리입니다.  
Enum 유틸을 올리는 김에 Jitpack에 게시해 보았습니다.

# Download

- repository: https://jitpack.io
- dependency
  - group: com.github.merge-simpson
  - artifact id: enum-util
  - version: 0.1.0

`build.gradle.kts` 예시

```kotlin
plugins {
    id("java")
}

group = "me.letsdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") } // << here
}

dependencies {
    implementation("com.github.merge-simpson:enum-util:0.1.0") // << here
}

tasks.test {
    useJUnitPlatform()
}
```

# Features

지금은 필드의 고유성을 확인하는 기능만 담아 두었습니다.

## 필드의 고유성 확인

`EnumUtil`은 `enum` 클래스의 어느 필드가 서로 다른 열거 상수 사이에 고유한지 확인하기 위해
다음 메서드 목록을 제공합니다.

### Getter 함수를 사용하는 필드의 고유성 확인

이 메서드들은 getter 함수를 인자로 받아 사용하며, 다음 목록이 있습니다.

```java
<E extends Enum<E>, V>
boolean isFieldUnique(E[] enumConstants, Function<E, V> valueGetter);
```

```java
<E extends Enum<E>, V>
boolean isFieldUnique(Class<E> enumClass, Function<E, V> valueGetter);
```

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

테스트 코드나 `-ea` 옵션이 있을 때 `assert` 키워드에서
[Getter 함수를 사용](#getter-함수를-사용하는-필드의-고유성-확인)하는 방식으로 `code` 필드가 고유한지 확인합니다.

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