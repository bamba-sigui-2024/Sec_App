package com.projetSpringBoot.AppSec.DAO.repositories;

import com.projetSpringBoot.AppSec.DAO.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}