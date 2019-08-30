package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="actionChains")
@Getter
@Setter
public class ActionChain {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToMany(fetch = FetchType.EAGER) //, orphanRemoval = true)
  @JoinColumn(name = "actionChain_id")
  @OrderBy  //("id ASC")
  private Set<Action> actions;

}
