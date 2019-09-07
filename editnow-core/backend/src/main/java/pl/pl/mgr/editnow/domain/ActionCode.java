package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.PythonLibrary;
import pl.pl.mgr.editnow.dto.action.ActionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="actionCodes")
@Getter
@Setter
public class ActionCode {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<PythonLibrary> pythonLibraries;

  @Enumerated(EnumType.STRING)
  private ActionType actionType;

  private String code;

}
