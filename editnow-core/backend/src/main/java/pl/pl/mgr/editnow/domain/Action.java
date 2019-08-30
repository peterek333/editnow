package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.domain.field.ActionStatus;
import pl.pl.mgr.editnow.dto.ActionType;

import javax.persistence.*;

@Entity
@Table(name="actions")
@Getter
@Setter
public class Action {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  //TODO handle actionname
  private String actionName;

  private ActionType actionType;

  @OneToOne
  private Image inputImage;

  @OneToOne
  private Image outputImage;

  @Enumerated(EnumType.STRING)
  private ActionStatus status;

  @ManyToOne
  @JoinColumn
  private User user;

}
