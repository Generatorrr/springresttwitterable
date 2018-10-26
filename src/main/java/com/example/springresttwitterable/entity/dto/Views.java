package com.example.springresttwitterable.entity.dto;

/**
 * Interfaces for JSONView usage in DTO classes.
 * <p>
 * Created on 10/26/18.
 * <p>
 * @author Vlad Martinkov
 */
public final class Views
{
    // Message views
    public interface MessageDTO {}
    public interface MessageToSaveDTO extends MessageDTO {}
    public interface MessageForListDTO extends MessageDTO {}
    public interface FullMessageDTO extends MessageDTO {}
    
    // User views
    public interface UserDTO {}
    public interface UserForUpdateDTO extends UserDTO {}
    public interface MessageAuthorDTO extends UserDTO {}
    public interface UserInitialFEDTO extends MessageAuthorDTO {}
    
    public interface NotPassUserData {}
    
    public interface InitialFEData {}
    
    
}
