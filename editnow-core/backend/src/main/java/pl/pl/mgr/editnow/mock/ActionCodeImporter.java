package pl.pl.mgr.editnow.mock;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pl.pl.mgr.editnow.domain.ActionCode;
import pl.pl.mgr.editnow.dto.PythonLibrary;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.repository.ActionCodeRepository;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ActionCodeImporter {

  private static final Logger LOGGER = Logger.getLogger(ActionCodeImporter.class.getName());
  private static final String FUNCTIONS_CODE_FILE_PATH = "code/actions-code.txt";
  private static final String ACTION_CODE_START_SIGN = "#";

  private final Map<ActionType, ActionCode> actionCodes = createActionCodes();

  @SuppressWarnings("unchecked")
  private Map<ActionType, ActionCode> createActionCodes() {
    Object[][] actionWithLibraries = {
      {ActionType.GRAYSCALE, Collections.singletonList(PythonLibrary.OPEN_CV)},
      {ActionType.MORPHOLOGY_TRANSFORM, Arrays.asList(PythonLibrary.OPEN_CV, PythonLibrary.NUMPY)},
    };

    return Stream.of(actionWithLibraries)
      .collect(Collectors.toMap(
        actionData -> (ActionType) actionData[0],
        actionData -> {
          ActionCode actionCode = new ActionCode();
          actionCode.setActionType((ActionType) actionData[0]);
          actionCode.setPythonLibraries((List<PythonLibrary>) actionData[1]);

          return actionCode;
        }));
  }

  private final ActionCodeRepository actionCodeRepository;

  public void importActionCodes() {
    Resource resource = new ClassPathResource(FUNCTIONS_CODE_FILE_PATH);

    try {
      try ( BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream())) ) {

        fillActionCodes(reader.lines());

        LOGGER.log(Level.INFO, "Action codes import completed");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void fillActionCodes(Stream<String> lines) {
    AtomicReference<ActionType> actualActionType = new AtomicReference<>();
    StringBuilder code = new StringBuilder();

    lines.forEach(codeLine -> {
      if (codeLine.startsWith(ACTION_CODE_START_SIGN)) {  //found start of next action code
        if (actualActionType.get() != null) { //save previous code
          saveActionCode(actualActionType.get(), code.toString());
        }

        actualActionType.set(ActionType.valueOf(  //point to actual action type
          codeLine.substring(1).replaceAll("\\s+|", "")));  //format "# GRAYSCALE" to "GRAYSCALE"

        code.setLength(0);  //maybe slower than new StringBuilder but avoids AtomicReference for this field
      } else if ( !codeLine.isEmpty()) {  //if is not empty line - add line to code
        code.append(codeLine).append("\n");
      }
    });
    saveActionCode(actualActionType.get(), code.toString()); //save last action
  }

  private void saveActionCode(ActionType actionType, String code) {
    ActionCode actionCode = actionCodes.get(actionType);
    actionCode.setCode(code);

    actionCodeRepository.save(actionCode);
  }

}
