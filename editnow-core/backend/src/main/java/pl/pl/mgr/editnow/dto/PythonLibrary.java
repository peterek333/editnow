package pl.pl.mgr.editnow.dto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum PythonLibrary {

  OPEN_CV("cv2", null),
  NUMPY("numpy", "np")
  ;

  private static final String KEYWORD_IMPORT = "import ";
  private static final String KEYWORD_AS = " as ";

  private final String name;
  private final String alias;

  public String importLine() {
    StringBuilder importLine = new StringBuilder(KEYWORD_IMPORT);
    importLine.append(name);
    if (alias != null) {
      importLine.append(KEYWORD_AS).append(alias);
    }

    return importLine.toString();
  }

}
