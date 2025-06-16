//package com.github.mymvcspring.config.converter;
//
//import com.github.mymvcspring.web.dto.member.MemberListResponse;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EnumValueConverter<T extends Enum<T>> implements Converter<String, T> {
//
//    private final Class<T> enumClass;
//
//    public EnumValueConverter(Class<T> enumClass) {
//        this.enumClass = enumClass;
//    }
//    @Override
//    public T convert(String value) {
//        if (value == null || value.isEmpty()) {
//            return null;
//        }
//        try {
//            return Enum.valueOf(enumClass, value.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Invalid value for enum " + enumClass.getSimpleName() + ": " + value);
//        }
//    }
//
////    public <T extends Enum<T>> T convertToEnum(String value, Class<T> enumClass) {
////        if (value == null || value.isEmpty()) {
////            return null;
////        }
////        try {
////            return Enum.valueOf(enumClass, value.toUpperCase());
////        } catch (IllegalArgumentException e) {
////            throw new IllegalArgumentException("Invalid value for enum " + enumClass.getSimpleName() + ": " + value);
////        }
////    }
//}
