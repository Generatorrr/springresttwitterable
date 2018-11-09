package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.InitialFrontendDataDTO;
import com.example.springresttwitterable.entity.dto.PageDTO;
import com.example.springresttwitterable.entity.mapper.MessageMapper;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            @AuthenticationPrincipal User user,
            @PageableDefault Pageable pageable
    ) {

        Page<Message> messages = messageRepository.findAll(pageable);
        Pageable pageInfo = messages.getPageable();
        InitialFrontendDataDTO initialFrontendDataDTO = new InitialFrontendDataDTO();
        initialFrontendDataDTO.setMessages(messageMapper.convertToList(messages));
        initialFrontendDataDTO.setProfile(userMapper.convertToInitialUserDTO(user));
        initialFrontendDataDTO.setPage(new PageDTO(messages.getTotalPages(), pageInfo.getPageNumber(), pageInfo.getPageSize()));
        model.addAttribute("frontendData", initialFrontendDataDTO);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
