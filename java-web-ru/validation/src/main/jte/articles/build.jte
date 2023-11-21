<!-- BEGIN -->
@import exercise.dto.articles.NewArticlePage
@param NewArticlePage page

@template.layout.page(
content = @`
    @if(page.getErrors() != null)
        <ul>
            @for(var validator : page.getErrors().values())
                @for(var error : validator)
                    <li>${error.getMessage()}</li>
                @endfor
            @endfor
        </ul>
    @endif
    <form action="/articles" method="post">
        <div>
            <label>
                Название
                <input type="text" required name="title" value="${page.getTitle()}" />
            </label>
        </div>
        <div>
            <label>
                Контент
                <input type="textarea" required name="content"  value="${page.getContent()}" />
            </label>
        </div>
        <input type="submit" value="Создать" />
    </form>
`
)
<!-- END -->
