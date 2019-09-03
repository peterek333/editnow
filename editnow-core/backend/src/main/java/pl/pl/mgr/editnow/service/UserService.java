package pl.pl.mgr.editnow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.pl.mgr.editnow.domain.User;
import pl.pl.mgr.editnow.repository.UserRepository;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public String generateUUID() {
    String generatedUUID = UUID.randomUUID().toString();

    User newUser = new User();
    newUser.setUuid(generatedUUID);
    userRepository.save(newUser);

    return generatedUUID;
  }

  public String getUserUUIDFromContext() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public User getUserFromContext() {
    String userUUIDFromContext = getUserUUIDFromContext();

    return userRepository.findByUuid(userUUIDFromContext);
  }
}
