package bjr.spring.login.Entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private UserRole name;

  public Role() {

  }

  public Role(UserRole name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public UserRole getName() {
    return name;
  }

  public void setName(UserRole name) {
    this.name = name;
  }
}