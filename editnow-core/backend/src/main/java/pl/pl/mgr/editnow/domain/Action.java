package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.domain.field.ActionStatus;

import javax.persistence.*;

@Entity
@Table(name="actions")
@Getter
@Setter
public class Action {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  //TODO handle actionname
  private String actionName;

  @OneToOne
  private Image inputImage;

  @OneToOne
  private Image outputImage;

  @Enumerated(EnumType.STRING)
  private ActionStatus status;

  @ManyToOne
//  @JoinColumn(name = "action_id")
  @JoinColumn
  private User user;

}
