package ru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Serv {
    @Autowired TARepo taRepo;

    public List<TA> getList(){
        return taRepo.findAll();
    }
}
