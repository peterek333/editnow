package pl.pl.mgr.editnow.configuration.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.repository.UserRepository;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class UUIDCookieFilter implements Filter {

  private static final Logger LOGGER = Logger.getLogger(UUIDCookieFilter.class.getName());

  private static final String USER_UUID_COOKIE = "userUUID";
  private final UserRepository userRepository;

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      LOGGER.log(Level.INFO, "Request on path: " + request.getRequestURI());
      setUUIDFromCookiesInContext(cookies);
    }

    chain.doFilter(request, resp);
  }

  private void setUUIDFromCookiesInContext(Cookie[] cookies) {
    Arrays.stream(cookies)
      .filter(cookie -> cookie.getName().equals(USER_UUID_COOKIE))
      .findFirst()
      .ifPresent(uuidCookie -> {
        String uuid = uuidCookie.getValue();

        LOGGER.log(Level.INFO, "Request from user: " + uuid);

        addToDatabaseIfNotExist(uuid);

        UUIDAuthentication uuidAuthentication = new UUIDAuthentication(uuid);
        SecurityContextHolder.getContext().setAuthentication(uuidAuthentication);
      });
  }

  @Transactional
  void addToDatabaseIfNotExist(String uuid) {
    User userFromDb = userRepository.findByUuid(uuid);

    if (userFromDb == null) {
      User user = new User();
      user.setUuid(uuid);

      userRepository.save(user);
    }
  }

}
