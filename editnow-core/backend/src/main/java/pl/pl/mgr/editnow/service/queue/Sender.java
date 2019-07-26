package pl.pl.mgr.editnow.service.queue;

import pl.pl.mgr.editnow.service.ImageService;

public interface Sender<T> {

  public void send(T data);

}
