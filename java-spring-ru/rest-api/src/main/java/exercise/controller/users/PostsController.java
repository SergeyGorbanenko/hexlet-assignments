package exercise.controller.users;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
public class PostsController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> getPosts(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(posts.size()))
            .body(posts.stream()
                .skip((long)(page-1) * limit)
                .limit(limit)
                .toList());
    }

    @GetMapping("/api/users/{id}/posts")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable int id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit){
        return ResponseEntity.ok()
            .body(posts.stream()
                .filter(post -> post.getUserId() == id)
                .skip((long)(page-1) * limit)
                .limit(limit)
                .toList());
    }

    @PostMapping("/api/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@PathVariable int id, @RequestBody Post post) {
        var newPost = new Post();
        newPost.setSlug(post.getSlug());
        newPost.setTitle(post.getTitle());
        newPost.setBody(post.getBody());
        newPost.setUserId(id);
        posts.add(newPost);
        return newPost;
    }
}
// END
