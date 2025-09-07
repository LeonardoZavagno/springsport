import { UserManager, WebStorageStateStore, Log } from 'https://cdn.jsdelivr.net/npm/oidc-client-ts@2/dist/oidc-client-ts.min.js';

Log.setLevel(Log.INFO);

const oidcConfig = {
    authority: window.location.origin,
    client_id: 'my-client-dev',
    redirect_uri: window.location.origin + '/index.html',
    response_type: 'code',
    scope: 'openid profile',
    post_logout_redirect_uri: window.location.origin + '/index.html',
    userStore: new WebStorageStateStore({ store: sessionStorage }),
    automaticSilentRenew: true,
};

const userManager = new UserManager(oidcConfig);
let currentUser = null;

// DOM Elements
const homeSection = document.getElementById('home-section');
const usersSection = document.getElementById('users-section');
const navHome = document.getElementById('nav-home');
const navUsers = document.getElementById('nav-users');
const navLogout = document.getElementById('nav-logout');
const sectionForm = document.getElementById('section-form');
const userForm = document.getElementById('user-form');
const userIdField = document.getElementById('user_id');
const userNameField = document.getElementById('user_name');
const userSurnameField = document.getElementById('user_surname');
const usersTableBody = document.getElementById('users-table-body');
const messageDiv = document.getElementById('message');
const cancelBtn = document.getElementById('cancel-edit');

// Utility functions
function showSection(section) {
    homeSection.style.display = "none";
    usersSection.style.display = "none";
    section.style.display = "block";
}

function showMessage(msg, isError = false) {
    messageDiv.textContent = msg;
    messageDiv.className = isError ? 'red-text text-darken-2' : 'green-text text-darken-2';
}

function resetForm() {
    sectionForm.textContent = 'New User';
    userForm.reset();
    userIdField.value = '';
    userForm.querySelector('button[type="submit"]').textContent = 'Create';
}

function editUser(id, name, surname) {
    sectionForm.textContent = 'Edit User';
    userIdField.value = id;
    userNameField.value = name;
    userSurnameField.value = surname;
    userForm.querySelector('button[type="submit"]').textContent = 'Update';
    M.updateTextFields();
}

// Auth functions
async function login() { await userManager.signinRedirect(); }
async function handleRedirect() { currentUser = await userManager.signinRedirectCallback(); showSection(homeSection); showMessage(`Welcome ${currentUser.profile.name || 'User'}`); }
async function logout() { if(currentUser) await userManager.signoutRedirect(); }

async function fetchAPI(url, options = {}) {
    if (!currentUser) throw new Error("Not logged in");
    options.headers = options.headers || {};
    options.headers['Authorization'] = `Bearer ${currentUser.access_token}`;
    if (!options.headers['Content-Type'] && options.method !== 'GET') options.headers['Content-Type'] = 'application/json';
    const response = await fetch(url, options);
    if (!response.ok) throw new Error(`${response.status} ${response.statusText}`);
    if (response.status === 204) return null;
    return response.json();
}

// CRUD functions
async function getUsers() {
    try {
        const users = await fetchAPI('/api/v1/users');
        usersTableBody.innerHTML = '';
        users.forEach(user => {
            const row = usersTableBody.insertRow();
            row.insertCell().textContent = user.user_id;
            row.insertCell().textContent = user.user_name;
            row.insertCell().textContent = user.user_surname;

            const editBtn = document.createElement('button');
            editBtn.textContent = 'Edit';
            editBtn.className = 'btn-small blue waves-effect waves-light';
            editBtn.addEventListener('click', () => editUser(user.user_id, user.user_name, user.user_surname));
            row.insertCell().appendChild(editBtn);

            const delBtn = document.createElement('button');
            delBtn.textContent = 'Delete';
            delBtn.className = 'btn-small red waves-effect waves-light';
            delBtn.addEventListener('click', () => deleteUser(user.user_id));
            row.insertCell().appendChild(delBtn);
        });
    } catch (err) { showMessage(err.message, true); }
}

async function saveUser(user) {
    const id = userIdField.value;
    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/v1/users/${id}` : `/api/v1/users`;
    try { await fetchAPI(url, { method, body: JSON.stringify(user) }); showMessage(id ? 'User updated!' : 'User created!'); resetForm(); getUsers(); } 
    catch (err) { showMessage(err.message, true); }
}

async function deleteUser(id) { if(!confirm('Are you sure?')) return; try { await fetchAPI(`/api/v1/users/${id}`, { method: 'DELETE' }); showMessage('User deleted!'); getUsers(); } catch(err){ showMessage(err.message,true); }}

// Event listeners
userForm.addEventListener('submit', e => { e.preventDefault(); saveUser({ user_name: userNameField.value, user_surname: userSurnameField.value }); });
cancelBtn.addEventListener('click', resetForm);
navHome.addEventListener('click', e => { e.preventDefault(); showSection(homeSection); });
navUsers.addEventListener('click', e => { e.preventDefault(); showSection(usersSection); getUsers(); });
navLogout.addEventListener('click', e => { e.preventDefault(); logout(); });

// Init SPA
(async function init() {
    if (window.location.search.includes('code=')) await handleRedirect();
    else {
        try { currentUser = await userManager.getUser(); if(!currentUser) await login(); else showSection(homeSection); } 
        catch { await login(); }
    }
})();
