function addContextMenu(event, taskId) {
    event.preventDefault();

    let contextMenuId = "contextMenu" + taskId;
    let menu = document.getElementById(contextMenuId);

    menu.style.left = event.clientX + "px";
    menu.style.top = event.clientY + "px";
    menu.style.width = "100px";
    menu.style.height = "100px";
    menu.style.visibility = "visible";
}

function hideContextMenu(contextMenuId) {
    document.getElementById(contextMenuId).style.visibility = 'hidden';
}

function formSubmit(formName) {
    document.getElementById(formName).submit();
}

document.addEventListener('click', function (event) {
    let inside = (event.target.closest('#container'));
    if (!inside) {
        let contextMenu = document.querySelectorAll('[id^="contextMenu"]');
        contextMenu.forEach(item => {
            item.style.visibility = 'hidden';
        })
    }
});

function successMessage() {
    UIkit.notification({
        message: '<span uk-icon=\'icon: check\'></span> Completed',
        pos: 'top-center',
        status: 'success'
    });
}

function errorMessage() {
    UIkit.notification({
        message: '<span uk-icon=\'icon: ban\'></span> Error',
        pos: 'top-center',
        status: 'danger'
    });
}

function warningMessage() {
    UIkit.notification({
        message: '<span uk-icon=\'icon: warning\'></span> NotFound',
        pos: 'top-center',
        status: 'warning'
    });
}

function createCKEditor() {
    ClassicEditor
        .create(document.querySelector('#editor'))
        .catch(error => {
            console.error(error);
        });
}
