package pl.pl.mgr.editnow.mapper;

import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.configuration.ParameterInfo;
import pl.pl.mgr.editnow.dto.configuration.ParameterInfoDto;

@Component
public class ParameterInfoDtoMapper implements Mapper<ParameterInfo, ParameterInfoDto> {

  @Override
  public ParameterInfoDto map(ParameterInfo parameterInfo) {
    ParameterInfoDto parameterInfoDto = new ParameterInfoDto();
    parameterInfoDto.setName(parameterInfo.getName());
    parameterInfoDto.setParameterType(parameterInfo.getParameterType());

    return parameterInfoDto;
  }

}
