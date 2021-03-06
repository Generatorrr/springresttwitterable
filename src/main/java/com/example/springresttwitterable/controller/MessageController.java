/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.ChannelDTO;
import com.example.springresttwitterable.entity.dto.message.ListMessageDTO;
import com.example.springresttwitterable.entity.dto.message.NewMessageDTO;
import com.example.springresttwitterable.entity.dto.message.UpdateMessageDTO;
import com.example.springresttwitterable.entity.mapper.MessageMapper;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.swing.text.html.HTMLDocument;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for work with {@link Message} entity.
 * <p>
 * Created on 10/25/18.
 * <p>
 * @author Vlad Martinkov
 */

@RestController
@Api(value="Message API", description="Operations with messages")
@RequestMapping("/message")
public class MessageController
{

    @Value("${upload.path}")
    private String uploadPath;

    
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;

    public MessageController(MessageRepository messageRepository, MessageMapper messageMapper,
            UserMapper userMapper)
    {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    @ApiOperation(value = "Get messages", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return messages"),
            @ApiResponse(code = 403, message = "Forbidden")
            }
    )
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ListMessageDTO>> main(
            @RequestParam(required = false, defaultValue = "") String filter
    ) {

        List<Message> toResponse;
        if (null != filter && !filter.isEmpty()) {
            toResponse = messageRepository.findByTag(filter);
        } else {
            toResponse = (List<Message>) messageRepository.findAll();
        }
        
        return new ResponseEntity<>(messageMapper.convert(toResponse), HttpStatus.OK);
    }

    @GetMapping("user/{user}")
    public ResponseEntity<ChannelDTO> userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setMessages(messageMapper.convert(user.getMessages()));
        channelDTO.setUserChannel(userMapper.convertToAuthorDTO(user));
        channelDTO.setSubscriptionsCount(user.getSubscribtions().size());
        channelDTO.setSubscribersCount(user.getSubscribers().size());
        channelDTO.setSubscriber(user.getSubscribers().contains(currentUser));
        channelDTO.setCurrentUser(currentUser.equals(user));
        return new ResponseEntity<>(channelDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addMessage
            (
                    @AuthenticationPrincipal User user,
                    @Valid NewMessageDTO messageDto
            ) throws IOException {
        
        Message message = new Message();   
        message.setText(messageDto.getText());
        message.setTag(messageDto.getTag());
        message.setAuthor(user);
        saveFile(message, messageDto.getFile());
        messageRepository.save(message);

        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateMessage(
            @AuthenticationPrincipal User currentUser,
            @Valid UpdateMessageDTO messageDTO
    ) throws IOException {
        Optional<Message> optionalMessage = messageRepository.findById(messageDTO.getId());
        if (!optionalMessage.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Message message = optionalMessage.get();
        if (message.getAuthor().equals(currentUser)) {
            if(!StringUtils.isEmpty(messageDTO.getText())) {
                message.setText(messageDTO.getText());
            }
            if(!StringUtils.isEmpty(messageDTO.getTag())) {
                message.setTag(messageDTO.getTag());
            }
            saveFile(message, messageDTO.getFile());
            message.setEdited(true);
            messageRepository.save(message);
        }

        return ResponseEntity.ok().build();
    }

    private void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
        if (null != file && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename= uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }
    
}
