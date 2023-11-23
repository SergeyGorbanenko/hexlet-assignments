<!-- BEGIN -->
@import exercise.dto.posts.PostsPage
@import exercise.util.NamedRoutes
@param PostsPage page

@template.layout.page(
page = page,
content = @`
    <h1>Посты</h1>
    @if(page.getPosts().isEmpty())
        <p>Пока не добавлено ни одного поста</p>
    @else
        @for(var post : page.getPosts())
            <div>
                <h2><a href="${NamedRoutes.postPath(post.getId())}">${post.getName()}</a></h2>
                <p>${post.getBody()}</p>
            </div>
        @endfor
    @endif
`
)
<!-- END -->
