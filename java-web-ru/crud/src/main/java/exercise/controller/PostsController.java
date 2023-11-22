package exercise.controller;

import java.util.Collections;
import exercise.dto.posts.PostsPage;
import exercise.dto.posts.PostPage;
import exercise.repository.PostRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class PostsController {

    // BEGIN
    public static void index(Context ctx) {
        Long pageNumber = ctx.queryParamAsClass("page", Long.class).getOrDefault(1L);
        if (pageNumber < 1L)
            pageNumber = 1L;
        int limit = 5;
        var posts = PostRepository.getEntities()
            .stream()
            .skip((pageNumber - 1) * limit)
            .limit(limit)
            .toList();
        var page = new PostsPage(posts, pageNumber);
        ctx.render("posts/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var post = PostRepository.find(id)
            .orElseThrow(() -> new NotFoundResponse("Page not found"));
        var page = new PostPage(post);
        ctx.render("posts/show.jte", Collections.singletonMap("page", page));
    }
    // END
}
