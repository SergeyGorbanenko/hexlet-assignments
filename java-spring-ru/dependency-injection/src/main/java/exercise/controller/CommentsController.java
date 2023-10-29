package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
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

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> get() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getById(@PathVariable long id) {
        var curComment = commentRepository.findById(id);
        if (curComment.isPresent()) {
            return curComment.get();
        }
        throw new ResourceNotFoundException("Comment with id " + id + " not found");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment commentData) {
        return commentRepository.save(commentData);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable long id, @RequestBody Comment commentData) {
        var curComment = commentRepository.findById(id);
        if (curComment.isPresent()) {
            curComment.get().setBody(commentData.getBody());
            curComment.get().setPostId(commentData.getPostId());
            return commentRepository.save(curComment.get());
        }
        else throw new ResourceNotFoundException("Comment with id = " + id + " not found");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        commentRepository.delete(commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " not found")));
    }
}
// END
