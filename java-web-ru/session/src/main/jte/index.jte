@import exercise.dto.MainPage
@import exercise.util.NamedRoutes
@param MainPage page

<!-- BEGIN -->
@template.layout.page(
    content = @`
        <main>
            <h1>Sessions</h1>
            @if(page.getName()!=null)
                Добро пожаловать, ${String.valueOf(page.getName())}.
                <form action="${NamedRoutes.logoutPath()}" method="post">
                    <input type="submit" value="Logout"/>
                </form>
            @else
                <p>
                    <a href="${NamedRoutes.buildSessionPath()}">Login</a>
                </p>
            @endif
        </main>
    `
)
<!-- END -->
