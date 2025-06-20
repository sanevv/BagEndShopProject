package com.github.semiprojectshop.web.sihu.dto.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchConditions {
    private String searchValue;
    @Getter(AccessLevel.NONE)
    private String searchColumn;
    @Setter(AccessLevel.NONE)
    private SearchType searchType;


    public void covertSearchType(){
        if(searchColumn == null){
            this.searchType = SearchType.USER_ID;
            return;
        }
        this.searchType = switch (this.searchColumn){
            case "userName" -> SearchType.USER_NAME;
            case "email" -> SearchType.EMAIL;
            default -> SearchType.USER_ID;
        };
    }

    @Getter
    public enum SearchType {
        USER_ID("userId"),
        USER_NAME("userName"),
        EMAIL("email");

        private final String value;

        SearchType(String value) {
            this.value = value;
        }
    }

}
