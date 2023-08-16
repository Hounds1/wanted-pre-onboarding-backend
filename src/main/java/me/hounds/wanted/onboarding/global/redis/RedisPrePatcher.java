package me.hounds.wanted.onboarding.global.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.TopRateContentsResponse;
import me.hounds.wanted.onboarding.domain.content.service.ContentReadService;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.RecommendRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class RedisPrePatcher {

    private final RecommendRepository recommendRepository;
    private final ContentReadService contentReadService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    private static final String CONTENTS_KEY = "top-recommend-";
    private static final String REMOVE_PATTERN = "top-recommend*";

    /**
     * 캐싱된 값은 1시간의 수명을 가지고 있습니다.
     * 서버가 처음 가동되면 잔존해있는 게시글이 있는지 검사하고
     * 상위 10개의 게시물을 다시 캐싱합니다.
     * 또한, 1시간 마다 추천수 변동을 반영하기 위해
     * 다시 캐싱합니다.
     */
    @PostConstruct
    @Scheduled(cron = "0 0 * * *")
    public void initializeTopRecommendation() throws Exception {
        if (redisService.removeKeysWithPattern(REMOVE_PATTERN))
            initializeRedisWithContent();
    }

    public void initializeRedisWithContent() throws Exception {
        List<Object[]> contentWithMostRecommends = recommendRepository.findContentWithMostLikes(getPageable());
        List<TopRateContentsResponse> metas = new ArrayList<>();

        log.info("Lists size is [{}]", contentWithMostRecommends.size());

        if (!contentWithMostRecommends.isEmpty()) {
            for (int i = 0; i < contentWithMostRecommends.size(); i++) {
                Long targetId = (Long) contentWithMostRecommends.get(i)[0];
                log.info("Target id is [{}]", targetId);
                SimpleContentResponse response = contentReadService.findTopById(targetId);

                metas.add(TopRateContentsResponse.of(response));

                String responseJSON = objectMapper.writeValueAsString(response);
                String newKey = CONTENTS_KEY + targetId;

                redisService.writeToRedisWithTTL(newKey, responseJSON, 30L);
            }
        }

        String responseJson = objectMapper.writeValueAsString(metas);
        String metaKey = CONTENTS_KEY + "meta";

        redisService.writeToRedisWithTTL(metaKey, responseJson, 60L);
    }

    public Pageable getPageable() {
        return PageRequest.of(0, 10);
    }
}
