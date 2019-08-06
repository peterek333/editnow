package pl.pl.mgr.editnow.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uuid;

//    @OneToMany(mappedBy = "actionId")
//    @JoinColumn(name="action_id", referencedColumnName="id")
//    @OrderColumn(name="")

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private transient List<Action> actionChain;

}
