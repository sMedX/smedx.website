package app.core.DB.DataModel;


import com.fasterxml.jackson.annotation.JsonView;
import app.core.Utils.JsonViews;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "cred")
    private String cred;


    @Column(name = "mail")
    private String mail;

    /*@Column(name = "messenger")
    private String messenger;

    @Column(name = "messenger_type")
    private String messengerType;*/

    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(String firstName, String lastName, String mail, String cred, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.cred = cred;
        //this.messenger = messenger;
        //this.messengerType = messengerType;
        this.role = role;
    }

    @JsonView(JsonViews.UserJsonView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(JsonViews.UserJsonView.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(JsonViews.UserJsonView.class)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonView(JsonViews.UserJsonView.class)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCred() {
        return cred;
    }

    public void setCred(String cred) {
        this.cred = cred;
    }

    @JsonView(JsonViews.UserJsonView.class)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

   /* @JsonView(JsonViews.UserJsonView.class)
    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    @JsonView(JsonViews.UserJsonView.class)
    public String getMessengerType() {
        return messengerType;
    }

    public void setMessengerType(String messengerType) {
        this.messengerType = messengerType;
    }*/

    @JsonView(JsonViews.UserJsonView.class)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
