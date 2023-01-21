package mt.rest;

import mt.exception.NotFoundException;
import mt.exception.ValidationException;
import mt.model.Post;
import mt.model.dto.PostDto;
import mt.rest.util.RestUtils;
import mt.service.AdminService;
import mt.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rest/posts")
public class PostController {


    private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);

    private final PostService postService;
    private final AdminService adminService;

    @Autowired
    public PostController(PostService postService, AdminService adminService) {
        this.postService = postService;
        this.adminService = adminService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getPosts() {
        return postService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPost(@RequestBody PostDto postdto) {
        Post post = new Post(postdto.getTitle(), postdto.getPostBody(), adminService.find(postdto.getAdminId()));
        postService.persist(post);
        LOG.debug("Created post {}.", post);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", post.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post getById(@PathVariable Long id) {
        final Post post = postService.find(id);
        if (Objects.equals(post, null)) {
            throw NotFoundException.createException("Ticket", id);
        }
        return post;
    }

    @PutMapping(value = "/update-{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateTicket(@PathVariable Long id, @RequestBody Post post) {
        final Post original = getById(id);
        if (!original.getId().equals(post.getId())) {
            throw new ValidationException("Post identifier in the data does not match the one in the request URL.");
        }
        postService.update(post);
        LOG.debug("Updated ticket {}.", post);
    }

    @DeleteMapping(value = "/delete-{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTicket(@PathVariable Long id) {
        final Post toRemove = postService.find(id);
        if (toRemove == null) {
            return;
        }
        postService.remove(toRemove);
        LOG.debug("Removed ticket {}.", toRemove);
    }
}
