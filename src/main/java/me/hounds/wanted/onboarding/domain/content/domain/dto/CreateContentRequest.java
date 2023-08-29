package me.hounds.wanted.onboarding.domain.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateContentRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String detail;

    private List<String> hashTags;

    public Content toEntity() {
        return Content.builder()
                .title(title)
                .detail(detail)
                .activated(true)
                .build();
    }
}
