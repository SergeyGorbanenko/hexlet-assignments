<!-- BEGIN -->
@import gg.jte.Content
@import exercise.dto.BasePage
@param Content content
@param BasePage page = null

<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
          crossorigin="anonymous">
    <title>Посты</title>
</head>
<body>
    @if(page != null && page.getFlash() != null)
        @if(page.getFlash().toLowerCase().contains("success"))
            <div class="alert alert-success" role="alert">
                <p>${page.getFlash()}</p>
            </div>
        @endif
        @if(page.getFlash().toLowerCase().contains("error"))
            <div class="alert alert-danger" role="alert">
                <p>${page.getFlash()}</p>
            </div>
        @endif
    @endif
    ${content}
</body>
</html>


<!-- END -->
