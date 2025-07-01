package com.github.semiprojectshop.config.module.converter;


import com.github.semiprojectshop.repository.sihu.user.RolesEnum;
import jakarta.persistence.AttributeConverter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@jakarta.persistence.Converter// converter 가 이미 임포트 되어있기에 다른임포트문은 직접입력함
public abstract class MyConverter<T extends Enum<T> & MyEnumInterface> implements AttributeConverter<T, String>, Converter<String, T> {

    private final Map<String, T> valueToEnumMap;
    public MyConverter(Class<T> targetEnumClass) {
        
        this.valueToEnumMap = EnumSet.allOf(targetEnumClass).stream()
                .flatMap(enumValue -> Stream.of(
                        Map.entry(enumValue.getValue(), enumValue),
                        Map.entry(
                                targetEnumClass.equals(RolesEnum.class) && enumValue.name().startsWith("ROLE_")
                                        ? enumValue.name().substring(5)
                                        : enumValue.name(),
                                enumValue
                        )//두개의 맵을 평탄하게 맵으로 합침 하나는 getValue()로, 하나는 enum 이름으로
                ))
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey, Map.Entry::getValue
                )
        );
    }

    @Override
    public T convert(@NonNull String source) {
        T result = valueToEnumMap.get(source.toUpperCase());
        if(result!=null) return result;

        throw new IllegalArgumentException("No enum constant for value: " + source);
    }

    @Override//null 인 경우 jpa 가 호출 안함
    public String convertToDatabaseColumn(T myEnum) {
        return myEnum.getValue();
    }

    @Override
    public T convertToEntityAttribute(String myEnumName) {
        return myEnumName==null ? null : valueToEnumMap.get(myEnumName);
    }


}
