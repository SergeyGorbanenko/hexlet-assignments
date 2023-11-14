<!-- BEGIN -->
@import exercise.dto.users.UsersPage
@param UsersPage page

<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
          crossorigin="anonymous">
    <title>Пользователи</title>
</head>
<body>
@if(page.getUsers().isEmpty())
    <p>Пока нет ни одного пользователя</p>
@else
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">id</th>
            <th scope="col">FirstName</th>
            <th scope="col">LastName</th>
            <th scope="col">Email</th>
        </tr>
        </thead>
        <tbody>
            @for(var user : page.getUsers())
                <tr>
                    <th scope="row">${user.getId()}</th>
                    <td><a href="/users/${user.getId()}">${user.getFirstName()}</a></td>
                    <td>${user.getLastName()}</td>
                    <td>${user.getEmail()}</td>
                </tr>
            @endfor
        </tbody>
    </table>
@endif
</body>
</html>
<!-- END -->
