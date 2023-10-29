package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Post> get() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        var curPost = postRepository.findById(id);
        if (curPost.isPresent()) {
            return curPost.get();
        }
        throw new ResourceNotFoundException("Post with id " + id + " not found");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post postData) {
        return postRepository.save(postData);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable long id, @RequestBody Post postData) {
        var curPost = postRepository.findById(id);
        if (curPost.isPresent()) {
            curPost.get().setBody(postData.getBody());
            curPost.get().setTitle(postData.getTitle());
            return postRepository.save(curPost.get());
        }
        else throw new ResourceNotFoundException("Post with id = " + id + " not found");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " not found"));
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }
}
// END
