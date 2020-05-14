package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.enums.Role;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

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
    Set<Message> messages = new HashSet<>();

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


    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();

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
                '}';
    }
}
