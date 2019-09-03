package pl.pl.mgr.editnow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActionQueueItem {

  private final long actionId;
  private final String actionName;
  private final String inputImageName;
  private final String outputImageName;
  private final String imageBase64;

}
