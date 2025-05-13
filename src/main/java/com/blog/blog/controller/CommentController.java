package com.blog.blog.controller;

import com.blog.blog.dto.CommentDto;
import com.blog.blog.dto.PostDto;
import com.blog.blog.repository.UserRepository;
import com.blog.blog.service.CommentService;
import com.blog.blog.service.PostService;
import com.blog.blog.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private CommentService commentService;
    private PostService postService;
    private UserRepository userRepository;
    public CommentController(CommentService commentService, PostService postService,UserRepository userRepository) {
        this.commentService = commentService;
        this.postService = postService;
        this.userRepository = userRepository;
    }


    @PostMapping("/post/{postUrl}/comments")
    public String createComment(@PathVariable("postUrl") String postUrl,
                                Model model,
                                @Valid @ModelAttribute("comment") CommentDto commentDto,
                                BindingResult result)
    {
        PostDto postDto = postService.findPostByUrl(postUrl);
        if(result.hasErrors())
        {
            System.out.println("error");
            model.addAttribute("post",postDto);
            model.addAttribute("comment",commentDto);
            return "blog/blog_post";
        }
        commentService.createComment(postUrl,commentDto);
        return "redirect:/post/"+postUrl;
    }


}
