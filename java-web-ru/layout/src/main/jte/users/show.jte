<!-- BEGIN -->
@import exercise.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
        <div>
            <p><a href="/">Home</a></p>
            <p><a href="/users">Users</a></p>
            <h2>${page.getUser().getFirstName()} ${page.getUser().getLastName()}</h2>
            <p>${page.getUser().getEmail()}</p>
        </div>
    `
)
<!-- END -->
