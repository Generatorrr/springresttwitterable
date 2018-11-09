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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.swing.text.html.HTMLDocument;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(value="Main API controller", description="Serves like entry point to return main template.")
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
        initialFrontendDataDTO.setProfile(userMapper.convertToInitialUserDTO(user));
        
        model.addAttribute("frontendData", initialFrontendDataDTO);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
