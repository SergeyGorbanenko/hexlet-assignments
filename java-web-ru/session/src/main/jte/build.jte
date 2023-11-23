@import exercise.dto.LoginPage
@import exercise.util.NamedRoutes
@param LoginPage page

<!-- BEGIN -->
@template.layout.page(
    content = @`
        <form action="${NamedRoutes.loginPath()}" method="post">
            <input type="text" placeholder="Nickname" name="name" value="${page.getName()}"/>
            <input type="password" placeholder="Password" name="password" />
            <input type="submit" />
        </form>
        <p>${page.getError()}</p>
    `
)
<!-- END -->
