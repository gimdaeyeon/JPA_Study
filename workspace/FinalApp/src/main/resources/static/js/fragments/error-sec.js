{
    let $errorSec = document.querySelector('.error');
    let $okBtn = document.querySelector('.error__box > button');

    $okBtn.addEventListener('click',function () {
        $errorSec.classList.add('none');
    });
}