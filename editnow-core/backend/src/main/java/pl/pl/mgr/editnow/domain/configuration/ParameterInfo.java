package pl.pl.mgr.editnow.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.configuration.ParameterType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  //TODO usunac??
@Embeddable
public class ParameterInfo {

  private String name;

  @Enumerated(EnumType.STRING)
  private ParameterType parameterType;

}
