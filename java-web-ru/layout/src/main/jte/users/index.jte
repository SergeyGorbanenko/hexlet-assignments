<!-- BEGIN -->
@import exercise.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
    content = @`
        @if(page.getUsers().isEmpty())
            <p>Пока нет ни одного пользователя</p>
        @else
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">id</th>
                    <th scope="col">FirstName LastName</th>
                    <th scope="col">Email</th>
                </tr>
                </thead>
                <tbody>
                @for(var user : page.getUsers())
                    <tr>
                        <th scope="row">${user.getId()}</th>
                        <td><a href="/users/${user.getId()}">${user.getFirstName()} ${user.getLastName()}</a></td>
                        <td>${user.getEmail()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        @endif
    `
)
<!-- END -->
