package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.InitialFrontendDataDTO;
import com.example.springresttwitterable.entity.mapper.MessageMapper;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLDocument;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(value="Main API controller", description="Operations with messages, media files etc.")
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;
    
    @Value("${spring.profiles.active}")
    private String profile;

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;

    public MainController(MessageRepository messageRepository, MessageMapper messageMapper,
            UserMapper userMapper)
    {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    @ApiOperation(value = "Get greeting page", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return needed page")
        }
    )
    @GetMapping
    @RequestMapping("/")
    public String greeting(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        
        InitialFrontendDataDTO initialFrontendDataDTO = new InitialFrontendDataDTO();
        initialFrontendDataDTO.setMessages(messageMapper.convert((List<Message>) messageRepository.findAll()));
        initialFrontendDataDTO.setProfile(userMapper.convert(user));
        
        model.addAttribute("frontendData", initialFrontendDataDTO);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }

    @GetMapping("user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {

        Set<Message> messages = user.getMessages();
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscribtions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }

//    @PostMapping("user-messages/{user}")
//    public String updateMessage(
//            @AuthenticationPrincipal User currentUser,
//            @PathVariable Long user,
//            @RequestParam("id") Message message,
//            @RequestParam String text,
//            @RequestParam String tag,
//            @RequestParam("file") MultipartFile file
//    ) throws IOException {
//        if (message.getAuthor().equals(currentUser)) {
//            if(!StringUtils.isEmpty(text)) {
//                message.setText(text);
//            }
//            if(!StringUtils.isEmpty(tag)) {
//                message.setTag(tag);
//            }
//            saveFile(message, file);
//            message.setEdited(true);
//            messageRepository.save(message);
//        }
//
//        return "redirect:/user-messages" + user;
//    }

}
