const frm = document.memberRoleUpdateFrm;
const id = frm.id;
const role = frm.role;

/**
 *  <pre>
 *  dataset
 *  - html태그에 데이터를 저장하는 속성
 *    (setter)
 *  - data-xxx : 여러단어인 경우 단어단위로 -연결
 *    - data-id
 *    - data-user-pet
 *
 *    (getter)
 *  - 가져올때는 태그객체의 dataset속성으로부터 key값 참조(camelcasing)
 */
document.querySelectorAll(".member-role").forEach((select) => {
    select.addEventListener('change', (e) => {
        const {value} = e.target;
        // console.log(value);
        // role.value = value;

        // console.log(e.target.dataset);
        const {id : idVal} = e.target.dataset; /* data-id로 저장한값 가져옴 */
        // console.log(idVal, value);

        if(confirm(`${idVal}회원을 ${value == 'A' ? '관리자' : '일반회원'}로 변경?`)) {
            role.value = value;
            id.value = idVal;
            frm.submit();
        } else {
            // 취소 누르면 원래 선택했던 값으로 복귀
            e.target.querySelector("[selected]").selected = true;
        }

        // id.value = ?;

    });
});