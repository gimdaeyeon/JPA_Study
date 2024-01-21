{// 버튼처리
    let $backBtn = document.querySelector('.go-back > button');

    $backBtn.addEventListener('click',()=>{
        window.history.back();
        // location.href = '/board/list';
    });

    // 수정 버튼
    let $editBtn =document.querySelector('.btns__edit');
    $editBtn.addEventListener('click',(e)=>{
        let boardId = e.target.dataset.num;
        location.href=`/board/modify/${boardId}`;
    });
    // 삭제 버튼

    let $deleteBtn = document.querySelector('.btns__delete');
    $deleteBtn.addEventListener('click',(e)=>{
        let boardId = e.target.dataset.num;
        location.href=`/board/remove/${boardId}`;
    });

}



















