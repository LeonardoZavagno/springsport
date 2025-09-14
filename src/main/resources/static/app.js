// DOM Elements
const loginSection = document.getElementById('login-section');
const homeSection = document.getElementById('home-section');
const usersSection = document.getElementById('users-section');
const sectionForm = document.getElementById('section-form');
const navHome = document.getElementById('nav-home');
const navUsers = document.getElementById('nav-users');
const navLogout = document.getElementById('nav-logout');
const loginForm = document.getElementById('login-form');
const loginMessageDiv = document.getElementById('login-message');
const userForm = document.getElementById('user-form');
const userIdField = document.getElementById('user_id');
const userNameField = document.getElementById('user_name');
const userSurnameField = document.getElementById('user_surname');
const usersTableBody = document.getElementById('users-table-body');
const messageDiv = document.getElementById('message');
const cancelBtn = document.getElementById('cancel-edit');
const logo = document.querySelector('.brand-logo');

// --- Utility functions ---
function showSection(section) {
    loginSection.style.display = 'none';
    homeSection.style.display = 'none';
    usersSection.style.display = 'none';
    section.style.display = 'block';
}

function showMessage(msg, isError = false, target = messageDiv) {
    target.textContent = msg;
    target.className = isError ? 'red-text text-darken-2' : 'green-text text-darken-2';
}

function resetForm() {
    userForm.reset();
    userIdField.value = '';
    sectionForm.textContent = 'New User';
    M.updateTextFields();
}

function updateNav() {
    const loggedIn = !!sessionStorage.getItem('jwt');
    navUsers.style.display = loggedIn ? 'block' : 'none';
    navLogout.style.display = loggedIn ? 'block' : 'none';
    navHome.style.display = loggedIn ? 'block' : 'none';
}

// --- Auth functions ---
async function loginUser(username, password) {
    const res = await fetch('/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });
    if (!res.ok) throw new Error('Invalid credentials');
    const data = await res.json();
    sessionStorage.setItem('jwt', data.token);
}

function logoutUser() {
    sessionStorage.removeItem('jwt');
    updateNav();
    showSection(loginSection);
    showMessage('', false, loginMessageDiv);
}

// --- Fetch wrapper with JWT ---
async function fetchAPI(url, options = {}) {
    options.headers = options.headers || {};
    const token = sessionStorage.getItem('jwt');
    if (!token) throw new Error("Not logged in");
    options.headers['Authorization'] = `Bearer ${token}`;
    if (!options.headers['Content-Type'] && options.method !== 'GET') {
        options.headers['Content-Type'] = 'application/json';
    }

    const res = await fetch(url, options);
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    if (res.status === 204) return null;
    return res.json();
}

// --- CRUD functions for users ---
async function getUsers() {
    try {
        const users = await fetchAPI('/api/v1/users');
        usersTableBody.innerHTML = '';
        users.forEach(u => {
            const row = usersTableBody.insertRow();
            row.insertCell().textContent = u.user_id;
            row.insertCell().textContent = u.user_name;
            row.insertCell().textContent = u.user_surname;

            const editBtn = document.createElement('button');
            editBtn.textContent = 'Edit';
            editBtn.className = 'btn-small blue';
            editBtn.onclick = () => editUser(u.user_id, u.user_name, u.user_surname);
            row.insertCell().appendChild(editBtn);

            const delBtn = document.createElement('button');
            delBtn.textContent = 'Delete';
            delBtn.className = 'btn-small red';
            delBtn.onclick = () => deleteUser(u.user_id);
            row.insertCell().appendChild(delBtn);
        });
    } catch (err) {
        showMessage(err.message, true);
    }
}

async function saveUser(user) {
    const id = userIdField.value;
    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/v1/users/${id}` : '/api/v1/users';
    try {
        await fetchAPI(url, { method, body: JSON.stringify(user) });
        showMessage(id ? 'User updated!' : 'User created!');
        resetForm();
        getUsers();
    } catch (err) {
        showMessage(err.message, true);
    }
}

async function deleteUser(id) {
    if (!confirm('Are you sure?')) return;
    try {
        await fetchAPI(`/api/v1/users/${id}`, { method: 'DELETE' });
        showMessage('User deleted!');
        getUsers();
    } catch (err) {
        showMessage(err.message, true);
    }
}

function editUser(id, name, surname) {
    userIdField.value = id;
    userNameField.value = name;
    userSurnameField.value = surname;
    sectionForm.textContent = 'Edit User';
    M.updateTextFields();
}

// --- Navigation logic ---
function showHomeOrLogin() {
    if (sessionStorage.getItem('jwt')) {
        showSection(homeSection);
    } else {
        showSection(loginSection);
    }
}

// --- Event listeners ---
logo.addEventListener('click', e => { e.preventDefault(); showHomeOrLogin(); });
navUsers.addEventListener('click', e => { 
    e.preventDefault(); 
    if (!sessionStorage.getItem('jwt')) return showSection(loginSection);
    showSection(usersSection); 
    getUsers(); 
});
navLogout.addEventListener('click', e => { e.preventDefault(); logoutUser(); });

if (loginForm) {
    loginForm.addEventListener('submit', async e => {
        e.preventDefault();
        try {
            const username = loginForm.username.value;
            const password = loginForm.password.value;
            await loginUser(username, password);
            updateNav();
            showSection(homeSection);
            showMessage(`Welcome ${username}`, false, loginMessageDiv);
        } catch (err) {
            showMessage(err.message, true, loginMessageDiv);
        }
    });
}

userForm.addEventListener('submit', e => {
    e.preventDefault();
    saveUser({ user_name: userNameField.value, user_surname: userSurnameField.value });
});

cancelBtn.addEventListener('click', resetForm);

// --- Initial page load ---
document.addEventListener('DOMContentLoaded', () => {
    updateNav();
    if (sessionStorage.getItem('jwt')) {
        showSection(homeSection);
    } else {
        showSection(loginSection);
    }
});
