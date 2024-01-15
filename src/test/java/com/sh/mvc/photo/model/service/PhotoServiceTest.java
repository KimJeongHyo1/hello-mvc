package com.sh.mvc.photo.model.service;

import com.sh.mvc.photo.model.entity.Photo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PhotoServiceTest {
    private PhotoService photoService;
    static final int LIMIT = 5; // 한 페이지당 게시물 수

    @BeforeEach
    void setUp() {
        this.photoService = new PhotoService();
    }

   @DisplayName("PhotoService는 null이 아니다.")
    @Test
    void test() {
        assertThat(photoService).isNotNull();
   }


    /**
     * 더보기영역만 존재
     * content영역은 같고 page bar는 없음
     */
    @DisplayName("전체 게시물 수 조회")
    @Test
    void test1() {
        // given
        // when
        int totalCount = photoService.getTotalCount();

        // then
        assertThat(totalCount).isGreaterThan(0);
    }
    @DisplayName("페이지별 게시물 조회")
    @ParameterizedTest
    @MethodSource("pageProvider") // test보다 먼저 실행돼야함
    void test2(int page) {
        // given
        assertThat(page).isNotZero();

        // when
        Map<String, Object> param = Map.of("page", page, "limit", LIMIT);
        List<Photo> photos = photoService.findAll(param);

        // then
        assertThat(photos)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy((photo) -> {
                    assertThat(photo.getId()).isNotNull().isNotZero();
                    assertThat(photo.getContent()).isNotNull();
                    assertThat(photo.getOriginalFilename()).isNotNull();
                    assertThat(photo.getRenamedFilename()).isNotNull();
                    assertThat(photo.getRegDate()).isNotNull();
                })
                .size().isLessThanOrEqualTo(LIMIT); // limit수보다는 적게 넘어와라 -> 숫자검증이라 마지막에 작성 가능
    }


    /**
     * 총 게시물 수 -> 전체 페이지 수 조회 -> Stream으로 변환
     * @return
     */
    public static Stream<Integer> pageProvider() {
        PhotoService photoService = new PhotoService();
        int totalCount = photoService.getTotalCount();
        int totalPage = (int) Math.ceil((double)totalCount / LIMIT);
        return IntStream.rangeClosed(1, totalPage).boxed();
    }





}