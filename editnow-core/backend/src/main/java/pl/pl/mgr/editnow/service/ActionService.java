package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.field.ActionStatus;
import pl.pl.mgr.editnow.repository.ActionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ActionService {

  private final ActionRepository actionRepository;

  public Action completeAction(long actionId) {
    Action action = actionRepository.findById(actionId);
    action.setStatus(ActionStatus.COMPLETED);

    return action;
  }

}
