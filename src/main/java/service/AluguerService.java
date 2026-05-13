package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.AluguerRepository;

@Service
public class AluguerService {

  @Autowired
  private AluguerRepository aluguerRepository;

}
