package me.hounds.wanted.onboarding.domain.content.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.TopRateContentsResponse;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.content.domain.persist.ContentRepository;
import me.hounds.wanted.onboarding.domain.content.error.CannotReadContentsException;
import me.hounds.wanted.onboarding.domain.content.error.ContentNotFoundException;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.RecommendRepository;
import me.hounds.wanted.onboarding.global.common.CustomPageResponse;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import me.hounds.wanted.onboarding.global.redis.RedisService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.hounds.wanted.onboarding.global.redis.vo.RedisKeys.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ContentReadService {

    private final ContentRepository contentRepository;
    private final RecommendRepository recommendRepository;
    private final ObjectMapper mapper;
    private final RedisService redisService;

    public CustomPageResponse<SimpleContentResponse> findWithPaging(final Long boardId, final Pageable pageable) {
        Page<Content> findContents = contentRepository.findAllByBoardId(pageable, boardId);

        Page<SimpleContentResponse> mappedResponse
                = findContents.map(content -> {
            long count = recommendRepository.countByContentId(content.getId());
            return SimpleContentResponse.of(content, count);
        });


        return CustomPageResponse.of(mappedResponse);
    }

    /**
     * 일반 게시물 조회시 1시간 캐싱
     */
    @Cacheable(value = "contentCache", key = "'caching-contents-' + #contentId")
    public SimpleContentResponse findById(final Long contentId) throws Exception {
            Content findContent = contentRepository.findById(contentId)
                    .orElseThrow(() -> new ContentNotFoundException(ErrorCode.CONTENT_NOT_FOUND));

            long count = recommendRepository.countByContentId(contentId);

            return SimpleContentResponse.of(findContent, count);
    }

    public SimpleContentResponse findTopById(final Long contentId) throws Exception {
        String fromRedis = redisService.readFromRedis(TOP_RECOMMENDATION_UNIT.getKey() + contentId);
        if (fromRedis != null) {
            log.info("[findTopById] :: This data has been come from Redis.");
            return mapper.readValue(fromRedis, SimpleContentResponse.class);
        } else {
            log.info("[findTopById] :: Saved new data into Redis.");
            Content findContent = contentRepository.findById(contentId)
                    .orElseThrow(() -> new ContentNotFoundException(ErrorCode.CONTENT_NOT_FOUND));
            long count = recommendRepository.countByContentId(contentId);

            SimpleContentResponse response = SimpleContentResponse.of(findContent, count);
            String responseJson = mapper.writeValueAsString(response);

            redisService.writeToRedis(TOP_RECOMMENDATION_UNIT.getKey() + contentId, responseJson);

            return response;
        }
    }

    /**
     * 캐싱된 상위 게시물 조회
     */
    public List<TopRateContentsResponse> readTopRateFromRedis() throws Exception {

        String jsonFromRedis
                = redisService.readFromRedis(TOP_RECOMMENDATION_META.getKey());

        if (!StringUtils.hasText(jsonFromRedis))
            return new ArrayList<>();

        log.info("cache data is [{}]", jsonFromRedis);

        try {
            TopRateContentsResponse[] contentArray
                    = mapper.readValue(jsonFromRedis, TopRateContentsResponse[].class);

            return Arrays.asList(contentArray);
        } catch (Exception e) {
            throw new CannotReadContentsException(ErrorCode.CAN_NOT_READ_CONTENTS);
        }
    }
}
