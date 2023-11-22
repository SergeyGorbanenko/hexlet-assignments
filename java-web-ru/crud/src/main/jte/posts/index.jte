<!-- BEGIN -->
@import exercise.dto.posts.PostsPage
@import exercise.util.NamedRoutes
@param PostsPage page

@template.layout.page(
    content = @`
        <h1>Posts</h1>
        @if(page.getPosts().isEmpty())
            <p>Пока не добавлено ни одного поста</p>
        @else
            @for(var post : page.getPosts())
                <div>
                    <h2><a href="${NamedRoutes.postPath(post.getId())}">${post.getName()}</a></h2>
                </div>
            @endfor
        @endif
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="${NamedRoutes.postsPageablePath(page.getPageNumber() - 1)}">Previous</a></li>
                <li class="page-item"><a class="page-link" href="${NamedRoutes.postsPageablePath(page.getPageNumber() + 1)}">Next</a></li>
            </ul>
        </nav>
    `
)
<!-- END -->
