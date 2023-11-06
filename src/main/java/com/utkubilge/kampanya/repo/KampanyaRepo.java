package com.utkubilge.kampanya.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utkubilge.kampanya.model.Kampanya;

@Repository
public interface KampanyaRepo extends JpaRepository<Kampanya,Long>{
    


}
