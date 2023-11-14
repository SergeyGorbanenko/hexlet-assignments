<!-- BEGIN -->
@import exercise.dto.users.UserPage
@param UserPage page

<html>
<head>
    <title>${page.getUser().getFirstName()}</title>
</head>
<body>
<h1>${page.getUser().getFirstName()}</h1>
<div>
    <h2>${page.getUser().getFirstName()}</h2>
    <p>${page.getUser().getLastName()}</p>
    <p>${page.getUser().getEmail()}</p>
</div>
</body>
</html>
<!-- END -->
