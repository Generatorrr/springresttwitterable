/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.message.ListMessageDTO;
import com.example.springresttwitterable.entity.dto.message.MessageDTO;
import com.example.springresttwitterable.entity.mapper.MessageMapper;
import com.example.springresttwitterable.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.swing.text.html.HTMLDocument;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * TODO: write a brief summary fragment.
 * <p>
 * TODO: write a detailed description.
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

    public MessageController(MessageRepository messageRepository, MessageMapper messageMapper)
    {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
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

    @PostMapping
    public ResponseEntity addMessage
            (
                    @AuthenticationPrincipal User user,
                    @Valid @RequestBody MessageDTO messageDto
            ) throws IOException
    {
        Message message = new Message();   
        message.setText(messageDto.getText());
        message.setTag(messageDto.getTag());
        message.setAuthor(user);
        saveFile(message, messageDto.getFile());
        messageRepository.save(message);

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
