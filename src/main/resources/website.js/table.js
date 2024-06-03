$(document).ready(getAllUsers);

// Функция для получения всех пользователей
function getAllUsers() {
    // Очистка таблицы
    $("#table").empty();

    // AJAX запрос для получения списка пользователей
    $.ajax({
        type: 'POST',
        url: '/admin/users',
        timeout: 3000,
        success: function (data) {
            console.log(data);
            // Добавление каждого пользователя в таблицу
            $.each(data, function (i, user) {
                $("#table").append($('<tr>').append(
                    $('<td>').append($('<span>').text(user.id)),
                    $('<td>').append($('<span>').text(user.username)),
                    $('<td>').append($('<span>').text(user.lastName)),
                    $('<td>').append($('<span>').text(user.email)),
                    $('<td>').append($('<span>').text(user.roles)),
                    // Кнопка "Edit" для редактирования пользователя
                    $('<td>').append($('<button>').text("Edit").attr({
                        "type": "button",
                        "class": "btn btn-primary edit",
                        "data-toggle": "modal",
                        "data-target": "#myModal"
                    }).data("user", user)),
                    // Кнопка "Delete" для удаления пользователя
                    $('<td>').append($('<button>').text("Delete").attr({
                        "type": "button",
                        "class": "btn btn-danger delete",
                        "data-toggle": "modal",
                        "data-target": "#myModalDelete"
                    }).data("user", user))
                ));
            });
        }
    });
}

// Обработчик нажатия кнопки редактирования
$(document).on("click", ".edit", function () {
    let user = $(this).data('user');

    // Заполнение формы данными пользователя для редактирования
    $('#firstNameInput').val(user.username);
    $('#lastNameInput').val(user.lastName);
    $('#emailInput').val(user.email);
    $('#idInput').val(user.id);
    $('#roleInput').val(user.roles);
});

// Обработчик нажатия кнопки редактирования пользователя
$(document).on("click", ".editUser", function () {
    let formData = $(".formEditUser").serializeArray();
    // AJAX запрос для отправки данных формы на редактирование
    $.ajax({
        type: 'POST',
        url: '/admin/edit',
        data: formData,
        timeout: 100,
        success: function () {
            // После успешного редактирования обновление списка пользователей
            getAllUsers();
        }
    });
});

// Обработчик нажатия кнопки удаления пользователя
$(document).on("click", ".delete", function () {
    let user = $(this).data('user');

    // Заполнение данных пользователя для удаления
    $('#firstName').val(user.username);
    $('#lastName').val(user.lastName);
    $('#email').val(user.email);
    $('#id').val(user.id);

    // Обработка подтверждения удаления пользователя
    $(document).on("click", ".deleteUser", function () {
        // AJAX запрос на удаление пользователя
        $.ajax({
            type: 'GET',
            url: '/admin/delete/' + $('#id').val(),
            timeout: 100,
            success: function () {
                // После успешного удаления обновление списка пользователей
                getAllUsers();
            }
        });
    });
});

// Обработчик нажатия кнопки добавления пользователя
$('.addUser').click(function () {
    $('#usersTable').trigger('click');
    let formData = $(".formAddUser").serializeArray();
    // AJAX запрос для добавления нового пользователя
    $.ajax({
        type: 'POST',
        url: '/admin/add',
        data: formData,
        timeout: 100,
        success: function () {
            // Очистка формы после успешного добавления
            $('.formAddUser')[0].reset();
            // Обновление списка пользователей
            getAllUsers();
        }
    });
});

$(document).ready(all());

// Функция для получения всех пользователей
function all() {
    // Очистка таблицы
    $("#userTable").empty();

    // AJAX запрос для получения всех пользователей
    $.ajax({
        type: 'GET',
        url: '/admin/all',
        timeout: 3000,
        // Обработка ошибки
        error: function() {
            $('#blockMenuforUser').hide();
        },
        success: function (data) {
            console.log(data);
            // Добавление пользователей в таблицу
            $.each(data, function (i, user) {
                // Проверка роли пользователя для отображения соответствующего меню
                if(user.roles == "ROLE_USER") {
                    $('#menuUser').trigger('click');
                    $('#main2').trigger('click');
                    $('#blockMenuforAdmin').hide();
                }
                $("#userTable").append($('<tr>').append(
                    $('<td>').append($('<span>').text(user.id)),
                    $('<td>').append($('<span>').text(user.username)),
                    $('<td>').append($('<span>').text(user.lastName)),
                    $('<td>').append($('<span>').text(user.email)),
                    $('<td>').append($('<span>').text(user.roles))
                ));
            });
        }
    });
}