package ru.neoflex.entity;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public", catalog = "creditCalc")
@NamedQueries({
        @NamedQuery(name = "findAllUsers", query = "SELECT u FROM User u"),
        @NamedQuery(name = "findByLoginAndPassword", query = "SELECT u FROM User u WHERE u.userLogin = :login AND u.userPsw = :psw")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    @Getter @Setter
    private Integer idUser;
    @Basic(optional = false)
    @Column(name = "user_login")
    @Getter @Setter
    private String userLogin;
    @Basic(optional = false)
    @Column(name = "user_psw")
    @Getter @Setter
    private String userPsw;
    @Basic(optional = false)
    @Column(name = "user_name")
    @Getter @Setter
    private String userName;
    @Basic(optional = false)
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UserRole userRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (idUser != that.idUser) return false;
        if (userLogin != null ? !userLogin.equals(that.userLogin) : that.userLogin != null) return false;
        if (userPsw != null ? !userPsw.equals(that.userPsw) : that.userPsw != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userRole != null ? !userRole.equals(that.userRole) : that.userRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + (userPsw != null ? userPsw.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }
}
