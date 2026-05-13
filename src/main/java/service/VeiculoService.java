package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.VeiculoRepository;

@Service
public class VeiculoService {

  @Autowired
  private VeiculoRepository veiculoRepository;

}
