<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Greeting</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</head>
<body>
    <!-- Navbar -->
    <nav>
        <div class="nav-wrapper blue">
            <a href="/" class="brand-logo" style="padding-left: 10px;">SpringSport</a>
            <ul class="right">
                <li><a href="users">Users</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container" style="margin-top: 50px;">
        <h4 class="section">${message}</h4>
    </div>
</body>
</html>
