package pl.pl.mgr.editnow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.domain.field.ActionStatus;
import pl.pl.mgr.editnow.dto.ActionType;
import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name="actions")
@Getter
@Setter
public class Action {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private ActionType actionType;

  @OneToOne
  private Image inputImage;

  @OneToOne
  private Image outputImage;

  @Enumerated(EnumType.STRING)
  private ActionStatus status;

  @ElementCollection
  private Map<String, Integer> parameters;

  @ManyToOne
  @JoinColumn
  @JsonIgnore
  private User user;

}
