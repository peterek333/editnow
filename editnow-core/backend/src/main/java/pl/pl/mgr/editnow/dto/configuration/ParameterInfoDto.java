package pl.pl.mgr.editnow.dto.configuration;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ParameterInfoDto {

  private String name;
  private ParameterType parameterType;
  private List<ParameterInfoOptionDto> parameterInfoOptionDtos;

}
