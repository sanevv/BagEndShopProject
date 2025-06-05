package com.github.mymvcspring.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    @JsonAlias({"userid", "id"}) // JSON에서 userId로 매핑
    private String userId;
    @JsonAlias("pwd")
    private String password;
    private String email;

    @Setter(AccessLevel.NONE)
    private String phoneNumber;
    @JsonAlias({"postcode"}) // JSON에서 ph1 또는 phone1으로 매핑
    private String zipCode;
    private String address;
    @JsonAlias("detailaddress")
    private String addressDetail;
    @JsonAlias("extraaddress")
    private String addressReference;
    private String gender;
    @JsonAlias("birthday")
    private String birthDay;
    @Getter(AccessLevel.NONE)
    private String hp1;
    @Getter(AccessLevel.NONE)
    private String hp2;
    @Getter(AccessLevel.NONE)
    private String hp3;

    public void defaultSetting(){
        this.phoneNumber = String.format("%s-%s-%s", hp1, hp2, hp3);
        this.gender = this.gender.equals("1") ? "남자" : "여자";
    }
/*
주요 Jackson 어노테이션
1. @JsonAlias
역직렬화 시 여러 다른 JSON 필드 이름을 하나의 Java 필드에 매핑할 수 있게 합니다.
예: @JsonAlias({"firstName", "first_name"}) - "firstName"이나 "first_name"으로 오는 값을 모두 받을 수 있습니다.
2. @JsonProperty
Java 필드명과 다른 JSON 속성명을 매핑합니다.
예: @JsonProperty("first_name") private String firstName;직렬화시 사용
3. @JsonIgnore
직렬화/역직렬화 시 특정 필드를 무시하도록 합니다.
예: @JsonIgnore private String password;
4. @JsonValue
객체를 직렬화할 때 해당 메서드의 반환값을 사용합니다.
예: 객체를 직렬화할 때 전체 객체 대신 특정 메서드의 반환값만 사용합니다.
5. @JsonCreator
JSON에서 객체 생성 시 사용할 생성자나 팩토리 메서드를 지정합니다.
예: 복잡한 객체 생성 로직이 필요할 때 유용합니다.
6. @JsonGetter / @JsonSetter
직렬화/역직렬화 시 사용할 getter/setter 메서드를 지정합니다.
예: @JsonGetter("full_name") public String getFullName() {...}
7. @JsonInclude
값의 상태에 따라 직렬화 여부를 결정합니다.
예: @JsonInclude(Include.NON_NULL) - null 값은 JSON에 포함하지 않음
8. @JsonFormat
날짜/시간 형식 지정에 사용됩니다.
예: @JsonFormat(pattern = "yyyy-MM-dd")
9. @JsonRawValue
값을 JSON 파싱없이 그대로 출력합니다.
예: 이미 JSON 형태의 문자열을 가진 필드에 사용
10. @JsonDeserialize / @JsonSerialize
커스텀 직렬화/역직렬화 클래스를 지정합니다.
예: @JsonSerialize(using = CustomSerializer.class)
11. @JsonAutoDetect
필드 가시성(visibility) 설정을 지정합니다.
예: private 필드를 직렬화할 수 있도록 설정
12. @JsonTypeInfo, @JsonSubTypes, @JsonTypeName
다형성 타입 처리를 위해 사용됩니다.
예: 상속 계층 구조에서 객체의 타입 정보를 포함시킬 때 유용
* */
}
