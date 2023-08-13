package me.hounds.wanted.onboarding.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomPageResponse<T> {

    private List<T> date = new ArrayList<>();
    private long totalPage;
    private int pageSize;
    private long totalElements;
    private int number;


    public static <T> CustomPageResponse<T> of(Page<T> response) {
        return new CustomPageResponse<>(
                response.getContent(),
                response.getTotalPages(),
                response.getSize(),
                response.getTotalElements(),
                response.getNumber());
    }
}
