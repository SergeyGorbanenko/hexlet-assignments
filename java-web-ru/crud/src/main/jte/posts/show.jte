<!-- BEGIN -->
@import exercise.dto.posts.PostPage
@import exercise.util.NamedRoutes
@param PostPage page

@template.layout.page(
    content = @`
        <h1>${page.getPost().getName()}</h1>
        <div>
            <h2>${page.getPost().getName()}</h2>
            <p>${page.getPost().getBody()}</p>
            <p><a href="${NamedRoutes.postsPath()}">Назад</a></p>
        </div>
    `
)

<!-- END -->
