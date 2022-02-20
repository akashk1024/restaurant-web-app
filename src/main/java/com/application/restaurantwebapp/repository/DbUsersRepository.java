package com.application.restaurantwebapp.repository;

import com.application.restaurantwebapp.entity.DbUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbUsersRepository extends JpaRepository <DbUsers,String>{
}
