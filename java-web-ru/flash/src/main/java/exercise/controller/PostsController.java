package exercise.controller;

import java.util.Collections;
import java.util.List;

import exercise.dto.posts.PostsPage;
import exercise.dto.posts.PostPage;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.dto.posts.BuildPostPage;
import exercise.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import io.javalin.http.NotFoundResponse;

public class PostsController {

    public static void build(Context ctx) {
        var page = new BuildPostPage();
        ctx.render("posts/build.jte", Collections.singletonMap("page", page));
    }

    // BEGIN
    public static void index(Context ctx) {
        var posts = PostRepository.getEntities();
        var page = new PostsPage(posts);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("posts/index.jte", Collections.singletonMap("page", page));
    }

    public static void create(Context ctx) {
        try {
            var name = ctx.formParamAsClass("name", String.class)
                .check(value -> value.length() > 2, "Name must be contain more than 2 letters")
                .get();
            var body = ctx.formParamAsClass("body", String.class).get();
            var post = new Post(name, body);
            PostRepository.save(post);
            ctx.sessionAttribute("flash", "Post has been success created!");
            ctx.status(201).redirect(NamedRoutes.postsPath());
        } catch (ValidationException e) {
            /*var name = ctx.formParam("name");
            var description = ctx.formParam("description");
            var page = new BuildCoursePage(null, name, description, e.getErrors());
            page.setFlash("Error occurred during course created");
            ctx.render("courses/new.jte", Collections.singletonMap("page", page)).status(422);*/
        }
    }
    // END

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var post = PostRepository.find(id)
            .orElseThrow(() -> new NotFoundResponse("Post not found"));

        var page = new PostPage(post);
        ctx.render("posts/show.jte", Collections.singletonMap("page", page));
    }
}
