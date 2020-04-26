function post(url, data, success) {
    $.post({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': url,
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': success
    });
}

function toggleMenu() {
    const toggleBtn = document.querySelector('.navbar-toggle');

    toggleBtn.addEventListener('click', (e) => {
        let menu;
        if (e.currentTarget.dataset.target) {
            menu = document.querySelector(e.currentTarget.dataset.target);
        }
        if (menu)
            menu.classList.toggle('collapsed');
    });
}