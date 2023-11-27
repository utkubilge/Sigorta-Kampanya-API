package com.utkubilge.kampanya.repo;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utkubilge.kampanya.model.Kampanya;

@Repository
public interface KampanyaRepo extends JpaRepository<Kampanya,Long>{
    
    //@Query("SELECT COUNT(*) FROM Kampanyalar WHERE status = ?1")
    Long countByStatus(int status);

    Kampanya findByNameAndDescAndType(String name, String desc, int type);


}
