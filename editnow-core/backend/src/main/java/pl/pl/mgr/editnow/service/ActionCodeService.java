package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.openide.util.MapFormat;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.domain.Action;
import pl.pl.mgr.editnow.domain.ActionChain;
import pl.pl.mgr.editnow.domain.ActionCode;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.dto.PythonLibrary;
import pl.pl.mgr.editnow.dto.action.ActionType;
import pl.pl.mgr.editnow.repository.ActionCodeRepository;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ActionCodeService {

  private static final String LOAD_IMAGE_OPEN_CV = "image = cv2.imread(\"path/to/your/image.jpg\")\n\n";
  private static final String NEW_LINE = "\n";

  private final UserService userService;
  private final ActionCodeRepository actionCodeRepository;

  public String generateCodeFromActionChain() {
    User user = userService.getUserFromContext();

    Set<Action> actions = user.getActionChain().getActions();

    StringBuilder generatedCode = new StringBuilder();
    boolean insertedLoadImage = false;
    Set<PythonLibrary> importedLibraries = new HashSet<>();

    for(Action action: actions) {
      ActionType actionType = action.getActionType();
      ActionCode actionCode = actionCodeRepository.findActionCodeByActionType(actionType);

      if ( !insertedLoadImage) {
        insertLoadImage(generatedCode, actionCode.getPythonLibraries());
        insertedLoadImage = true;
      }

      importedLibraries.addAll(actionCode.getPythonLibraries());  //add only unique libraries

      String code = prepareCode(actionCode, action.getParameters());

      //TODO convert image BGR -> RGB / RGB -> BGR for cv <-> scikit

      generatedCode.append("# Action name: ").append(actionType.name()).append(NEW_LINE);
      generatedCode.append(code).append(NEW_LINE);
    }

    insertImports(generatedCode, importedLibraries);
//    appendSaveImage(generatedCode, lastLibraryOpenCv);  //TODO

    return generatedCode.toString();
  }

  private void insertLoadImage(StringBuilder generatedCode, List<PythonLibrary> firstActionPythonLibraries) {
    String loadImageCode = firstActionPythonLibraries.contains(PythonLibrary.OPEN_CV)
      ? LOAD_IMAGE_OPEN_CV
      : "LOAD_IMAGE_BY_SCIKIT";

    generatedCode.append(loadImageCode);
  }

  private String prepareCode(ActionCode actionCode, Map<String, Integer> parameters) {
    String code = actionCode.getCode();

    if (parameters.size() > 0) {
      code = MapFormat.format(code, parameters);
    }

    return code;
  }

  private void insertImports(StringBuilder generatedCode, Set<PythonLibrary> importedLibraries) {
    StringBuilder generatedImports = new StringBuilder();
    importedLibraries.forEach(libraryImport ->
      generatedImports.append(libraryImport.importLine()).append(NEW_LINE));
    generatedImports.append(NEW_LINE);

    generatedCode.insert(0, generatedImports.toString());
  }

}
