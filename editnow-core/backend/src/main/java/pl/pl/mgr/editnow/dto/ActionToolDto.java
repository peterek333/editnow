package pl.pl.mgr.editnow.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pl.mgr.editnow.dto.configuration.ParameterInfoDto;
import java.util.List;

@Getter
@Setter
public class ActionToolDto {

  private String name;
  private List<ParameterInfoDto> parameterInfoDtos;

}