package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
    List<Article> findAllByThemeIn(List<Theme> themes);
}
