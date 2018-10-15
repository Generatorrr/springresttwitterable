package com.example.springresttwitterable.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Data;

@Entity
@Table(name = "account")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
public class User implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    @Id
    // We don't need generate this value cause we are going to use Ids from Google
    private String id;

    @NotBlank(message = "Username can't be empty")
    private String name;
    
    @Column(name = "userpic")
    private String userpic;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "locale")
    private String locale;
    
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;
    
    @Column(name = "email")
    private String email;

    // FetchType.LAZY by default
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Message> messages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_subscriptions",
            joinColumns = { @JoinColumn(name = "subscriber_id") },
            inverseJoinColumns = { @JoinColumn(name = "channel_id") }
    )
    private Set<User> subscribtions = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_subscriptions",
            joinColumns = { @JoinColumn(name = "channel_id") },
            inverseJoinColumns = { @JoinColumn(name = "subscriber_id") }
    )
    private Set<User> subscribers = new HashSet<>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserpic()
    {
        return userpic;
    }

    public void setUserpic(String userpic)
    {
        this.userpic = userpic;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public LocalDateTime getLastVisit()
    {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit)
    {
        this.lastVisit = lastVisit;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    public Set<User> getSubscribtions()
    {
        return subscribtions;
    }

    public void setSubscribtions(Set<User> subscribtions)
    {
        this.subscribtions = subscribtions;
    }

    public Set<User> getSubscribers()
    {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers)
    {
        this.subscribers = subscribers;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(lastVisit, user.lastVisit) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(id, lastVisit, email);
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userpic='" + userpic + '\'' +
                ", gender='" + gender + '\'' +
                ", locale='" + locale + '\'' +
                ", lastVisit='" + lastVisit + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", messages=" + messages +
                '}';
    }
}
