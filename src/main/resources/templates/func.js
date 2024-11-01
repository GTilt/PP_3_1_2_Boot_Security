document.addEventListener('DOMContentLoaded', () => {
    fetchUsers();
});

function fetchUsers() {
    fetch('/admin')
        .then(response => response.json())
        .then(data => {
            const users = data.allUsers;
            const tableBody = document.getElementById('user-table-body');
            tableBody.innerHTML = ''; // Очищаем таблицу перед заполнением новыми данными

            users.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.roles.map(role => role.name).join(', ')}</td>
                <td><button class="btn btn-info btn-sm" onclick="openEditModal(${user.id})">Edit</button></td>
                <td><button class="btn btn-danger btn-sm" onclick="openDeleteModal(${user.id})">Delete</button></td>
            `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Ошибка загрузки пользователей:', error));
}

function openEditModal(userId) {
    fetch(`/admin/edit?id=${userId}`)
        .then(response => response.json())
        .then(user => {
            document.getElementById('edit-id').value = user.id;
            document.getElementById('edit-firstName').value = user.firstName;
            document.getElementById('edit-lastName').value = user.lastName;
            document.getElementById('edit-age').value = user.age;
            document.getElementById('edit-email').value = user.email;
            document.getElementById('edit-username').value = user.username;
            document.getElementById('edit-role').value = user.roles[0].name;
            $('#editUserModal').modal('show');
        })
        .catch(error => console.error('Ошибка загрузки данных пользователя:', error));
}

function openDeleteModal(userId) {
    fetch(`/admin/delete?id=${userId}`)
        .then(response => response.json())
        .then(user => {
            document.getElementById('delete-id').value = user.id;
            document.getElementById('delete-username').textContent = user.username;
            $('#deleteUserModal').modal('show');
        })
        .catch(error => console.error('Ошибка загрузки данных пользователя:', error));
}

function updateUser() {
    const userId = document.getElementById('edit-id').value;
    const updatedUser = {
        id: userId,
        firstName: document.getElementById('edit-firstName').value,
        lastName: document.getElementById('edit-lastName').value,
        age: document.getElementById('edit-age').value,
        email: document.getElementById('edit-email').value,
        username: document.getElementById('edit-username').value,
        roles: [{name: document.getElementById('edit-role').value}]
    };

    fetch('/admin/edit', {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(updatedUser)
    })
        .then(response => {
            if (response.ok) {
                fetchUsers();
                $('#editUserModal').modal('hide');
            } else {
                alert('Не удалось обновить пользователя');
            }
        })
        .catch(error => console.error('Ошибка при обновлении пользователя:', error));
}

function deleteUser() {
    const userId = document.getElementById('delete-id').value;

    fetch(`/admin/delete`, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({id: userId})
    })
        .then(response => {
            if (response.ok) {
                fetchUsers();
                $('#deleteUserModal').modal('hide');
            } else {
                alert('Не удалось удалить пользователя');
            }
        })
        .catch(error => console.error('Ошибка при удалении пользователя:', error));
}




