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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ActionCodeService {

  private static final String LOAD_IMAGE_OPEN_CV = "image = cv2.imread(\"path/to/your/image.jpg\")\n\n";
  private static final String NEW_LINE = "\n";

  private final UserService userService;
  private final ActionCodeRepository actionCodeRepository;

  public String generateCodeFromActionChain() {
    User user = userService.getUserFromContext();

    ActionChain actionChain = user.getActionChain();
    Set<Action> actions = actionChain.getActions();

    StringBuilder generatedCode = new StringBuilder();
    AtomicReference<AtomicBoolean> firstLibraryOpenCv = new AtomicReference<>();
    Set<PythonLibrary> librariesImport = new HashSet<>();

    actions.forEach(action -> {
      ActionType actionType = action.getActionType();
      ActionCode actionCode = actionCodeRepository.findActionCodeByActionType(actionType);
      if (firstLibraryOpenCv.get() == null) {
        firstLibraryOpenCv.set(
          new AtomicBoolean(actionCode.getPythonLibraries().contains(PythonLibrary.OPEN_CV)));
      }

      Map<String, Integer> parameters = action.getParameters();

      String code = actionCode.getCode();
      if (parameters.size() > 0) {
        code = MapFormat.format(code, parameters);
      }

      librariesImport.addAll(actionCode.getPythonLibraries());  //add only unique libraries

      //convert image BGR -> RGB / RGB -> BGR for cv <-> scikit

      generatedCode.append("# Action name: ").append(actionType.name()).append(NEW_LINE);
      generatedCode.append(code).append(NEW_LINE);
    });
    if (firstLibraryOpenCv.get() != null) {
      insertLoadImage(generatedCode, firstLibraryOpenCv.get());
    }
    insertImports(generatedCode, librariesImport);
    //insert load image by opencv depend on firstly used

    return generatedCode.toString();
  }

  private void insertLoadImage(StringBuilder generatedCode, AtomicBoolean atomicBoolean) {
    generatedCode.insert(0, atomicBoolean.get() ? LOAD_IMAGE_OPEN_CV : "LOAD_IMAGE_BY_SCIKIT");
  }

  private void insertImports(StringBuilder generatedCode, Set<PythonLibrary> librariesImport) {
    StringBuilder generatedImports = new StringBuilder();
    librariesImport.forEach(libraryImport ->
      generatedImports.append(libraryImport.importLine()).append(NEW_LINE));
    generatedImports.append(NEW_LINE);

    generatedCode.insert(0, generatedImports.toString());
  }

}
