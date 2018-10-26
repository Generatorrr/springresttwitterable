package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Message implements Serializable
{

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private Long id;

    @NotBlank(message = "Please, fill the message")
    @Length(max = 2048, message = "Message too long (more than 2 kB)")
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private String text;
    
    @Length(max = 255, message = "Tag is too long")
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private User author;

    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private String filename;

    @JsonView({Views.MessageDTO.class, Views.InitialFEData.class})
    private boolean edited;

    public Message() {

    }

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {

        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthorName() {
        return null != this.author ? this.author.getName() : "No Author";
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
