package com.sh.mvc.member.model.service;

import com.sh.mvc.member.model.entity.Gender;
import com.sh.mvc.member.model.entity.Member;
import com.sh.mvc.member.model.entity.Role;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 순서 지정하려고 사용
public class MemberServiceTest {
    MemberService memberService; // fixture (테스트 대상 객체)

    @BeforeEach
    public void beforeEach() {
        this.memberService = new MemberService();
    } // 매번 새로 만들어 줌

    @DisplayName("MemberService객체는 null이 아니다")
    @Test
    public void test1(){
        assertThat(memberService).isNotNull();

    }


    /**
     * mybatis는 ResultSet의 데이터를  vo클래스객체로 자동변환한다
     * - 컬럼명과 필드명이 일치해야한다. (언더스코어 <-> 카멜케이싱 설정 필수) -> config에 있음
     * - varchar2/char <-> String
     * - number <-> int/double
     * - date <-> java.util.Date(기본값), java.time.LocalDate
     * - number(0|1) <-> boolean(자동변환해줌)
     * - varchar2/char <-> Enum
     */
    @DisplayName("존재하는 회원이 정상적으로 조회된다")
    @Test
     public void test2() {
        Member member = memberService.findById("abcde");
        System.out.println(member);
        // 객체
        assertThat(member).isNotNull();
        // 필드
        assertThat(member.getId()).isNotNull();
        assertThat(member.getPassword()).isNotNull();
        assertThat(member.getName()).isNotNull();
        assertThat(member.getRole()).isNotNull();
    }

    @DisplayName("존재하지 않는 회원은 NULL이 된다")
    @Test
    public void test3() {
        Member member = memberService.findById("asdasdasdfafesdfasdfsdfdsf");
        assertThat(member).isNull();


    }
    @DisplayName("회원 전체 조회")
    @Test
    public void test4() {
        List<Member> members = memberService.findAll();
        assertThat(members)
                .isNotNull()
                .isNotEmpty();
        // Consumer타입 람다식 : 매개변수가 하나 있고, 리턴타입은 없음
        members.forEach((member) -> {
             System.out.println(member);
             assertThat(member.getId()).isNotNull();
             assertThat(member.getPassword()).isNotNull();
             assertThat(member.getName()).isNotNull();
             assertThat(member.getRole()).isNotNull();
        });
    }

    @DisplayName("회원 이름 검색")
    @Test
    public void test5() {
        String keyword = "무개";
        List<Member> members = memberService.fidByName(keyword);
        assertThat(members)
                .isNotNull()
                .isNotEmpty();
        members.forEach((member) -> assertThat(member.getName()).contains(keyword)); // member.getName()). 점을 찍었을 때 뒤에 나오는걸로사용
    }

    @DisplayName("성별 검색")
    @Test
    public void test6() {
        String gender = "M";
        List<Member> members = memberService.findByGender(gender);
        assertThat(members).isNotNull().isNotEmpty();
        members.forEach((member) -> {
            // Gender enum객체의 실제값 : name()
            assertThat(member.getGender().name()).isEqualTo(gender); // String 타입비교
            assertThat(member.getGender()).isEqualTo(Gender.valueOf(gender)); // Enum 타입비교 String -> enum
        });
    }


//    @Disabled // 건너뛰게하기
    @Order(1)
    @DisplayName("회원가입")
    @Test
    @Disabled
    public void test7() {
        String id = "honggd";
        String password = "1234";
        String name = "홍길동";

        Member member =
                new Member(
                        id, password, name, Role.U, Gender.M, LocalDate.of(1999,9,9),
                        "honggd@naver.com", "01012341234", Arrays.asList("게임", "독서"),0 , null);
        int result = memberService.insertMember(member);
        assertThat(result).isEqualTo(1);

        Member member2 = memberService.findById(id);
        assertThat(member2).isNotNull();
        assertThat(member2.getId()).isEqualTo(id);
        assertThat(member2.getPassword()).isEqualTo(password);
        assertThat(member2.getName()).isEqualTo(name);
    }

    @Order(2)
    @DisplayName("회원가입 시 오류 체크") // 한번 입력되어있을때다시 하면 오류남
    @Test
    @Disabled
    public void test8() {
        Member member =
                new Member(
                        "sinsa", null, "홍길동", Role.U, Gender.M, LocalDate.of(1999,9,9),
                        "honggd@naver.com", "01012341234", Arrays.asList("게임", "독서"),0 , null);
        Throwable th = catchThrowable(() -> {
            int result = memberService.insertMember(member); // 예외 던져지면 성공
        });
        assertThat(th).isInstanceOf(Exception.class); // 예외메세지에 뭐가 들었는지 확인
    }

    @Order(3)
//    @Disabled
    @DisplayName("회원정보 수정")
    @Test
    @Disabled
    public void test9() {
        // given 주어진 상황 작성
        String id = "honggd";
        Member member = memberService.findById(id);

        // when 업무로직 작성
        String newName = member.getName() + "길동";
        Gender newGender = null;
        LocalDate newBirthday = LocalDate.of(2000, 1, 1);
        String newEmail = "honggd@Gmail.com";
        String newPhone ="01011112222";

        member.setName(newName);
        member.setGender(newGender);
        member.setBirthday(newBirthday);
        member.setEmail(newEmail);
        member.setPhone(newPhone);

        int result = memberService.updateMember(member);
        assertThat(result).isGreaterThan(0);

        // then 검증코드
        Member member2 = memberService.findById(id);
        assertThat(member2.getName()).isEqualTo(newName);
        assertThat(member2.getGender()).isEqualTo(newGender);
        assertThat(member2.getBirthday()).isEqualTo(newBirthday);
        assertThat(member2.getEmail()).isEqualTo(newEmail);
        assertThat(member2.getPhone()).isEqualTo(newPhone);
    }


//    @Disabled
    @Order(4)
    @DisplayName("회원 비밀번호 수정")
    @Test
    @Disabled
    public void test10() {
        // update member set password = ? where id = ?
        String id = "honggd";
        Member member = memberService.findById(id);

        String newPassword = "qwer1234";
        member.setPassword(newPassword);

        int result = memberService.updateMemberPassword(member);

        assertThat(result).isGreaterThan(0);
        Member member2 = memberService.findById(id);
        assertThat(member2.getPassword()).isEqualTo(newPassword);
}


//    @Disabled
    @Order(5)
    @DisplayName("회원 권한 수정")
    @Test
    @Disabled
    public void test11() {
        // update member set role = ? where id= ?
        String id = "honggd";
        Member member = memberService.findById(id);

        Role newRole = Role.A;
        member.setRole(newRole);

        int result = memberService.updateMemberRole(member);

        assertThat(result).isGreaterThan(0);
        Member member2 = memberService.findById(id);
        assertThat(member2.getRole()).isEqualTo(newRole);
        assertThat(member2.getRole().name()).isEqualTo(newRole.name());
    }


//    @Disabled
    @Order(6)
    @DisplayName("회원 삭제")
    @Test
    @Disabled
    public void test12() {
        String id = "honggd";
        Member member = memberService.findById(id);
        assertThat(member).isNotNull();

        int result = memberService.deleteMember(id);
        assertThat(result).isGreaterThan(0);

        Member member2 = memberService.findById(id);
        assertThat(member2).isNull();
    }
}


