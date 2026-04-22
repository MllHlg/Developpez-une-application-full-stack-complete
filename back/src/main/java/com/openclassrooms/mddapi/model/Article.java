package com.openclassrooms.mddapi.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "articles")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Column(nullable = false)
	private String titre;

	@CreatedDate
	@Temporal(TemporalType.DATE)
	@Column(updatable = false, columnDefinition="DATE")
    private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User auteur;

	@ManyToOne
	@JoinColumn(name = "theme_id", nullable = false)
	private Theme theme;

	@NonNull
    @Size(max = 2500)
    @Column(nullable = false)
	private String texte;
	
}
