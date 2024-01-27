{  //버튼 처리

    // 목록 버튼
    let $backBtn = document.querySelector('.go-back > button');

    $backBtn.addEventListener('click', () => {
        window.history.back();
        // location.href = '/board/list';
    });

    // 수정 버튼
    let $editBtn = document.querySelector('.btns__edit')

    $editBtn.addEventListener('click', (e) => {
        let boardId = e.target.dataset.num;
        location.href = '/board/modify/' + boardId;
    });

    // 삭제 버튼
    let $deleteBtn = document.querySelector('.btns__delete');

    $deleteBtn.addEventListener('click', (e) => {
        let boardId = e.target.dataset.num;
        location.href = '/board/remove/' + boardId;
    });
}


let page = 0;
let hasNext = true;

{   //리플 처리

//    페이지 진입시 리스트 뿌리기
    getCommentList();

//    페이징 이벤트 처리
    window.addEventListener('scroll', function () {
        if (!hasNext) return;

        let {scrollTop, scrollHeight, clientHeight} = document.documentElement;

        if (clientHeight + scrollTop >= scrollHeight) {
            page++;
            getCommentList();
        }
    });


//     작성
    let $writeBtn = document.querySelector('.reply__write-btn');
    $writeBtn.addEventListener('click', function () {
        let boardId = document.querySelector('#boardId').value;
        let content = document.querySelector('#reply-content').value;
        let bodyData = {content: content};

        fetch(`/api/v3/boards/${boardId}/comments`, {
            method: 'post',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(bodyData)
        }).then(resp => {
            if (!resp.ok) {
                throw new Error("댓글 작성 실패");
            }
            return resp.json();
        }).then(resp => {
            console.log(resp);

            document.querySelector('.reply__list-ul').innerHTML = '';
            document.querySelector('#reply-content').value = '';
            getCommentList();
        }).catch(err => console.log('error : ', err.message));

    });
}

function displayCommentList(commentList) {
    let commentTag = '';

    commentList.forEach(comment => {
        commentTag += `
         <li>
            <strong class="reply__writer">${comment.loginId}</strong>
            <span class="reply__date">${comment.createdDate}</span>
            <p class="reply__content">${comment.content}</p>
         </li>
       `;
    });

    document.querySelector('.reply__list-ul').insertAdjacentHTML("beforeend", commentTag);
}

/**
 * 댓글 목록 받아오는 함수
 */
function getCommentList() {
    let boardId = document.querySelector('#boardId').value;

    fetch(`/api/v1/boards/${boardId}/comments?page=${page}`, {method: 'get'})
        .then(resp => {
            if (!resp.ok) {
                throw new Error("댓글 리스트 조회 실패");
            }
            return resp.json();
        }).then(resp => {
        hasNext = !resp.data.last;
        displayCommentList(resp.data.content);

    })
}







