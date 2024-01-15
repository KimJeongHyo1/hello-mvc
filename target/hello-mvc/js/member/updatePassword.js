// 비밀번호 일치여부
document.querySelector("#newPasswordConfirmation").onblur = (e) => {
    const pw1 = document.querySelector("#newPassword");
    const pw2 = e.target;
    if(pw1.value !== pw2.value) {
        alert("비밀번호가 일치하지 않습니다.");
        pw1.select();
    }
};

// 폼 유효성검사
document.memberPasswordUpdateFrm.onsubmit = (e) => {
    const frm = e.target;
    const oldPassword = e.target.oldPassword;
    const newPassword = e.target.newPassword;
    const newPasswordConfirmation = e.target.querySelector("#newPasswordConfirmation");

    // 비밀번호 검사 - 영문자/숫자/특수문자!@#$% 4글자 이상
    if (!/^[\w!@#$%]{4,}$/.test(newPassword.value)) {
        alert("비밀번호는 영문자/숫자/특수문자 !@#$% 4글자 이상이어야 합니다.");
        return false;
    }
    if (newPassword.value !== newPasswordConfirmation.value) {
        alert("두 비밀번호가 일치하지 않습니다.");
        return false;
    }
};