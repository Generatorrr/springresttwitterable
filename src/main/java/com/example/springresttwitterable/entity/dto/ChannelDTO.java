package com.example.springresttwitterable.entity.dto;

import com.example.springresttwitterable.entity.dto.message.ListMessageDTO;
import com.example.springresttwitterable.entity.dto.user.UserAuthorDTO;

import java.util.List;

import lombok.Data;

/**
 * DTO for response to user channel page;
 * Created on 10/30/18.
 * <p>
 * @author Vlad Martinkov
 */

@Data
public class ChannelDTO
{
    
    private List<ListMessageDTO> messages;
    private UserAuthorDTO userChannel;
    private int subscriptionsCount;
    private int subscribersCount;
    private boolean isSubscriber;
    private boolean isCurrentUser;
}
