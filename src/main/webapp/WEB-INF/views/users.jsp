<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
    <a href="/">Home</a>
    <br/>
    <hr>
    <div id="message" style="color:green; font-weight: bold;"></div>
    <h1>New/Edit User</h1>
    <form id="user-form">
        <input type="hidden" id="user_id"/>
        <table>
            <tbody>
                <tr>
                    <td><label for="user_name">NAME:</label></td>
                    <td><input type="text" id="user_name" maxlength="50" required/></td>
                </tr>
                <tr>
                    <td><label for="user_surname">SURNAME:</label></td>
                    <td><input type="text" id="user_surname" maxlength="50" required/></td>
                </tr>
            </tbody>
        </table>
        <input type="submit" value="Create"/>
        <button type="button" id="cancel-edit">Cancel</button>
    </form>
    <br/>
    <hr>
    <h1>List Users</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>SURNAME</th>
                <th colspan="2">ACTIONS</th>
            </tr>
        </thead>
        <tbody id="users-table-body">
        </tbody>
    </table>

    <script>
        const userForm = document.getElementById('user-form');
        const userIdField = document.getElementById('user_id');
        const userNameField = document.getElementById('user_name');
        const userSurnameField = document.getElementById('user_surname');
        const usersTableBody = document.getElementById('users-table-body');
        const messageDiv = document.getElementById('message');
        const cancelBtn = document.getElementById('cancel-edit');

        const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
        const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

        const API_URL = '/api/v1/users';

        const apiHeaders = {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        };

        async function fetchAPI(url, options = {}) {
            const response = await fetch(url, options);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`API request failed: ${response.status} ${response.statusText} - ${errorText}`);
            }
            if (response.status === 204) {
                return null;
            }
            return response.json();
        }

        async function getUsers() {
            try {
                const users = await fetchAPI(API_URL);
                usersTableBody.innerHTML = '';
                users.forEach(user => {
                    const row = usersTableBody.insertRow();
                    row.insertCell().textContent = user.user_id;
                    row.insertCell().textContent = user.user_name;
                    row.insertCell().textContent = user.user_surname;

                    const editButton = document.createElement('button');
                    editButton.textContent = 'Edit';
                    editButton.addEventListener('click', () => editUser(user.user_id, user.user_name, user.user_surname));
                    row.insertCell().appendChild(editButton);

                    const deleteButton = document.createElement('button');
                    deleteButton.textContent = 'Delete';
                    deleteButton.addEventListener('click', () => deleteUser(user.user_id));
                    row.insertCell().appendChild(deleteButton);
                });
            } catch (error) {
                showMessage(error.message, true);
            }
        }

        async function saveUser(user) {
            const id = userIdField.value;
            const method = id ? 'PUT' : 'POST';
            const url = id ? `${API_URL}/${id}` : API_URL;

            try {
                await fetchAPI(url, { method, headers: apiHeaders, body: JSON.stringify(user) });
                showMessage(`User ${id ? 'updated' : 'created'} successfully!`);
                resetForm();
                getUsers();
            } catch (error) {
                showMessage(error.message, true);
            }
        }

        async function deleteUser(id) {
            if (confirm('Are you sure you want to delete this user?')) {
                try {
                    await fetchAPI(`${API_URL}/${id}`, { method: 'DELETE', headers: apiHeaders });
                    showMessage('User deleted successfully!');
                    getUsers();
                } catch (error) {
                    showMessage(error.message, true);
                }
            }
        }

        function editUser(id, name, surname) {
            userIdField.value = id;
            userNameField.value = name;
            userSurnameField.value = surname;
            userForm.querySelector('input[type="submit"]').value = 'Update';
        }

        function resetForm() {
            userForm.reset();
            userIdField.value = '';
            userForm.querySelector('input[type="submit"]').value = 'Create';
        }

        function showMessage(msg, isError = false) {
            messageDiv.textContent = msg;
            messageDiv.style.color = isError ? 'red' : 'green';
        }

        userForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const user = { user_name: userNameField.value, user_surname: userSurnameField.value };
            saveUser(user);
        });

        cancelBtn.addEventListener('click', resetForm);

        getUsers();
    </script>
</body>
</html>